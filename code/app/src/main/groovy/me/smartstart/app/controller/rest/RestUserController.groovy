package me.smartstart.app.controller.rest

import me.smartstart.app.util.JsonUtil
import me.smartstart.app.vo.DataTableParams
import me.smartstart.app.vo.DataTableResult
import me.smartstart.app.vo.UserCommand
import me.smartstart.core.domain.User
import me.smartstart.core.repository.UserRepository
import me.smartstart.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping('/rest/user')
class RestUserController {


    private static final Logger logger = LoggerFactory.getLogger(RestUserController)

    @Autowired
    UserService userService

    
}
