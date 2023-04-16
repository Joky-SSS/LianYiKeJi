package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: GoodsBean
 * @Description: 物品
 * @Author: Lee
 * @CreateDate: 2020/7/1 10:35
 */
public class GoodsBean implements Parcelable {

    /**
     * id : A958FB980BC67123E053C9640A0AF771
     * remarks :
     * createBy :
     * createDate :
     * updateBy :
     * updateDate :
     * taskId : 09e237a036db4b67af95b097ed002227
     * inventoryId : A94B008DAF944B88E053C9640A0A0CA8
     * inventoryCatalogId : 85B1B4AFE2F87183E053EA02A8C0236A
     * subjectId : Z010102
     * usageId : U10200
     * schlstageId : 2
     * name : 书写投影器
     * spec : 250mm×250mm
     * uom : 台
     * guideName :
     * guideSpec :
     * guideUom :
     * code : 00001
     * quantity : 0.00
     * amount : 0.00
     * purchaseDate :
     * warrantyTime :
     * usefulLife :
     * vendorName :
     * vendorPhonenum :
     * userName :
     * unitId : 8808010001
     * batchNo :
     * brand :
     * barcode :
     * guideCatalogId :
     * buildingId : 35ebdcfe845c40109df3303439e366ef
     * createdBy : pad测试小学
     * createdDate : 2020-07-01
     * lastModifiedBy : pad测试小学
     * lastModifiedDate : 2020-07-08
     * targetQuantity : 0.00
     * targetAmount : 0.00
     * newMark : Y
     * containerId :
     * containerName :
     * price : 0.00
     * assetAttr :
     * locDesc :
     * vendorContact :
     * subjectName : 科学
     * usageName : 科学仪器
     * buildingName : 科学实验室
     * assetAttrName :
     * barcodeUrl :
     */

    private String id;
    private String remarks;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String taskId;
    private String inventoryId;
    private String inventoryCatalogId;
    private String subjectId;
    private String usageId;
    private String schlstageId;
    private String name;
    private String spec;
    private String uom;
    private String guideName;
    private String guideSpec;
    private String guideUom;
    private String code;
    private String quantity;
    private String amount;
    private String purchaseDate;
    private String warrantyTime;
    private String usefulLife;
    private String vendorName;
    private String vendorPhonenum;
    private String userName;
    private String unitId;
    private String batchNo;
    private String brand;
    private String barcode;
    private String guideCatalogId;
    private String buildingId;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String targetQuantity;
    private String targetAmount;
    private String newMark;
    private String containerId;
    private String containerName;
    private String price;
    private String assetAttr;
    private String locDesc;
    private String vendorContact;
    private String subjectName;
    private String usageName;
    private String buildingName;
    private String assetAttrName;
    private String barcodeUrl;
    private String refPrice;
    //打印数量
    private int printCount = 1;
    //是否选中
    private boolean isSelect = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryCatalogId() {
        return inventoryCatalogId;
    }

