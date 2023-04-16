package com.lianyi.app.model;
/**
  * @ClassName:      SubjectBean
  * @Description:     科目
  * @Author:         Lee
  * @CreateDate:     2020/6/28 14:20
 */
public class SubjectBean {

    /**
     * name : 数学
     * id : Z010101
     * parentId : root
     * seqnum : 1010
     * buildingInfos :
     */

    private String name;
    private String id;
    private String parentId;
    private int seqnum;
    private String buildingInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(int seqnum) {
        this.seqnum = seqnum;
    }

    public String getBuildingInfos() {
        return buildingInfos;
    }

    public void setBuildingInfos(String buildingInfos) {
        this.buildingInfos = buildingInfos;
    }
}
