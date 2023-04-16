package com.lianyi.app.model;

import android.os.Parcel;
import android.os.Parcelable;


public class InventoryGoodBean implements Parcelable {
    private String id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 库存卡片ID
     */
    private String inventoryId;

    /**
     * 目录ID
     */
    private String inventoryCatalogId;

    /**
     * 标准目录ID
     */
    private String guideCatalogId;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;
    /**
     * 规格
     */
    private String spec;
    /**
     * 单位
     */
    private String uom;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 金额
     */
    private String amount;

    /**
     * 单价
     */
    private String price;

    /**
     * 学段ID
     */
    private String schlstageId;

    /**
     * 科目ID
     */
    private String subjectId;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 用途ID
     */
    private String usageId;

    /**
     * 用途名称
     */
    private String usageName;

    /**
     * 房间ID
     */
    private String buildingId;

    /**
     * 房间名称
     */
    private String buildingName;

    /**
     * 柜子ID
     */
    private String containerId;

    /**
     * 柜子名称
     */
    private String containerName;

    /**
     * 位置
     */
    private String locDesc;

    /**
     * 保管人
     */
    private String userName;

    /**
     * 购入日期
     */
    private String purchaseDate;

    /**
     * 保修期(月)
     */
    private String warrantyTime;

    /**
     * 使用年限(年)
     */
    private String usefulLife;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 供应商
     */
    private String vendorName;

    /**
     * 供应商联系人
     */
    private String vendorContact;

    /**
     * 供应商联系电话
     */
    private String vendorPhonenum;

    /**
     * 资产属性
     */
    private String assetAttr;

    /**
     * 资产属性名称
     */
    private String assetAttrName;

    /**
     * 单据编号
     */
    private String batchNo;

    /**
     * rfid识别码/标签码
     */
    private String barcode;

    /**
     * 机构ID
     */
    private String unitId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private String createdDate;

    /**
     * 修改人
     */
    private String lastModifiedBy;

    /**
     * 修改时间
     */
    private String lastModifiedDate;

    /**
     * 新旧标记(NEW:新  OLD:旧)
     */
    private String newOldSign;

    /**
     * 盈亏标记(LOSS:亏  GAIN:盈)
     */
    private String lossGainSign;

    /**
     * 边框颜色：
     * BLUE     rfid识别到的
     * RED      rfid未识别到的
     * GREEN    标记为'新'的物品(不存在rfid码)
     */
    private String borderColor;

    /**
     * rfid绑定状态(0 否, 1 是)
     */
    private String rfidStatus;

    /**
     * 单据ID
     */
    private String billId;

    /**
     * 单据编号
     */
    private String billCode;

    /**
     * 单据状态
     */
    private String billStatus;

    /**
     * 原有数量
     */
    private String originalQuantity;

    /**
     * 原有金额
     */
    private String originalAmount;

    private String rfidInventoryBillId;

    /**
     * rfid识别码
     */
    private String rfidCode;

    private boolean isSelected;
    public InventoryGoodBean() {
    }

