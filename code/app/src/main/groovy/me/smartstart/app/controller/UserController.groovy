package me.smartstart.app.controller

import me.smartstart.app.util.JsonUtil
import me.smartstart.app.vo.DataTableParams
import me.smartstart.app.vo.DataTableResult
import me.smartstart.core.domain.User
import me.smartstart.core.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
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
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.servlet.http.HttpServletRequest
import java.text.DateFormat

@Controller
@RequestMapping('/user')
class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController)

    @Autowired
    UserRepository userRepository

    // index view
    // Role and Authority are the same, hasRole and hasAuthority are the same as well.
    // hasAuthority('ROLE_ADMIN') has same result with hasRole('ROLE_ADMIN')
    // @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // role based, isAuthenticated is not necessary here
    //@PreAuthorize("hasPermission('', 'userManagement')") // permission based
    @GetMapping('/index')
    String index() {
        return 'user/index'
    }

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

        Page<User> page = userRepository.findAll(specification, pageable)

        DataTableResult<UserCommand> result = new DataTableResult<>(params)
        List<UserCommand> userCommands = new ArrayList<>()
        page.content.each {
            userCommands.add(new UserCommand(it))
        }
        result.data = userCommands
        result.recordsTotal = page.totalElements
        result.recordsFiltered = userRepository.count(specification)

        JsonUtil.toJson(result)
    }
}

class UserCommand {

    long id
    String username
    String firstName
    String lastName
    String dateCreated
    String lastUpdated

    public UserCommand() {}

    public UserCommand(User user) {
        id = user.id
        username = user.username
        firstName = user.firstName
        lastName = user.lastName
        Locale locale = LocaleContextHolder.locale
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
        dateCreated = dateFormat.format(user.dateCreated)
        //lastUpdated = dateFormat.format(user.lastUpdated)
    }

}