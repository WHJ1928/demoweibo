package com.example.demoweibo.mapper;

import com.example.demoweibo.dao.Username;
import com.example.demoweibo.dao.WeiboExample;
import com.example.demoweibo.dao.WeiboInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeiboMapper {

    int updateByExampleSelective(@Param("record") WeiboInfo record, @Param("example") WeiboExample example);

    int updateByExample(@Param("record") WeiboInfo record, @Param("example") WeiboExample example);


    @Select("SELECT username FROM weibo WHERE userId IS NULL")
    List<Username> selectUserName();
}