package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;

    static String modifyOk = "5";

    /**
     * 添加问卷
     *
     * @param questionnaireEntity
     * @return
     */
    public int addQuestionnaireInfo(QuestionnaireEntity questionnaireEntity, String user) {
        String id = UUIDUtil.getOneUUID();
        Date date = DateUtil.getCreateTime();
        questionnaireEntity.setId(id);
        questionnaireEntity.setCreatedBy(user);
        questionnaireEntity.setLastUpdatedBy(user);
        questionnaireEntity.setCreationDate(date);
        questionnaireEntity.setLastUpdateDate(date);
        return questionnaireEntityMapper.insertSelective(questionnaireEntity);
    }

    /**
     * 查询项目列表
     *
     * @param questionnaireEntity
     * @return
     */
    public List<Object> queryQuestionnaireList(QuestionnaireEntity questionnaireEntity, String projectID) {
        List<Object> resultList = new ArrayList<Object>();
        if ("".equals(questionnaireEntity.getQuestionName())) {
            questionnaireEntity.setQuestionName(null);
        }

        List<Map<String, Object>> result = questionnaireEntityMapper.queryQuestionListByProjectId(projectID);
        for (Map<String, Object> Obj : result) {
            resultList.add(Obj);
        }
        resultList.add(questionnaireEntityMapper.selectQuestionCountByProjectId(projectID));
        return resultList;
    }


    public int deleteQuestionnaireById(QuestionnaireEntity projectEntity) {
        return questionnaireEntityMapper.deleteByPrimaryKey(projectEntity.getId());
    }


    public int modifyQuestionnaire(HashMap<String, Object> map) {
        return questionnaireEntityMapper.modifyQuestionnaire(map);
    }

    public String queryQuestionnaireStatus(String questionId) {
        return questionnaireEntityMapper.queryQuestionnaireIsStopStatus(questionId);
    }

    public boolean checkModify(String questionId) {
        String res = queryQuestionnaireStatus(questionId);
        return res.equals("5") || res.equals("4");
    }

    public boolean checkModify(HashMap<String, Object> map) {
        String questionId = (String) map.get("questionId");
        return checkModify(questionId);
    }

    public int modifyQuestionnaireInfo(QuestionnaireEntity questionnaireEntity) {
        return questionnaireEntityMapper.modifyQuestionnaireInfo(questionnaireEntity);
    }

    public int addSendQuestionnaire(HashMap<String,Object> map){
        return 1;
    }

    public QuestionnaireEntity selectByPrimaryKey(String id){
        return questionnaireEntityMapper.selectByPrimaryKey(id);
    }
}

