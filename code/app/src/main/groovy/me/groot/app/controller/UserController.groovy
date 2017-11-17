package me.groot.app.controller

import me.groot.app.util.JsonUtil
import me.groot.app.vo.DataTableParams
import me.groot.app.vo.DataTableResult
import me.groot.app.vo.RoleCommand
import me.groot.app.vo.UserCommand
import me.groot.core.domain.User
import me.groot.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping('/user')
class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController)

    @Autowired
    UserService userService

    // index view
    // Role and Authority are the same, hasRole and hasAuthority are the same as well.
    // hasAuthority('ROLE_ADMIN') has same result with hasRole('ROLE_ADMIN')
    // @PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')") // role based, isAuthenticated is not necessary here
    @PreAuthorize("hasPermission('', 'manageUser')")
    // permission based
    @GetMapping('/index')
    String index(Model model) {
        List<RoleCommand> commands = new ArrayList<>()
        userService.getAllRoles().each {
            commands.add(new RoleCommand(it))
        }
        model.addAttribute('roles', commands)
        return 'user/index'
    }

    @PreAuthorize("hasPermission('', 'manageUser')")
    // permission based
    @GetMapping(value = 'list', produces = 'application/json;charset=UTF-8')
    @ResponseBody
    String list(HttpServletRequest request) {

        DataTableParams params = new DataTableParams(request)

        // populate jpa specification
        // sorting
        Sort sort = new Sort(Sort.Direction.DESC, 'id') // default is id desc
        if (params.order && params.orderDir) {
            sort = new Sort(Sort.Direction.fromString(params.orderDir), params.order)
        }

        // pagination, page has already been calculated by params
        Pageable pageable = new PageRequest(params.page, params.size, sort)

        // filtering if existing in the params
        Specification<User> specification = null // nullable
        if (params.search) {
            specification = new Specification<User>() {
                @Override
                Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>()
                    predicates.add(cb.like(root.get('username').as(String), "%${params.search}%")) // username
                    predicates.add(cb.like(root.get('firstName').as(String), "%${params.search}%")) // first name
                    predicates.add(cb.like(root.get('lastName').as(String), "%${params.search}%")) // last name

                    cb.or(predicates as Predicate[])
                }
            }
        }

        Page<User> page = userService.findUsers(specification, pageable)

        DataTableResult<UserCommand> result = new DataTableResult<>(params)
        List<UserCommand> userCommands = new ArrayList<>()
        page.content.each {
            userCommands.add(new UserCommand(it))
        }
        result.data = userCommands
        result.recordsTotal = page.totalElements
        result.recordsFiltered = userService.countUser(specification)

        logger.debug("datatable result: ${JsonUtil.toJson(result)}")

        JsonUtil.toJson(result)
    }
}
