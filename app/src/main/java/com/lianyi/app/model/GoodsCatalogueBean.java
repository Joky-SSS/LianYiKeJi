package com.lianyi.app.model;
/**
  * @ClassName:      GoodsCatalogueBean
  * @Description:     物品标准目录
  * @Author:         Lee
  * @CreateDate:     2020/7/8 14:35
 */
public class GoodsCatalogueBean {

    /**
     * id : 85B1B4AFE2F87183E053EA02A8C0236A
     * categoryId : 20809000400
     * code : 00001
     * name : 书写投影器
     * spec : 250mm×250mm
     * uom : 台
     * spell : sxtyq
     */

    private String id;
    private String categoryId;
    private String code;
    private String name;
    private String spec;
    private String uom;
    private String spell;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}
