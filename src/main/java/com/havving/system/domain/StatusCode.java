package com.havving.system.domain;

/**
 * Created by HAVVING on 2021-03-11.
 */
public enum StatusCode {
    OK(200), CREATED(201), BAD_REQUEST(400), NOT_FOUND(404), SERVER_ERROR(500);

    private int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
