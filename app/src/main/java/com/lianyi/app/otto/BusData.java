package com.lianyi.app.otto;
/**
  * @ClassName:      BusData
  * @Description:     otto
  * @Author:         Lee
  * @CreateDate:     2020/7/5 18:38
 */
public class BusData<T> {

    public int tag;
    public T data;

    public BusData(int tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
