package com.submit.service;

import com.submit.dao.adminMapper;
import com.submit.pojo.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminService {
    @Autowired(required = false)
    adminMapper adminMapper;
    public admin selectadmin(String idadmin, String password){
        return adminMapper.selectadmin(idadmin, password);
    }
}
