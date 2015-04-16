package com.lntu.online.model.entityOld;

import java.lang.reflect.ParameterizedType;

import com.lntu.online.util.JsonUtil;

public abstract class Model<M extends Model<?>> {

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
