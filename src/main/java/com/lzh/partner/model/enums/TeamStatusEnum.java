package com.lzh.partner.model.enums;

import lombok.Getter;

/**
 * @Classname TeamStatusEnum
 * @Description 队伍状态枚举
 * @Version 1.0.0
 * @Date 2023/10/12 18:45
 * @Created by lzh
 */
@Getter
public enum TeamStatusEnum {

    PUBLIC(0,"公开"),
    PRIVATE(1,"私有"),
    SECRET(2,"加密");


    private int value;

    private String text;

    public static TeamStatusEnum getEnumByValue(Integer value){
        if (value == null){
            return null;
        }
        TeamStatusEnum[] values = TeamStatusEnum.values();
        for (TeamStatusEnum teamStatusEnum : values) {
            if (teamStatusEnum.getValue() == value){
                return teamStatusEnum;
            }
        }
        return null;
    }

    TeamStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }
}
