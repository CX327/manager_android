package org.com.manager.bean;

import org.com.manager.R;

/**
 * Created by jie.hua on 2016/3/27.
 * 消费类型
 */
public enum ConsumeTypeEnum {
    YIBAN(0, R.mipmap.icon_yiban, "一般"),
    DIANYING(1, R.mipmap.icon_dianying, "电影"),
    FANKA(2, R.mipmap.icon_fanka, "饭卡"),
    JIUSHUI(3, R.mipmap.icon_jiushui, "酒水"),
    TAOTAO(4, R.mipmap.icon_taobao, "淘宝"),
    CANYIN(5, R.mipmap.icon_chifan, "吃饭"),
    DAPAI(6, R.mipmap.icon_dapai, "打牌"),
    DIANFEI(7, R.mipmap.icon_dianfei, "电费"),
    FANGZU(8, R.mipmap.icon_fangzu, "房租"),
    JIANGTONG(9, R.mipmap.icon_jiaotong, "交通"),
    ZHUSHU(10, R.mipmap.icon_zhusu, "住宿"),
    LIANAI(11, R.mipmap.icon_lianai, "恋爱"),
    YOUXI(12, R.mipmap.icon_youxi, "游戏"),
    LIFA(13, R.mipmap.icon_lifa, "理发"),
    LINGSHI(14, R.mipmap.icon_lingshi, "零食");

    /**
     * 类型id
     */
    int typeId;
    /**
     * 类型图标
     */
    int typeImage;
    /**
     * 类型名称
     */
    String typeName;

    ConsumeTypeEnum() {
    }

    ConsumeTypeEnum(int typeId, int typeImage, String typeName) {
        this.typeId = typeId;
        this.typeImage = typeImage;
        this.typeName = typeName;
    }

   /* public ConsumeTypeEnum getConsumeTypeEnum(int id) {
        for (ConsumeTypeEnum consumeTypeEnum : ConsumeTypeEnum.values()) {
            if (consumeTypeEnum.getTypeId() == id) {
                return consumeTypeEnum;
            }
        }
        return YIBAN;
    }*/

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(int typeImage) {
        this.typeImage = typeImage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
