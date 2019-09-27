package com.cwc.service;

import com.cwc.model.User;

/**
 * @author bwh
 * @date 2019/9/18/018 - 11:37
 * @Description
 */
public class UserService {
    public boolean findUser(String openid){
        User user = User.dao.findFirst("SELECT * FROM sys_user WHERE openid = ?",openid);
        if(user==null){
            return false;
        }
        return true;
    }

    public User getUser(String openid){
        return User.dao.findFirst("SELECT * FROM sys_user WHERE openid = ?",openid);
    }

    public boolean login(String name,String pwd){
        User user = User.dao.findFirst("SELECT * FROM sys_user WHERE name = ? AND pwd = ?",name,pwd);
        if(user==null){
            return false;
        }
        return true;
    }
}
