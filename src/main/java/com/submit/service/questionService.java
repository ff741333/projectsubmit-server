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

    public List<Map<String,Object>> selectonesjobanswer(String username, Integer idjob){
        return questionMapper.selectonesjobanswer(username,idjob);
    }

    public boolean insertorupdateanswer (String username,Integer idquestion, String youranswer){
        return questionMapper.insertorupdateanswer(username,idquestion,youranswer);
    }

    public int updatequestion(Integer idquestion, String content, Integer type, Integer no, String answer, Integer option){
        if(type == 1) //填空题
            return questionMapper.updatequestion1(idquestion,content,type,no,answer);
        else if(type == 2) //选择题
            return questionMapper.updatequestion2(idquestion,content,type,no,answer,option);
        else
            return -1;
    }

    public boolean addquestion(Integer idjob){
        List<question> list = questionMapper.selectbyjobid(idjob);
        int i = 0;
        for (question x:list){
            if(i<x.getNo())
                i=x.getNo();
        }
        i++;
        return questionMapper.insertquestion(idjob,i);
    }

    public int deletequestion(Integer idquestion){
        return questionMapper.deletequestionbyid(idquestion);
    }
}
