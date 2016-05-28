package org.com.manager.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.com.manager.util.FrameUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.hua on 2016/3/27.
 */
public class ConsumeModel implements Serializable {
    /**
     * id
     */
    int consumeId;

    /**
     * 消费时间
     */
    String consumeTime;
    /**
     * 消费金额
     */
    float consumeMoney;
    /**
     * 是否是支出
     */
    boolean consumeIsPay;
    /**
     * 消费类型
     */
    int consumeTypeId;
    /**
     * 备注
     */
    String remarks;

    public ConsumeModel() {
    }

    public HashMap getMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        ConsumeTypeEnum consumeType = getConsumeTypeEnum(getConsumeTypeId());
        hashMap.put(FrameUtils.IT_ACCOUNTING_TYPE_IMAGE, consumeType.getTypeImage());
        hashMap.put(FrameUtils.IT_ACCOUNTING_TIME, getConsumeTime());
        hashMap.put(FrameUtils.IT_ACCOUNTING_MONEY, getConsumeMoney());
        hashMap.put(FrameUtils.IT_ACCOUNTING_REMARKS, getRemarks());

        return hashMap;
    }

    /**
     * 获得消费类型枚举
     */
    public ConsumeTypeEnum getConsumeTypeEnum(int id) {
        for (ConsumeTypeEnum consumeTypeEnum : ConsumeTypeEnum.values()) {
            if (consumeTypeEnum.getTypeId() == id) {
                return consumeTypeEnum;
            }
        }
        return ConsumeTypeEnum.YIBAN;
    }

    public ConsumeModel(String consumeTime, float consumeMoney,
                        boolean consumeIsPay, int consumeTypeId,
                        String remarks) {
        this.consumeTime = consumeTime;
        this.consumeMoney = consumeMoney;
        this.consumeIsPay = consumeIsPay;
        this.consumeTypeId = consumeTypeId;
        this.remarks = remarks;
    }

    public ConsumeModel(int consumeId, String consumeTime,
                        float consumeMoney, boolean consumeIsPay,
                        int consumeTypeId, String remarks) {
        this.consumeId = consumeId;
        this.consumeTime = consumeTime;
        this.consumeMoney = consumeMoney;
        this.consumeIsPay = consumeIsPay;
        this.consumeTypeId = consumeTypeId;
        this.remarks = remarks;
    }

    public int getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(int consumeId) {
        this.consumeId = consumeId;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public float getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(float consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public boolean isConsumeIsPay() {
        return consumeIsPay;
    }

    public void setConsumeIsPay(boolean consumeIsPay) {
        this.consumeIsPay = consumeIsPay;
    }

    public int getConsumeTypeId() {
        return consumeTypeId;
    }

    public void setConsumeTypeId(int consumeTypeId) {
        this.consumeTypeId = consumeTypeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
