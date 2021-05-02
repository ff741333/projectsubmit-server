package com.submit.dao;

import com.submit.pojo.job;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface jobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(job record);

    int insertSelective(job record);

    job selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(job record);

    int updateByPrimaryKey(job record);
    //插入job，具体的那次作业
    @Insert("insert into job (teachclassid,no,title,duedate,type,note,creteTime)" +
            "values(#{teachclassid},#{no},#{title},#{duedate},#{type},#{note},#{creteTime})")
    boolean insertjob(job job);

    @Select("select * from job where teachclassid=#{teachclassid} order by createTime desc")
    List<job> getjobbyteachclassid(int teacherclassid);

    //转义字符
    @Select("SELECT DATE_FORMAT(b.createTime,'%Y-%m-%d %h:%m:%s') as time,b.*, " +
            "c.coursename FROM job b ,teachclass c " +
            "WHERE b.teachclassid in " +
            "(SELECT a.classID FROM studentclass a " +
            "WHERE a.studentno=#{stdentid}) " +
            "AND c.ID=b.teachclassid " +
            "ORDER BY b.ID DESC")
    List<Map<String, Object>> gettaskdetail(String studentid);

    @Select("SELECT job.ID as id,teachclassid,no,title,duedate,type,job.note," +
            "DATE_FORMAT(createTime,'%Y-%m-%d %h:%m:%s') as createtime,count,jobID,studentno,score,DATE_FORMAT(time,'%Y-%m-%d %h:%m:%s'),status " +
            "FROM job LEFT JOIN score ON job.ID = score.jobID and score.studentno = #{studentno} " +
            "WHERE teachclassid = #{teachclassid} ORDER BY no DESC")
    List<Map<String, Object>> getstudentjob(String studentno, Integer teachclassid);



}