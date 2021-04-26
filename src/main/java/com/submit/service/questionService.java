package com.submit.service;

import com.submit.dao.questionMapper;
import com.submit.pojo.question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class questionService {
    private static final Logger logger = LoggerFactory.getLogger(studentService.class);
    @Autowired(required = false)
    questionMapper questionMapper;

    public question selectbyjobidandno(Integer idjob, Integer no){
        return questionMapper.selectbyjobidandno(idjob,no);
    }

    public List<question> selectbyjobid(Integer idjob){
        return questionMapper.selectbyjobid(idjob);
    }

    public Map<String, Object> selectonesqustion(String username, Integer idquestion){
        return questionMapper.selectonesqustion(username,idquestion);
    }
}
