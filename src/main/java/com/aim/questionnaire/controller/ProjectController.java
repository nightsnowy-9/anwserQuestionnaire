package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.service.ProjectService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;


    /**
     * 查询全部项目的信息
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody(required = false) ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Object> result = projectService.queryProjectList(projectEntity);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(result);
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        int result = projectService.deleteProjectById(projectEntity);
        if (result == -1) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.PROJECT_EXIST_MESSAGE);
        } else if (result == 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.QUESTION_EXIST_MESSAGE);
        } else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        int result = projectService.addProjectInfo(projectEntity, projectEntity.getCreatedBy());
        if (result == 0) {
            httpResponseEntity.setMessage(Constans.PROJECT_NAME_EXIT_MESSAGE);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
        } else {
            httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        int result = projectService.modifyProjectInfo(projectEntity, projectEntity.getLastUpdatedBy());

        if (result == -1) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.PROJECT_EXIST_MESSAGE);
        } else if (result == 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.QUESTION_EXIST_MESSAGE);
        } else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    @RequestMapping(value = "/queryAllProjectName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProjectName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
}
