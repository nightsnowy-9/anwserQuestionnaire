package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
//import com.aim.questionnaire.config.shiro.SysUserService;
//import com.aim.questionnaire.config.shiro.entity.UserOnlineBo;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.UserEntity;
//import com.alibaba.fastjson.JSONArray;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * Created by wln on 2018\8\9 0009.
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    //@Autowired
    //private SysUserService sysUserService;

    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    public List<Map<String,Object>> queryUserList(Map<String,Object> map) {
//        Map<String,Object> user = new HashMap<>();
//        user.put("id", 1);
//        user.put("username", "admin");
//        user.put("password", "1");
//        user.put("startTime", new Date());
//        user.put("stopTime", new Date());
//        user.put("status", 1);
//        List<Map<String,Object>> data = new ArrayList<>();
//        data.add(user);
        List<Map<String,Object>> data=userEntityMapper.queryUserList(map);
//        System.out.println(data);
//        for(Map<String,Object> m: data){
//            System.out.println(m);
//        }

        return data;
    }

    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    @CacheEvict(value = "query-user-list",allEntries = true)
    public int addUserInfo(Map<String,Object> map) {

        if(map.get("username") != null) {
            int userResult = userEntityMapper.queryExistUser(map);
            if(userResult != 0) {
                //用户名已经存在
                return 3;
            }
        }

        String id = UUIDUtil.getOneUUID();
        map.put("id",id);
        //创建时间
        Date date = DateUtil.getCreateTime();
        map.put("creationDate",date);
        map.put("lastUpdateDate",date);
        //创建人
        String user = "admin";
        map.put("createdBy",user);
        map.put("lastUpdatedBy",user);
        //前台传入的时间戳转换
        String startTimeStr = map.get("startTime").toString();
        String endTimeStr = map.get("stopTime").toString();
        Date startTime = DateUtil.getMyTime(startTimeStr);
        Date endTime = DateUtil.getMyTime(endTimeStr);
        map.put("startTime",startTime);
        map.put("stopTime",endTime);

        int result = userEntityMapper.addUserInfo(map);
        return result;
    }

    /**
     * 编辑用户的基本信息
     * @param map
     * @return
     */
    @CacheEvict(value = "query-user-list",allEntries = true)
    public int modifyUserInfo(Map<String, Object> map) {

        return 0;
    }

    /**
     * 修改用户状态
     * @param map
     * @return
     */
    @CacheEvict(value = "query-user-list",allEntries = true)
    public int modifyUserStatus(Map<String, Object> map) {

        return 0;
    }

    /**
     * 根据id查询用户信息
     * @param userEntity
     * @return
     */
//    @Cacheable("select-user-info-by-id")
    public Map<String,Object> selectUserInfoById(UserEntity userEntity) {

        return null;
    }

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    @CacheEvict(value = "query-user-list",allEntries = true)
    public int deteleUserInfoById(UserEntity userEntity) {
        int result = userEntityMapper.deteleUserInfoById(userEntity);
        return result;
    }
}
