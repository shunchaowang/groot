package me.smartstart.app.vo

class RestResponse {

    String status
    Object data

    RestResponse() {}

    RestResponse(String status, Object data) {
        this.status = status
        this.data = data
    }
}
