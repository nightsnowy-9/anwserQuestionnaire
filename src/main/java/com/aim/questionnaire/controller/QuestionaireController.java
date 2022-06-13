package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.ProjectService;
import com.aim.questionnaire.service.QuestionnaireService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class QuestionaireController {
    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 添加问卷
     *
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        int result = questionnaireService.addQuestionnaireInfo(questionnaireEntity, questionnaireEntity.getCreatedBy());
        httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);

        return httpResponseEntity;
    }

    /**
     * 删除问卷
     *
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/deleteQuestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQuestionnaireById(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        int result = questionnaireService.deleteQuestionnaireById(questionnaireEntity);
        if (result == 0) {

            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.COPY_EXIT_DELETE_MESSAGE);
        } else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(result);
            httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     * 查询全部问卷的信息
     *
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/queryQuestionnaireList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireList(@RequestBody(required = false) QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Object> result = questionnaireService.queryQuestionnaireList(questionnaireEntity, questionnaireEntity.getProjectId());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(result);
        return httpResponseEntity;
    }

    /**
     * 查询单个问卷的信息
     *
     * @param questionnaireEntity
     * @return
     */
    @RequestMapping(value = "/queryQuestionnaireStatus", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireStatus(@RequestBody(required = false) QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String result = questionnaireService.queryQuestionnaireStatus(questionnaireEntity.getId());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(result);
        return httpResponseEntity;
    }



    @PostMapping("/modifyQuestionnaire")
    HttpResponseEntity modifyQuestionnaire(@RequestBody HashMap<String, Object> map) {
        HttpResponseEntity response = new HttpResponseEntity();
        if (questionnaireService.checkModify(map)) {
            map.put("questionList", JSON.toJSONString(map.get("questionList")));
            questionnaireService.modifyQuestionnaire(map);
            response.setCode(Constans.SUCCESS_CODE);
            response.setMessage(Constans.UPDATE_MESSAGE);
        } else {
            response.setCode(Constans.EXIST_CODE);
            response.setMessage(Constans.COPY_EXIT_MODIFY_MESSAGE);
        }
        return response;
    }

    @PostMapping("/changeQuestionnaireStatus")
    HttpResponseEntity changeQuestionnaireStatus(@RequestBody HashMap<String, Object> map) {
        HttpResponseEntity response = new HttpResponseEntity();
        if (true) {
            questionnaireService.modifyQuestionnaire(map);
            response.setCode(Constans.SUCCESS_CODE);
            response.setMessage(Constans.UPDATE_MESSAGE);
        } else {
            response.setCode(Constans.SUCCESS_CODE);
            response.setMessage(Constans.QUESTION_EXIST_MESSAGE);
        }
        return response;
    }


    @PostMapping("/modifyQuestionnaireInfo")
    HttpResponseEntity modifyQuestionnaireInfo(@RequestBody QuestionnaireEntity questionnaireEntity) {
        HttpResponseEntity response = new HttpResponseEntity();
        questionnaireEntity.setLastUpdateDate(new Date());
        if (questionnaireService.checkModify(questionnaireEntity.getId())) {
            questionnaireService.modifyQuestionnaireInfo(questionnaireEntity);
            response.setCode(Constans.SUCCESS_CODE);
            response.setMessage(Constans.UPDATE_MESSAGE);
        } else {
            response.setCode(Constans.SUCCESS_CODE);
            response.setMessage(Constans.QUESTION_EXIST_MESSAGE);
        }
        return response;
    }



    @PostMapping("/addSendQuestionnaire")
    HttpResponseEntity addSendQuestionnaire(@RequestBody HashMap<String, Object> map) {
        HttpResponseEntity response = new HttpResponseEntity();

        questionnaireService.addSendQuestionnaire(map);

        response.setCode(Constans.SUCCESS_CODE);
        response.setMessage(Constans.ADD_MESSAGE);

        return response;
    }
}
