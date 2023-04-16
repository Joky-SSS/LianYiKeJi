package com.lianyi.app.model;
/**
  * @ClassName:      RoomTypeBean
  * @Description:     房间类型
  * @Author:         Lee
  * @CreateDate:     2020/6/28 15:29
 */
public class RoomTypeBean {

        /**
         * id : 020101
         * name : 数学教室
         * parentId : Z010101
         * lvl : 3
         * seqNum : 30101
         */

        private String id;
        private String name;
        private String parentId;
        private int lvl;
        private int seqNum;

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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getLvl() {
            return lvl;
        }

        public void setLvl(int lvl) {
            this.lvl = lvl;
        }

        public int getSeqNum() {
            return seqNum;
        }

        public void setSeqNum(int seqNum) {
            this.seqNum = seqNum;
    }
}
