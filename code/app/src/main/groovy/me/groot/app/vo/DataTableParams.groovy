package me.groot.app.vo

import javax.servlet.http.HttpServletRequest

class DataTableParams {

    String search
    String order
    String orderDir
    int page
    int size // page size
    int draw // used by DataTable to prevent CSS Cross Site Scripting

    DataTableParams(HttpServletRequest request) {
        search = request.getParameter('search[value]')
        int orderColumnIndex = request.getParameter('order[0][column]')?.toInteger()
        order = request.getParameter("columns[${orderColumnIndex}][name]")
        orderDir = request.getParameter('order[0][dir]')?.toUpperCase()
        size = request.getParameter('length')?.toInteger()
        // data tables use a start to indicate the start of the record, we have to convert it to zero-based page base
        // on size
        page = 0 // if no start parameter, page 0 wanted
        if (request.getParameter('start')) {
            int start = Integer.valueOf(request.getParameter('start'))
            page = start / size
        }
        draw = request.getParameter('draw')?.toInteger()
    }
}
