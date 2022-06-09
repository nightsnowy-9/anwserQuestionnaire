package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
//import com.aim.questionnaire.config.shiro.SysUserService;
//import com.aim.questionnaire.config.shiro.entity.UserOnlineBo;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.MyUserEntityMapper;
import com.aim.questionnaire.dao.entity.UserEntity;
//import com.alibaba.fastjson.JSONArray;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by nightsnowy on 2022\5\30.
 */
@Service
public class MyUserService extends  UserService{
    @Autowired
    private MyUserEntityMapper userEntityMapper;

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    public List<Map<String,Object>> queryUserList(Map<String,Object> map) {
        List<Map<String,Object>> data=userEntityMapper.queryUserList(map);
        //List<Map<String,Object>> data = new ArrayList<>();
        Map<String, Object> tot = new HashMap<>();
        tot.put("length", userEntityMapper.queryUserListLength(map));
        data.add(tot);
        return data;
    }
}
