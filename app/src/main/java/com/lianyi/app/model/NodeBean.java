package com.lianyi.app.model;

/**
  * @ClassName:      NodeBean
  * @Description:     节点地址
  * @Author:         Lee
  * @CreateDate:     2020/6/22 10:51
 */
public class NodeBean {

        /**
         * id : 7aaeae97-f4b2-11e9-b01a-005056aa08ec
         * nodeCode : ys01
         * parentCode : EMS
         * nodeUrl : https://ys5.lianyitech.com.cn/iportal3
         * loginUrl : https://ys5.lianyitech.com.cn/iportal3/auth/password/access/token
         * nodeName : iportal3
         * status : 0
         * sort : 1
         */

        private String id;
        private String nodeCode;
        private String parentCode;
        private String nodeUrl;
        private String loginUrl;
        private String nodeName;
        private int status;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNodeCode() {
            return nodeCode;
        }

        public void setNodeCode(String nodeCode) {
            this.nodeCode = nodeCode;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getNodeUrl() {
            return nodeUrl;
        }

        public void setNodeUrl(String nodeUrl) {
            this.nodeUrl = nodeUrl;
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", nodeCode='" + nodeCode + '\'' +
                    ", parentCode='" + parentCode + '\'' +
                    ", nodeUrl='" + nodeUrl + '\'' +
                    ", loginUrl='" + loginUrl + '\'' +
                    ", nodeName='" + nodeName + '\'' +
                    ", status=" + status +
                    ", sort=" + sort +
                    '}';
        }

}
