package me.smartstart.app.vo

class DataTableResult<T> {

    long recordsTotal
    long recordsFiltered
    List<T> data
    int draw // the same value with the request

    DataTableResult(DataTableParams params) {
        draw = params.draw
    }
}
