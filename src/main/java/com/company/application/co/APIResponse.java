package com.company.application.co;

import static com.company.application.authserver.constant.Constant.*;

public class APIResponse<T> {

    private boolean status;
    private String message;
    private T data;

    public APIResponse(){
        this.status = true;
        this.message = SUCCESS_MESSAGE;
    }

    public APIResponse(T data){
        this.data = data;
        this.status = true;
        this.message = SUCCESS_MESSAGE;
    }

    public APIResponse(boolean status, String message, T data){
        this.status = status;
        this.message  = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