    protected InventoryGoodBean(Parcel in) {
        id = in.readString();
        taskId = in.readString();
        inventoryId = in.readString();
        inventoryCatalogId = in.readString();
        guideCatalogId = in.readString();
        code = in.readString();
        name = in.readString();
        spec = in.readString();
        uom = in.readString();
        quantity = in.readString();
        amount = in.readString();
        price = in.readString();
        schlstageId = in.readString();
        subjectId = in.readString();
        subjectName = in.readString();
        usageId = in.readString();
        usageName = in.readString();
        buildingId = in.readString();
        buildingName = in.readString();
        containerId = in.readString();
        containerName = in.readString();
        locDesc = in.readString();
        userName = in.readString();
        purchaseDate = in.readString();
        warrantyTime = in.readString();
        usefulLife = in.readString();
        brand = in.readString();
        vendorName = in.readString();
        vendorContact = in.readString();
        vendorPhonenum = in.readString();
        assetAttr = in.readString();
        assetAttrName = in.readString();
        batchNo = in.readString();
        barcode = in.readString();
        unitId = in.readString();
        createdBy = in.readString();
        createdDate = in.readString();
        lastModifiedBy = in.readString();
        lastModifiedDate = in.readString();
        newOldSign = in.readString();
        lossGainSign = in.readString();
        borderColor = in.readString();
        rfidStatus = in.readString();
        billId = in.readString();
        billCode = in.readString();
        billStatus = in.readString();
        originalQuantity = in.readString();
        originalAmount = in.readString();
        rfidInventoryBillId = in.readString();
        rfidCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskId);
        dest.writeString(inventoryId);
        dest.writeString(inventoryCatalogId);
        dest.writeString(guideCatalogId);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(spec);
        dest.writeString(uom);
        dest.writeString(quantity);
        dest.writeString(amount);
        dest.writeString(price);
        dest.writeString(schlstageId);
        dest.writeString(subjectId);
        dest.writeString(subjectName);
        dest.writeString(usageId);
        dest.writeString(usageName);
        dest.writeString(buildingId);
        dest.writeString(buildingName);
        dest.writeString(containerId);
        dest.writeString(containerName);
        dest.writeString(locDesc);
        dest.writeString(userName);
        dest.writeString(purchaseDate);
        dest.writeString(warrantyTime);
        dest.writeString(usefulLife);
        dest.writeString(brand);
        dest.writeString(vendorName);
        dest.writeString(vendorContact);
        dest.writeString(vendorPhonenum);
        dest.writeString(assetAttr);
        dest.writeString(assetAttrName);
        dest.writeString(batchNo);
        dest.writeString(barcode);
        dest.writeString(unitId);
        dest.writeString(createdBy);
        dest.writeString(createdDate);
        dest.writeString(lastModifiedBy);
        dest.writeString(lastModifiedDate);
        dest.writeString(newOldSign);
        dest.writeString(lossGainSign);
        dest.writeString(borderColor);
        dest.writeString(rfidStatus);
        dest.writeString(billId);
        dest.writeString(billCode);
        dest.writeString(billStatus);
        dest.writeString(originalQuantity);
        dest.writeString(originalAmount);
        dest.writeString(rfidInventoryBillId);
        dest.writeString(rfidCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InventoryGoodBean> CREATOR = new Creator<InventoryGoodBean>() {
        @Override
        public InventoryGoodBean createFromParcel(Parcel in) {
            return new InventoryGoodBean(in);
        }

        @Override
        public InventoryGoodBean[] newArray(int size) {
            return new InventoryGoodBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGuideCatalogId() {
        return guideCatalogId;
    }

    public void setGuideCatalogId(String guideCatalogId) {
        this.guideCatalogId = guideCatalogId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSchlstageId() {
        return schlstageId;
    }

    public void setSchlstageId(String schlstageId) {
        this.schlstageId = schlstageId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getUsageName() {
        return usageName;
    }

    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getVendorPhonenum() {
        return vendorPhonenum;
    }

    public void setVendorPhonenum(String vendorPhonenum) {
        this.vendorPhonenum = vendorPhonenum;
    }

    public String getAssetAttr() {
        return assetAttr;
    }

    public void setAssetAttr(String assetAttr) {
        this.assetAttr = assetAttr;
    }

    public String getAssetAttrName() {
        return assetAttrName;
    }

    public void setAssetAttrName(String assetAttrName) {
        this.assetAttrName = assetAttrName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public String getNewOldSign() {
        return newOldSign;
    }

    public void setNewOldSign(String newOldSign) {
        this.newOldSign = newOldSign;
    }

    public String getLossGainSign() {
        return lossGainSign;
    }

    public void setLossGainSign(String lossGainSign) {
        this.lossGainSign = lossGainSign;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getRfidStatus() {
        return rfidStatus;
    }

    public void setRfidStatus(String rfidStatus) {
        this.rfidStatus = rfidStatus;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(String originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getRfidInventoryBillId() {
        return rfidInventoryBillId;
    }

    public void setRfidInventoryBillId(String rfidInventoryBillId) {
        this.rfidInventoryBillId = rfidInventoryBillId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
