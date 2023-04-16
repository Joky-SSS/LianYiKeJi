package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class InventoryRoomListBean implements Parcelable {

    /**
     * pageNo : 1
     * pageSize : 30
     * pageCount : 1
     * count : 2
     * list : [{"name":"数学教室","amount":0}]
     * sum :
     */

    private int pageNo;
    private int pageSize;
    private int pageCount;
    private String count;
    private String sum;
    private List<RoomBean> data;

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

    public List<RoomBean> getList() {
        return data;
    }

    public void setList(List<RoomBean> list) {
        this.data = list;
    }

    public static class RoomBean implements Parcelable {
        /**
         * name : 数学教室
         * amount : 0
         */
        private String taskId;
        private String id;
        private String name;
        private String quantity;
        private String amount;
        private String gainQty;
        private String lossQty;
        private String gainAmount;
        private String lossAmount;

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

        public String getGainQty() {
            return gainQty;
        }

        public void setGainQty(String gainQty) {
            this.gainQty = gainQty;
        }

        public String getLossQty() {
            return lossQty;
        }

        public void setLossQty(String lossQty) {
            this.lossQty = lossQty;
        }

        public String getGainAmount() {
            return gainAmount;
        }

        public void setGainAmount(String gainAmount) {
            this.gainAmount = gainAmount;
        }

        public String getLossAmount() {
            return lossAmount;
        }

        public void setLossAmount(String lossAmount) {
            this.lossAmount = lossAmount;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.taskId);
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.quantity);
            dest.writeString(this.amount);
            dest.writeString(this.gainQty);
            dest.writeString(this.gainAmount);
            dest.writeString(this.lossQty);
            dest.writeString(this.lossAmount);
        }

        public RoomBean() {
        }

        protected RoomBean(Parcel in) {
            this.taskId = in.readString();
            this.id = in.readString();
            this.name = in.readString();
            this.quantity = in.readString();
            this.amount = in.readString();
            this.gainQty = in.readString();
            this.gainAmount = in.readString();
            this.lossQty = in.readString();
            this.lossAmount = in.readString();
        }

        public static final Creator<RoomBean> CREATOR = new Creator<RoomBean>() {
            @Override
            public RoomBean createFromParcel(Parcel source) {
                return new RoomBean(source);
            }

            @Override
            public RoomBean[] newArray(int size) {
                return new RoomBean[size];
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
        dest.writeTypedList(this.data);
    }

    public InventoryRoomListBean() {
    }

    protected InventoryRoomListBean(Parcel in) {
        this.pageNo = in.readInt();
        this.pageSize = in.readInt();
        this.pageCount = in.readInt();
        this.count = in.readString();
        this.sum = in.readString();
        this.data = in.createTypedArrayList(RoomBean.CREATOR);
    }

    public static final Creator<InventoryRoomListBean> CREATOR = new Creator<InventoryRoomListBean>() {
        @Override
        public InventoryRoomListBean createFromParcel(Parcel source) {
            return new InventoryRoomListBean(source);
        }

        @Override
        public InventoryRoomListBean[] newArray(int size) {
            return new InventoryRoomListBean[size];
        }
    };
}
