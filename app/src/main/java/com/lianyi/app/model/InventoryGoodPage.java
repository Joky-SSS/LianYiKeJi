package com.lianyi.app.model;

import android.util.PrintStreamPrinter;

import java.util.List;

public class InventoryGoodPage {
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Page {
        private int pageNo;
        private int pageSize;
        private int pageCount;
        private String count;
        private List<InventoryGoodBean> list;

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

        public List<InventoryGoodBean> getList() {
            return list;
        }

        public void setList(List<InventoryGoodBean> list) {
            this.list = list;
        }
    }
}

