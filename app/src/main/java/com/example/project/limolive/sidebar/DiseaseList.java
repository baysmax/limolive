package com.example.project.limolive.sidebar;

/**
 * 作用:
 *
 * @author LITP
 * @date 2016/11/3
 */

public class DiseaseList  {


    /**
     * xmlName : 分裂情感性障碍
     * xmlId : 1199
     * category : disease
     */

    private String xmlName;     //症状名字
    private String xmlId;       //症状名字
    private String category;    //分类的 英文名

    private String letter = ""; //字母，转为拼音后在这里添加

    public String getXmlName() {
        return xmlName;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public String getXmlId() {
        return xmlId;
    }

    public void setXmlId(String xmlId) {
        this.xmlId = xmlId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
