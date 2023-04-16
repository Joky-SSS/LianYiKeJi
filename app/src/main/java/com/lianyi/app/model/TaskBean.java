package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
  * @ClassName:      TaskBean
  * @Description:
  * @Author:         Lee
  * @CreateDate:     2020/7/6 12:08
 */
public class TaskBean implements Parcelable {


    /**
     * status : PROCESSING
     * id : 50b1405c8215425394af7c95582879ec
     * name : pad测试小学清查任务
     * type : STOCKTAKE
     * beginDate : 1594261167000
     * endDate :
     * orgId : 8808010001
     * orgName :
     * currentMark : Y
     */

    private String status;
    private String id;
    private String name;
    private String type;
    private long beginDate;
    private String endDate;
    private String orgId;
    private String orgName;
    private String currentMark;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCurrentMark() {
        return currentMark;
    }

    public void setCurrentMark(String currentMark) {
        this.currentMark = currentMark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeLong(this.beginDate);
        dest.writeString(this.endDate);
        dest.writeString(this.orgId);
        dest.writeString(this.orgName);
        dest.writeString(this.currentMark);
    }

    public TaskBean() {
    }

    protected TaskBean(Parcel in) {
        this.status = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.beginDate = in.readLong();
        this.endDate = in.readString();
        this.orgId = in.readString();
        this.orgName = in.readString();
        this.currentMark = in.readString();
    }

    public static final Parcelable.Creator<TaskBean> CREATOR = new Parcelable.Creator<TaskBean>() {
        @Override
        public TaskBean createFromParcel(Parcel source) {
            return new TaskBean(source);
        }

        @Override
        public TaskBean[] newArray(int size) {
            return new TaskBean[size];
        }
    };
}
