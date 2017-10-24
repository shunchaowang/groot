package me.smartstart.app.vo

import javax.servlet.http.HttpServletRequest

class DataTableParams {

    String search
    String order
    String orderDir
    String offset
    String max

    DataTableParams(HttpServletRequest request) {
        search = request.getParameter('search[value]')
        int orderColumnIndex = Integer.valueOf(request.getParameter('order[0][column]'))
        order = request.getParameter("columns[${orderColumnIndex}][name]")
        orderDir = request.getParameter('order[0][dir]')
        offset = request.getParameter('start')
        max = request.getParameter('length')
    }
}
