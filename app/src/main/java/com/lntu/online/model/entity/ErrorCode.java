package com.lntu.online.model.entity;

public enum  ErrorCode {

    CLIENT_ERROR,    // 默认值，没有错误码是使用该值
    HTTP_ERROR,      // HTTP错误
    UNKNOWN_ERROR,   // 客户端错误，出现这个需要开发者调试

    AUTH_ERROR,
    REMOTE_INVOKE_ERROR,
    PASSWORD_ERROR,
    NO_RIGHTS,
    NOT_FOUND,
    NOT_EVALUATE,
    COURSE_EVA_KEY_ERROR,
    ARGS_ERROR

}
