package com.lzh.partner.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname TeamUpdateRequest
 * @Description 队伍更新封装
 * @Version 1.0.0
 * @Date 2023/10/16 21:07
 * @Created by lzh
 */
@Data
public class TeamUpdateRequest implements Serializable {

    private static final long serialVersionUID = 838649550953170643L;

    /**
     * 队伍id
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
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

}
