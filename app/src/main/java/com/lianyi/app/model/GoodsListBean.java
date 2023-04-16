package com.lianyi.app.model;

import java.util.List;
/**
  * @ClassName:      GoodsListBean
  * @Description:     物品列表
  * @Author:         Lee
  * @CreateDate:     2020/7/1 10:38
 */
public class GoodsListBean {

    /**
     * quantity : 10
     * dataList : {"pageNo":1,"pageSize":30,"pageCount":1,"count":"10","list":[{"id":"A958FB98048F7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB74B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3B67183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"计算机","spec":"多媒体","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"01001","quantity":"1.00","amount":"2950.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"2950.00","newMark":"Y","containerId":"","containerName":"","price":"2950.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9804907123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB84B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3C17183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"计算器","spec":"简易型","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"01012","quantity":"1.00","amount":"25.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"25.00","newMark":"Y","containerId":"","containerName":"","price":"25.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803E37123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F21766A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFE3C17183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"计算器","spec":"简易型","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"01012","quantity":"1.00","amount":"20.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"20.00","newMark":"Y","containerId":"","containerName":"","price":"20.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803F67123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB94B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3E77183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"打孔器","spec":"四件","uom":"套","guideName":"","guideSpec":"","guideUom":"","code":"02002","quantity":"1.00","amount":"12.80","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"12.80","newMark":"Y","containerId":"","containerName":"","price":"12.80","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803FA7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAF914B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3F37183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"打气筒","spec":"","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"02015","quantity":"1.00","amount":"11.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"11.00","newMark":"Y","containerId":"","containerName":"","price":"11.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A77123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F1D166A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB1C7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"软尺","spec":"2000mm","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"10007","quantity":"1.00","amount":"1.80","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"1.80","newMark":"Y","containerId":"","containerName":"","price":"1.80","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A67123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F1D266A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB1D7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"卷尺","spec":"20m","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"10008","quantity":"1.00","amount":"9.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"9.00","newMark":"Y","containerId":"","containerName":"","price":"9.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803AB7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20566A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB2C7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"托盘天平","spec":"500g，0.5g","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11006","quantity":"1.00","amount":"35.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"35.00","newMark":"Y","containerId":"","containerName":"","price":"35.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A97123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20666A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB2E7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"简易天平","spec":"200g，1g","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11007","quantity":"1.00","amount":"22.50","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"22.50","newMark":"Y","containerId":"","containerName":"","price":"22.50","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803AA7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20766A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB3B7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"弹簧度盘秤","spec":"指针式，1kg","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11019","quantity":"1.00","amount":"82.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"82.00","newMark":"Y","containerId":"","containerName":"","price":"82.00","assetAttr":"","locDesc":"","vendorContact":""}],"sum":""}
     * aomunt : 3169.1
     */

    private String  quantity;
    private DataListBean dataList;
    private String aomunt;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public DataListBean getDataList() {
        return dataList;
    }

    public void setDataList(DataListBean dataList) {
        this.dataList = dataList;
    }

    public String getAomunt() {
        return aomunt;
    }

    public void setAomunt(String aomunt) {
        this.aomunt = aomunt;
    }

    public static class DataListBean {
        /**
         * pageNo : 1
         * pageSize : 30
         * pageCount : 1
         * count : 10
         * list : [{"id":"A958FB98048F7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB74B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3B67183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"计算机","spec":"多媒体","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"01001","quantity":"1.00","amount":"2950.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"2950.00","newMark":"Y","containerId":"","containerName":"","price":"2950.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9804907123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB84B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3C17183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"计算器","spec":"简易型","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"01012","quantity":"1.00","amount":"25.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"25.00","newMark":"Y","containerId":"","containerName":"","price":"25.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803E37123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F21766A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFE3C17183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"计算器","spec":"简易型","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"01012","quantity":"1.00","amount":"20.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"20.00","newMark":"Y","containerId":"","containerName":"","price":"20.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803F67123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAFB94B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3E77183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"打孔器","spec":"四件","uom":"套","guideName":"","guideSpec":"","guideUom":"","code":"02002","quantity":"1.00","amount":"12.80","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"12.80","newMark":"Y","containerId":"","containerName":"","price":"12.80","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803FA7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A94B008DAF914B88E053C9640A0A0CA8","inventoryCatalogId":"85B1B4AFE3F37183E053EA02A8C0236A","subjectId":"Z010102","usageId":"U10200","schlstageId":"2","name":"打气筒","spec":"","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"02015","quantity":"1.00","amount":"11.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"11.00","newMark":"Y","containerId":"","containerName":"","price":"11.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A77123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F1D166A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB1C7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"软尺","spec":"2000mm","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"10007","quantity":"1.00","amount":"1.80","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"1.80","newMark":"Y","containerId":"","containerName":"","price":"1.80","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A67123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F1D266A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB1D7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"卷尺","spec":"20m","uom":"个","guideName":"","guideSpec":"","guideUom":"","code":"10008","quantity":"1.00","amount":"9.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"9.00","newMark":"Y","containerId":"","containerName":"","price":"9.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803AB7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20566A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB2C7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"托盘天平","spec":"500g，0.5g","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11006","quantity":"1.00","amount":"35.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"35.00","newMark":"Y","containerId":"","containerName":"","price":"35.00","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803A97123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20666A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB2E7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"简易天平","spec":"200g，1g","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11007","quantity":"1.00","amount":"22.50","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"22.50","newMark":"Y","containerId":"","containerName":"","price":"22.50","assetAttr":"","locDesc":"","vendorContact":""},{"id":"A958FB9803AA7123E053C9640A0AF771","remarks":"","createBy":"","createDate":"","updateBy":"","updateDate":"","taskId":"405c2e875e26487ebc92f69ffe856659","inventoryId":"A8A81896F20766A5E053C9640A0A3252","inventoryCatalogId":"85B1B4AFDB3B7183E053EA02A8C0236A","subjectId":"Z010101","usageId":"U10100","schlstageId":"2","name":"弹簧度盘秤","spec":"指针式，1kg","uom":"台","guideName":"","guideSpec":"","guideUom":"","code":"11019","quantity":"1.00","amount":"82.00","purchaseDate":"","warrantyTime":"","usefulLife":"","vendorName":"","vendorPhonenum":"","userName":"","unitId":"8808010001","batchNo":"","brand":"","barcode":"","guideCatalogId":"","buildingId":"ad47ed4bad9045dcaacde4b1fde41752","createdBy":"pad测试小学","createdDate":"2020-07-01","lastModifiedBy":"pad测试小学","lastModifiedDate":"2020-07-01","targetQuantity":"1.00","targetAmount":"82.00","newMark":"Y","containerId":"","containerName":"","price":"82.00","assetAttr":"","locDesc":"","vendorContact":""}]
         * sum :
         */

        private int pageNo;
        private int pageSize;
        private int pageCount;
        private String count;
        private String sum;
        private List<GoodsBean> list;

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

        public List<GoodsBean> getList() {
            return list;
        }

        public void setList(List<GoodsBean> list) {
            this.list = list;
        }
    }

}
