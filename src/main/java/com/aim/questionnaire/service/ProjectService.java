package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.ProjectEntityMapper;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wln on 2018\8\6 0006.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;


    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    public int addProjectInfo(ProjectEntity projectEntity, String user) {
        String id = UUIDUtil.getOneUUID();
        Date date = DateUtil.getCreateTime();
        projectEntity.setId(id);
        projectEntity.setCreatedBy(user);
        projectEntity.setLastUpdatedBy(user);
        projectEntity.setCreationDate(date);
        projectEntity.setLastUpdateDate(date);
        return projectEntityMapper.insertSelective(projectEntity);
    }

    /**
     * 修改项目
     *
     * @param projectEntity
     * @return
     */
    public int modifyProjectInfo(ProjectEntity projectEntity, String user) {
        Date date = DateUtil.getCreateTime();
        projectEntity.setLastUpdateDate(date);
        projectEntity.setLastUpdatedBy(user);
        return projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
    }

    /**
     * 删除项目
     *
     * @param projectEntity
     * @return
     */
    public int deleteProjectById(ProjectEntity projectEntity) {
//        System.out.println(projectEntity.getId());
        return projectEntityMapper.deleteProjectById(projectEntity.getId());
    }

    /**
     * 查询项目列表
     *
     * @param projectEntity
     * @return
     */
    public List<Object> queryProjectList(ProjectEntity projectEntity) {
        List<Object> resultList = new ArrayList<Object>();
        if ("".equals(projectEntity.getProjectName())) {
            projectEntity.setProjectName(null);
        }
        List<Map<String, Object>> result = projectEntityMapper.queryProjectList(projectEntity);
        for (Map<String, Object> Obj : result) {
            resultList.add(Obj);
        }
        return resultList;
    }

    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    public List<Map<String, Object>> queryAllProjectName() {

        return null;
    }
}
