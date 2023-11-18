package com.lzh.partner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.partner.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86132
* @description 针对表【tag(标签)】的数据库操作Mapper
* @createDate 2023-09-25 03:10:38
* @Entity com.lzh.partner.model.domain.Tag
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user ORDER BY RAND() LIMIT #{limit}")
    List<User> gerRandomData(@Param("limit") int limit);

}




