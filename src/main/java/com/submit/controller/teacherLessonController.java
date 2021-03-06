package com.submit.controller;

import com.submit.pojo.teachclass;
import com.submit.service.teacherService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/teacher")
@Controller
public class teacherLessonController {

    @Autowired(required = false)
    teacherService teacherService;

    @ResponseBody
    @PostMapping(value = "/updatetechclass", produces = {"text/plain;charset=utf-8"})
    public String updateteachclass(String teachclassno,String coursename,String coursesemester
            ,String credit,String id,String evalmethod)
    {
        try {
            teachclass teachclass = teacherService.getteacherclassbyid(id);
            teachclass.setCoursename(coursename);
            teachclass.setTeachclassno(teachclassno);
            teachclass.setCoursesemester(coursesemester);
            teachclass.setCredit(Byte.parseByte(credit));
            teachclass.setEvalmethod(evalmethod);
            teacherService.updateteachclass(teachclass);
            return "更新成功";
        }
        catch (Exception e)
        {e.printStackTrace();return "更新失败";}
    }

    @ResponseBody
    @GetMapping(value = "/getteacherclassthisterm")
    public List<teachclass> getteacherclassthisterm(HttpServletRequest request)
    {
        try {
            String teacherid = (String) request.getSession().getAttribute("teacherid");
            if(teacherid == null || teacherid.isEmpty()){
                teacherid = (String) SecurityUtils.getSubject().getSession().getAttribute("name");
            }
            List<teachclass> list = teacherService.getteacherclassthisterm(teacherid);
            return list;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @GetMapping(value = "/getteacherclass")
    public List<teachclass> getteacherclass(HttpServletRequest request)
    {
        try {
            String teacherid = (String) request.getSession().getAttribute("teacherid");
            List<teachclass> list = teacherService.getteachcassbyteacheridall(teacherid);
            return list;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping(value = "/addtechclass", produces = {"text/plain;charset=utf-8"})
    public String addteachclass(String teachclassno,String coursename,String coursesemester
            ,String credit,String evalmethod,HttpServletRequest request)
    {
        try {
            teachclass teachclass = new teachclass();
            teachclass.setCoursename(coursename);
            teachclass.setTeachclassno(teachclassno);
            teachclass.setCoursesemester(coursesemester);
            teachclass.setCredit(Byte.parseByte(credit));
            teachclass.setEvalmethod(evalmethod);
            teachclass.setTeacherno((String)request.getSession().getAttribute("teacherid"));
            teacherService.addeachclass(teachclass);
            return "添加成功";
        }
        catch (Exception e)
        {e.printStackTrace();return "添加失败";}
    }

    @ResponseBody
    @PostMapping(value = "/deleteteachclassbyid", produces = {"text/plain;charset=utf-8"})
    public String deleteteachclassbyid(String id)
    {
        try {
            teacherService.deleteteachclassbyid(Integer.parseInt(id));
            return "删除成功";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "删除失败";
        }
    }
}
