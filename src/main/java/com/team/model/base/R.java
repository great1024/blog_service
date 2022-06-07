package com.team.model.base;

import java.io.Serializable;

public  class R<T> implements Serializable {
    private Boolean success;
    private Object message;
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public R(Boolean success, Object message) {
        this.success=success;
        this.message=message;
    }
    public R(T data) {
        this.success=true;
        this.data=data;
    }

    public static R<?> success(Object message){
        return new R<>(true,message);
    }
    public static R<?> success(){
        return new R<>(true,"");
    }

    public static R<?> error(Object message){
        return new R<>(false,message);
    }
    public static R<?> successWithData(Object data){
        return new R<>(data);
    }
    public static void callBack(String callbackApi,R<?> r){

    }
}
