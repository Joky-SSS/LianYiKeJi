package com.lianyi.app.utils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.lianyi.app.model.PageList;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.TaskBean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * @ClassName: ResponseParser
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/6/28 11:16
 */
@Parser(name = "Response", wrappers = {List.class, PageList.class})
public class ResponseParser<T> extends AbstractParser<T> {

    //注意，以下两个构造方法是必须的
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        //获取泛型类型
        final Type type = ParameterizedTypeImpl.get(Response.class, mType);
        Response<T> data = convert(response, type);
        //获取data字段
        T t = data.getData();
        //网络成功
        if (response.code() == HttpURLConnection.HTTP_OK) {
            if (data.getCode() == 0) {
                if (t == null) {
                    t = (T) "";
                    return t;
                } else {
                    return t;
                }
            } else {
                ToastUtils.showLong(data.getMessage());
                throw new ParseException(String.valueOf(data.getCode()), data.getMessage(), response);
            }
        } else {//网络失败
            throw new ParseException(String.valueOf(data.getCode()), data.getMessage(), response);
        }
    }
}