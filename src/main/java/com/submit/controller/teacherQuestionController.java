package com.submit.controller;

import com.submit.api.response.ApiResult;
import com.submit.component.token.CurrentUser;
import com.submit.exception.ApiException;
import com.submit.model.User;
import com.submit.pojo.job;
import com.submit.pojo.question;
import com.submit.service.questionService;
import com.submit.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RequestMapping(value = "/teacher")
@Controller
public class teacherQuestionController {

    @Autowired(required = false)
    questionService questionService;
    @Autowired(required = false)
    teacherService teacherService;

    @ResponseBody
    @GetMapping(value = "getallquestions")
    public Map<String, Object> getquestionsno(Integer jobid) {
        try {
            List<question> list = questionService.selectbyjobid(jobid);
            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("code", "0");
            map.put("count", list.size());
            map.put("data", list);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping(value = "/setquestion", produces = {"text/plain;charset=utf-8"})
    public String setquestion(question question) {
        try {
            if (questionService.updatequestion(
                    question.getIdquestion()
                    , question.getContent()
                    , question.getType()
                    , question.getNo()
                    , question.getAnswer()
                    , question.getOption()) > 0)
                return "修改成功";
            else return "无修改";
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }
    }

    @ResponseBody
    @PostMapping(value = "/addonequestion", produces = {"text/plain;charset=utf-8"})
    public String setquestion(Integer idjob) {
        try {
            if (questionService.addquestion(idjob))
                return "添加成功";
            else return "无添加";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
    }

    @ResponseBody
    @PostMapping(value = "/deletequestion", produces = {"text/plain;charset=utf-8"})
    public String deletequestion(Integer idquestion) {
        try {
            System.out.println(idquestion);
            if (questionService.deletequestion(idquestion) > 0)
                return "删除成功";
            else return "无删除";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @ResponseBody
    @GetMapping(value = "/getanswer")
    public Map<String, Object> getanswer(Integer jobid,String studentno) {
        try {
            List<Map<String,Object>> list = questionService.selectonesjobanswer(studentno,jobid);
            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("code", "0");
            map.put("count", list.size());
            map.put("data", list);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping(value = "/updatestatus", produces = {"text/plain;charset=utf-8"})
    public String updatestatus(Integer scoreid) {
        try {
            if (teacherService.updatejobstatus(scoreid) > 0)
                return "批阅成功";
            else return "无批阅";
        } catch (Exception e) {
            e.printStackTrace();
            return "批阅失败";
        }
    }

    @ResponseBody
    @PostMapping(value = "/updateallstatus", produces = {"text/plain;charset=utf-8"})
    public String updateallstatus(Integer jobid) {
        try {
            int count = teacherService.updatealljobstatus(jobid);
            if (count > 0)
                return "一键批阅"+count+"份";
            else return "无批阅";
        } catch (Exception e) {
            e.printStackTrace();
            return "批阅失败";
        }
    }
}
