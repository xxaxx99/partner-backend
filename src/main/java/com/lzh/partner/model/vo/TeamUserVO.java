package com.lzh.partner.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname TeamUserVO
 * @Description 队伍和用户信息封装类
 * @Version 1.0.0
 * @Date 2023/10/16 16:45
 * @Created by lzh
 */
@Data
public class TeamUserVO implements Serializable {

    private static final long serialVersionUID = 4670197981807270552L;
    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户id（队长 id）
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人用户信息
     */
    private UserVO createUser;

    /**
     * 是都已经加入
     */
    private boolean hasJoin = false;


    private Integer hasJoinNum;

}
