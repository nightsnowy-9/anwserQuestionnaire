//package com.aim.questionnaire.controller;
//
//import java.util.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aim.questionnaire.beans.HttpResponseEntity;
//import com.aim.questionnaire.common.Constans;
//import com.aim.questionnaire.dao.UserEntityMapper;
//import com.aim.questionnaire.dao.entity.UserEntity;
//import com.aim.questionnaire.service.MyUserService;
//import com.github.pagehelper.PageInfo;
//
//
///**
// * Created by nightsnowy on 2022\5\30.
// */
//@RestController
//@RequestMapping("/admin")
//public class MyUserController extends UserController{
//
//    private final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private MyUserService userService;
//
//    @Autowired
//    private UserEntityMapper userEntityMapper;
//
//    /**
//     * 查询用户列表（模糊搜索）
//     * @param map
//     * @return
//     */
//    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST, headers = "Accept=application/json")
//    public HttpResponseEntity queryUserList(@RequestBody Map<String,Object> map) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        List<Map<String,Object>> data = userService.queryUserList(map);
//        data.add(userService.queryUserListLength(map));
//        httpResponseEntity.setData(data);
//        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//        return httpResponseEntity;
//    }
//}
