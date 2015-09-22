package org.lntu.online.model.entity;

import com.google.gson.annotations.SerializedName;
import org.lntu.online.util.gson.GsonWrapper;

import retrofit.RetrofitError;

public class ErrorInfo {

    private int statusCode;

    @SerializedName("code")
    private ErrorCode errorCode;

    private String message;

    public static ErrorInfo build(RetrofitError error) {
        ErrorInfo result;
        try {
            result = (ErrorInfo) error.getBodyAs(ErrorInfo.class);
            if (result == null) {
                result = new ErrorInfo();
                if (error.getResponse() == null) {
                    result.statusCode = 0;
                    result.errorCode = ErrorCode.CLIENT_ERROR;
                } else {
                    result.statusCode = error.getResponse().getStatus();
                    result.errorCode = ErrorCode.HTTP_ERROR;
                }
                result.message = error.getLocalizedMessage();
            } else {
                result.statusCode = error.getResponse().getStatus();
            }
        } catch (Exception e) {
            result = new ErrorInfo();
            result.statusCode = error.getResponse() == null ? 0 : error.getResponse().getStatus();
            result.errorCode = ErrorCode.UNKNOWN_ERROR;
            result.message = error.getLocalizedMessage();
        }
        return result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return GsonWrapper.gson.toJson(this);
    }

}