    public void setInventoryCatalogId(String inventoryCatalogId) {
        this.inventoryCatalogId = inventoryCatalogId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getSchlstageId() {
        return schlstageId;
    }

    public void setSchlstageId(String schlstageId) {
        this.schlstageId = schlstageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getGuideSpec() {
        return guideSpec;
    }

    public void setGuideSpec(String guideSpec) {
        this.guideSpec = guideSpec;
    }

    public String getGuideUom() {
        return guideUom;
    }

    public void setGuideUom(String guideUom) {
        this.guideUom = guideUom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(String warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public String getUsefulLife() {
        return usefulLife;
    }

    public void setUsefulLife(String usefulLife) {
        this.usefulLife = usefulLife;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPhonenum() {
        return vendorPhonenum;
    }

    public void setVendorPhonenum(String vendorPhonenum) {
        this.vendorPhonenum = vendorPhonenum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGuideCatalogId() {
        return guideCatalogId;
    }

    public void setGuideCatalogId(String guideCatalogId) {
        this.guideCatalogId = guideCatalogId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(String targetQuantity) {
        this.targetQuantity = targetQuantity;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getNewMark() {
        return newMark;
    }

    public void setNewMark(String newMark) {
        this.newMark = newMark;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAssetAttr() {
        return assetAttr;
    }

    public void setAssetAttr(String assetAttr) {
        this.assetAttr = assetAttr;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getUsageName() {
        return usageName;
    }

    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAssetAttrName() {
        return assetAttrName;
    }

    public void setAssetAttrName(String assetAttrName) {
        this.assetAttrName = assetAttrName;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getBarcodeUrl() {
        return barcodeUrl;
    }

    public void setBarcodeUrl(String barcodeUrl) {
        this.barcodeUrl = barcodeUrl;
    }

    public String getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(String refPrice) {
        this.refPrice = refPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.remarks);
        dest.writeString(this.createBy);
        dest.writeString(this.createDate);
        dest.writeString(this.updateBy);
        dest.writeString(this.updateDate);
        dest.writeString(this.taskId);
        dest.writeString(this.inventoryId);
        dest.writeString(this.inventoryCatalogId);
        dest.writeString(this.subjectId);
        dest.writeString(this.usageId);
        dest.writeString(this.schlstageId);
        dest.writeString(this.name);
        dest.writeString(this.spec);
        dest.writeString(this.uom);
        dest.writeString(this.guideName);
        dest.writeString(this.guideSpec);
        dest.writeString(this.guideUom);
        dest.writeString(this.code);
        dest.writeString(this.quantity);
        dest.writeString(this.amount);
        dest.writeString(this.purchaseDate);
        dest.writeString(this.warrantyTime);
        dest.writeString(this.usefulLife);
        dest.writeString(this.vendorName);
        dest.writeString(this.vendorPhonenum);
        dest.writeString(this.userName);
        dest.writeString(this.unitId);
        dest.writeString(this.batchNo);
        dest.writeString(this.brand);
        dest.writeString(this.barcode);
        dest.writeString(this.guideCatalogId);
        dest.writeString(this.buildingId);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDate);
        dest.writeString(this.lastModifiedBy);
        dest.writeString(this.lastModifiedDate);
        dest.writeString(this.targetQuantity);
        dest.writeString(this.targetAmount);
        dest.writeString(this.newMark);
        dest.writeString(this.containerId);
        dest.writeString(this.containerName);
        dest.writeString(this.price);
        dest.writeString(this.assetAttr);
        dest.writeString(this.locDesc);
        dest.writeString(this.vendorContact);
        dest.writeString(this.subjectName);
        dest.writeString(this.usageName);
        dest.writeString(this.buildingName);
        dest.writeString(this.assetAttrName);
        dest.writeString(this.barcodeUrl);
        dest.writeString(this.refPrice);
        dest.writeInt(this.printCount);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public GoodsBean() {
    }

    protected GoodsBean(Parcel in) {
        this.id = in.readString();
        this.remarks = in.readString();
        this.createBy = in.readString();
        this.createDate = in.readString();
        this.updateBy = in.readString();
        this.updateDate = in.readString();
        this.taskId = in.readString();
        this.inventoryId = in.readString();
        this.inventoryCatalogId = in.readString();
        this.subjectId = in.readString();
        this.usageId = in.readString();
        this.schlstageId = in.readString();
        this.name = in.readString();
        this.spec = in.readString();
        this.uom = in.readString();
        this.guideName = in.readString();
        this.guideSpec = in.readString();
        this.guideUom = in.readString();
        this.code = in.readString();
        this.quantity = in.readString();
        this.amount = in.readString();
        this.purchaseDate = in.readString();
        this.warrantyTime = in.readString();
        this.usefulLife = in.readString();
        this.vendorName = in.readString();
        this.vendorPhonenum = in.readString();
        this.userName = in.readString();
        this.unitId = in.readString();
        this.batchNo = in.readString();
        this.brand = in.readString();
        this.barcode = in.readString();
        this.guideCatalogId = in.readString();
        this.buildingId = in.readString();
        this.createdBy = in.readString();
        this.createdDate = in.readString();
        this.lastModifiedBy = in.readString();
        this.lastModifiedDate = in.readString();
        this.targetQuantity = in.readString();
        this.targetAmount = in.readString();
        this.newMark = in.readString();
        this.containerId = in.readString();
        this.containerName = in.readString();
        this.price = in.readString();
        this.assetAttr = in.readString();
        this.locDesc = in.readString();
        this.vendorContact = in.readString();
        this.subjectName = in.readString();
        this.usageName = in.readString();
        this.buildingName = in.readString();
        this.assetAttrName = in.readString();
        this.barcodeUrl = in.readString();
        this.refPrice = in.readString();
        this.printCount = in.readInt();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    @Override
    public String toString() {
        return "GoodsBean{" +
                "id='" + id + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", taskId='" + taskId + '\'' +
                ", inventoryId='" + inventoryId + '\'' +
                ", inventoryCatalogId='" + inventoryCatalogId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", usageId='" + usageId + '\'' +
                ", schlstageId='" + schlstageId + '\'' +
                ", name='" + name + '\'' +
                ", spec='" + spec + '\'' +
                ", uom='" + uom + '\'' +
                ", guideName='" + guideName + '\'' +
                ", guideSpec='" + guideSpec + '\'' +
                ", guideUom='" + guideUom + '\'' +
                ", code='" + code + '\'' +
                ", quantity='" + quantity + '\'' +
                ", amount='" + amount + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", warrantyTime='" + warrantyTime + '\'' +
                ", usefulLife='" + usefulLife + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", vendorPhonenum='" + vendorPhonenum + '\'' +
                ", userName='" + userName + '\'' +
                ", unitId='" + unitId + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", brand='" + brand + '\'' +
                ", barcode='" + barcode + '\'' +
                ", guideCatalogId='" + guideCatalogId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                ", targetQuantity='" + targetQuantity + '\'' +
                ", targetAmount='" + targetAmount + '\'' +
                ", newMark='" + newMark + '\'' +
                ", containerId='" + containerId + '\'' +
                ", containerName='" + containerName + '\'' +
                ", price='" + price + '\'' +
                ", assetAttr='" + assetAttr + '\'' +
                ", locDesc='" + locDesc + '\'' +
                ", vendorContact='" + vendorContact + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", usageName='" + usageName + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", assetAttrName='" + assetAttrName + '\'' +
                ", barcodeUrl='" + barcodeUrl + '\'' +
                ", refPrice='" + refPrice + '\'' +
                ", printCount=" + printCount +
                ", isSelect=" + isSelect +
                '}';
    }
}
