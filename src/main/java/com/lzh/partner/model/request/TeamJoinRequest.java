package com.lzh.partner.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 队伍
 * @TableName team
 */
@Data
public class TeamJoinRequest implements Serializable {


    private static final long serialVersionUID = 6830447383626723427L;

    /**
     * 队伍id
     */
    private Long teamId;


    /**
     * 密码
     */
    private String password;

}