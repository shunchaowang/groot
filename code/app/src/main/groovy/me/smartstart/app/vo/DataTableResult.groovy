package me.smartstart.app.vo

class DataTableResult<T> {

    int recordsTotal
    int recordsFiltered
    List<T> data

    DataTableResult() {
        data = new ArrayList<>()
    }
}
