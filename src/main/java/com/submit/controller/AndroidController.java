package com.submit.controller;

import com.submit.api.response.ApiResult;
import com.submit.api.response.LoginInfo;
import com.submit.component.token.CurrentUser;
import com.submit.component.token.LoginRequired;
import com.submit.exception.ApiException;
import com.submit.model.User;
import com.submit.pojo.job;
import com.submit.pojo.question;
import com.submit.pojo.teachclass;
import com.submit.service.questionService;
import com.submit.service.studentService;
import com.submit.utils.TokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.submit.exception.ApiException.ERROR.COMMON_BUSINESS_ERROR;

@RequestMapping("android")
@Controller
public class AndroidController {

    Logger logger= LoggerFactory.getLogger(AndroidController.class);
    @Autowired(required = false)
    studentService studentService;
    @Autowired(required = false)
    questionService questionService;


    //Android登录
    @ResponseBody
    @RequestMapping(value = "/studentloginforandroid", method = RequestMethod.POST)
    public ApiResult studentloginforandroid(String username, String password, HttpServletRequest request)  throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("role","student");
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //3.执行登录方法
        try {
            subject.login(token);
            //登录成功
            User user = new User();
            user.setLoginName(username);
            user.setPassword(password);
            ApiResult<LoginInfo> apiResult = new ApiResult<>();
            apiResult.setData(new LoginInfo()
                    .setUser(user)
                    .setToken(TokenUtils.createJwtToken(user.getLoginName())));
            request.getSession().setAttribute("studentid",username);
            return apiResult;
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            throw new ApiException("账号不存在！", COMMON_BUSINESS_ERROR);
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            throw new ApiException("用户名或密码错误！", COMMON_BUSINESS_ERROR);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new ApiException("用户名或密码错误！", COMMON_BUSINESS_ERROR);
        }
    }

    //获取课程信息
    @LoginRequired
    @ResponseBody
    @GetMapping("getcoursename")
    public ApiResult getcoursename (@CurrentUser User user)throws Exception
    {
        List<teachclass> map = studentService.getList(user.getLoginName());
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> item;
        for(teachclass x:map){
            item = new HashMap<>();
            item.put("coursename",x.getCoursename());
            item.put("teachclassid",x.getId());
            list.add(item);
        }

        return new ApiResult<List<Map<String,Object>>>().setData(list);
    }

    //获取某个课程的所有作业
    @LoginRequired
    @ResponseBody
    @GetMapping("getcoursejob")
    public ApiResult getcoursejob (@CurrentUser User user,  String teachclassid ) throws Exception
    {
        if(teachclassid == null || teachclassid.isEmpty())
            throw new ApiException("无班级！", COMMON_BUSINESS_ERROR);
        List<Map<String,Object>> list = studentService.getstudentjob(user.getLoginName(),Integer.parseInt(teachclassid));
        return new ApiResult<List<Map<String,Object>>>().setData(list);
    }

    //获取某个课程的作业详细情况
    @LoginRequired
    @ResponseBody
    @GetMapping("getquestion")
    public ApiResult getquestion(Integer idjob, Integer no) throws Exception {
        if(idjob == null || no == null)
            throw new ApiException("参数错误！",COMMON_BUSINESS_ERROR);
        question question = questionService.selectbyjobidandno(idjob, no);
        if(question == null)
            throw new ApiException("查询不到作业！",COMMON_BUSINESS_ERROR);
        return new ApiResult<question>().setData(question);
    }

    //获取某个作业的所有问题
    @LoginRequired
    @ResponseBody
    @GetMapping("getquestionsno")
    public ApiResult getquestionsno(@CurrentUser User user, Integer idjob) throws Exception {
        if(idjob == null)
            throw new ApiException("参数错误！",COMMON_BUSINESS_ERROR);
        List<question> list = questionService.selectbyjobid(idjob);
        if(list == null)
            throw new ApiException("查询不到作业！",COMMON_BUSINESS_ERROR);
        return new ApiResult<List<question>>().setData(list);
    }

    //获取某个同学的作业详细情况
    @LoginRequired
    @ResponseBody
    @GetMapping("getonesquestion")
    public ApiResult getquestion(@CurrentUser User user, Integer idquestion) throws Exception {
        if(idquestion == null)
            throw new ApiException("参数错误！",COMMON_BUSINESS_ERROR);
        Map<String,Object> map = questionService.selectonesqustion(user.getLoginName(), idquestion);
        if(map == null)
            throw new ApiException("查询不到问题！",COMMON_BUSINESS_ERROR);
        return new ApiResult<Map<String,Object>>().setData(map);
    }

    //提交作业
    @LoginRequired
    @ResponseBody
    @PostMapping("changejobstatus")
    public ApiResult changejobstatus(@CurrentUser User user, Integer idjob) throws Exception {
        if(idjob == null)
            throw new ApiException("参数错误！",COMMON_BUSINESS_ERROR);
        Boolean result = studentService.insertintojobstatus(user.getLoginName(), idjob);
        if(result==null || !result)
            throw new ApiException("提交失败！",COMMON_BUSINESS_ERROR);
        return new ApiResult<Boolean>().setData(result);
    }

    //提交或更新答案
    @LoginRequired
    @ResponseBody
    @PostMapping("answersubmit")
    public ApiResult answersubmit(@CurrentUser User user, Integer idquestion,String youranswer) throws Exception {
        if(idquestion == null || youranswer ==null)
            throw new ApiException("参数错误！",COMMON_BUSINESS_ERROR);
        Boolean result = questionService.insertorupdateanswer(user.getLoginName(), idquestion, youranswer);
        if(result==null || !result)
            throw new ApiException("提交失败！",COMMON_BUSINESS_ERROR);
        return new ApiResult<Boolean>().setData(result);
    }


}
