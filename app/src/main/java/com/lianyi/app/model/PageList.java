package com.lianyi.app.model;

import java.util.List;

/**
 * @ClassName: PageList
 * @Description: 分页
 * @Author: Lee
 * @CreateDate: 2020/6/28 16:37
 */
public class PageList<T> {
    private int pageNo;
    private int pageSize;
    private int pageCount;
    private String count;
    private String sum;
    private List<T> datas;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
