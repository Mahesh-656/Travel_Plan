package org.travel_plan.dto;

public class ApiResponse<T> {
    private T data;
    private String message;
    private Integer status;

    public T getData() {
        return data;
    }
}

