package com.aim.questionnaire.dao;

import com.aim.questionnaire.dao.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MyUserEntityMapper extends UserEntityMapper{
    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    List<Map<String,Object>> queryUserList(Map<String,Object> map);
    /**
     * 查询用户列表长度
     * @param map
     * @return
     */
    int queryUserListLength(Map<String,Object> map);
}
