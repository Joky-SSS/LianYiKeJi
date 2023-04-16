package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
/**
  * @ClassName:      RoomListBean
  * @Description:     房间列表
  * @Author:         Lee
  * @CreateDate:     2020/6/28 16:42
 */
public class RoomListBean implements Parcelable {

    /**
     * pageNo : 1
     * pageSize : 30
     * pageCount : 1
     * count : 2
     * list : [{"name":"数学教室","amount":0},{"name":"新建数学教室房间","amount":0}]
     * sum :
     */

    private int pageNo;
    private int pageSize;
    private int pageCount;
    private String count;
    private String sum;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * name : 数学教室
         * amount : 0
         */

        private String id;
        private String name;
        private String quantity;
        private String amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.quantity);
            dest.writeString(this.amount);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.quantity = in.readString();
            this.amount = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageNo);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.pageCount);
        dest.writeString(this.count);
        dest.writeString(this.sum);
        dest.writeTypedList(this.list);
    }

    public RoomListBean() {
    }

    protected RoomListBean(Parcel in) {
        this.pageNo = in.readInt();
        this.pageSize = in.readInt();
        this.pageCount = in.readInt();
        this.count = in.readString();
        this.sum = in.readString();
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<RoomListBean> CREATOR = new Parcelable.Creator<RoomListBean>() {
        @Override
        public RoomListBean createFromParcel(Parcel source) {
            return new RoomListBean(source);
        }

        @Override
        public RoomListBean[] newArray(int size) {
            return new RoomListBean[size];
        }
    };
}
