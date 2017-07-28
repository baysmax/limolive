package com.example.project.limolive.sidebar;


/**
 * json解析的时候的类
 */
public enum DATA {

    //疾病列表
    DISEASELIST("diseaseList",DiseaseList.class)

    ;

    private final String mId;

    private final Class cls;

    public Class getClzss() {
        return cls;
    }

    DATA(String id, Class clzss) {
        mId = id;
        cls = clzss;
    }

    /**
     * 根据json的key获取类
     * @param id
     * @return
     */
    public static DATA fromId(String id) {

        DATA[] values = values();
        int cc = values.length;

        for (int i = 0; i < cc; i++) {
            if (values[i].mId.equals(id)) {
                return values[i];
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return mId;
    }
}

