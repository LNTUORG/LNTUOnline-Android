package org.lntu.online.model.entity;

public enum  ErrorCode {

    CLIENT_ERROR,    // 默认值，没有错误码是使用该值
    HTTP_ERROR,      // HTTP错误
    UNKNOWN_ERROR,   // 客户端错误，出现这个需要开发者调试

    AUTH_ERROR,            // 401
    REMOTE_INVOKE_ERROR,   // 500
    PASSWORD_ERROR,        // 400
    NO_RIGHTS,             // 403
    NOT_FOUND,             // 404
    NOT_EVALUATE,          // 403
    COURSE_EVA_KEY_ERROR,  // 400
    ARGS_ERROR             // 400

}
