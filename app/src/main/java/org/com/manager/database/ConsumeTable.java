package org.com.manager.database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by jie.hua on 2016/3/27.
 */
@DatabaseTable(tableName = "ConsumeTable")
public class ConsumeTable {
    /**
     * id
     */
    @DatabaseField(generatedId = true)
    int consumeId;

    /**
     * 消费时间
     */
    @DatabaseField
    String consumeTime;
    /**
     * 消费金额
     */
    @DatabaseField
    float consumeMoney;
    /**
     * 是否是支出
     */
    @DatabaseField
    boolean consumeIsPay;
    /**
     * 消费类型
     */
    @DatabaseField
    int consumeTypeId;
    /**
     * 备注
     */
    @DatabaseField
    String remarks;

    public ConsumeTable() {
    }

    public ConsumeTable(String consumeTime, float consumeMoney,
                        boolean consumeIsPay, int consumeTypeId,
                        String remarks) {
        this.consumeTime = consumeTime;
        this.consumeMoney = consumeMoney;
        this.consumeIsPay = consumeIsPay;
        this.consumeTypeId = consumeTypeId;
        this.remarks = remarks;
    }

    public ConsumeTable(int consumeId, String consumeTime,
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
