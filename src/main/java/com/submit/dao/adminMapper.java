package com.submit.dao;

import com.submit.pojo.admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface adminMapper {
    @Select("SELECT * FROM admin WHERE idadmin = #{idadmin}")
    admin selectbykey(String idadmin);
    @Select("SELECT * FROM admin WHERE idadmin = #{idadmin} and password = #{password}")
    admin selectadmin(String idadmin, String password);

}
