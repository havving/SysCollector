package com.havving.system.domain;

import com.havving.system.global.StatusCode;

/**
 * Created by HAVVING on 2021-03-11.
 */
public class JsonResponse<T extends SysModel> {

    private int statusCode;
    private T[] model;

    public JsonResponse(StatusCode status, T... model) {
        this.statusCode = status.getStatusCode();
        this.model = model;
    }

    public T[] getModel() {
        return this.model;
    }
}
