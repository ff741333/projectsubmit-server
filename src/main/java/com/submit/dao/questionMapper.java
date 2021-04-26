package com.submit.dao;

import com.submit.pojo.question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface questionMapper {
    @Select("SELECT * FROM question WHERE idjob = #{idjob} and no = #{no}")
    question selectbyjobidandno(Integer idjob, Integer no);

    @Select("SELECT * FROM question WHERE idjob = #{idjob}")
    List<question> selectbyjobid(Integer idjob);

    @Select("SELECT * FROM question LEFT JOIN answer ON question.idquestion = answer.idquestion AND answer.studentno = #{username} WHERE question.idquestion = #{idquestion}")
    Map<String,Object> selectonesqustion(String username, Integer idquestion);
}
