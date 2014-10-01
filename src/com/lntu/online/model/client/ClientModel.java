package com.lntu.online.model.client;

import java.lang.reflect.ParameterizedType;

import com.lntu.online.util.JsonUtil;

public abstract class ClientModel<M extends ClientModel<?>> {

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public M fromJson(String json) {
        @SuppressWarnings("unchecked")
        Class<M> entityClass = (Class<M>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return JsonUtil.fromJson(json, entityClass);
    }

    @Override
    public String toString() {
        return toJson();
    }

}
