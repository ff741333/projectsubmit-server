package com.submit.dao;

import com.submit.pojo.question;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM question LEFT JOIN answer ON question.idquestion = answer.idquestion AND answer.studentno = #{username} WHERE question.idjob= #{idjob}")
    List<Map<String,Object>> selectonesjobanswer(String username, Integer idjob);

    @Insert("INSERT INTO answer VALUES (#{idquestion},#{username},#{youranswer}) ON DUPLICATE KEY UPDATE youranswer = #{youranswer}")
    boolean insertorupdateanswer (String username,Integer idquestion, String youranswer);

    @Update("UPDATE question SET no = #{no},content = #{content}, type = #{type}, answer = #{answer}, question.option = #{option} WHERE idquestion = #{idquestion}")
    int updatequestion2(Integer idquestion, String content, Integer type, Integer no, String answer, Integer option);

    @Update("UPDATE question SET no = #{no},content = #{content}, type = #{type}, answer = #{answer} WHERE idquestion = #{idquestion}")
    int updatequestion1(Integer idquestion, String content, Integer type, Integer no, String answer);

    @Insert("INSERT INTO question (idjob, no) VALUES (#{idjob}, #{no})")
    boolean insertquestion(Integer idjob, Integer no);

    @Delete("DELETE FROM question WHERE idquestion = #{idquestion}")
    int deletequestionbyid(Integer idquestion);
}
