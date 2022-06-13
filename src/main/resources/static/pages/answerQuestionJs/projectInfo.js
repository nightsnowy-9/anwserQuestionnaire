/**
 * Created by Amy on 2018/8/7.
 */
$(function () {
    console.log(1);
    isLoginFun();
    header();
    $("#ctl01_lblUserName").text(getCookie('userName'));
    getProjectInfo();
    var oTable = new TableInit();
    oTable.Init();
});

// 查看项目详细信息
function getProjectInfo() {
    var projectId = getCookie('projectId');

    var url = '/queryProjectList';
    var data = {
        "id": projectId
    };
    commonAjaxPost(true, url, data, getProjectInfoSuccess);

}

// 查看项目详细信息成功
function getProjectInfoSuccess(result) {
    // //console.log(result)
    if (result.code == "666") {
        var projectInfo = result.data[0];
        $("#projectNameSpan").text(projectInfo.projectName);
        $("#createTimeSpan").text(projectInfo.createDate.replace(/-/g,'/'));
        $("#adminSpan").text(projectInfo.createdBy);
        $("#projectContentSpan").text(projectInfo.projectContent);

        var text = "";
            text += "<tr>";
            text += "    <td style=\"text-align: center;color: #d9534f\" colspan=\"4\">暂无调查问卷</td>";
            text += "</tr>";
        $("#questTableBody").empty();
        $("#questTableBody").append(text)

    } else if (result.code == "333") {
        layer.msg(result.message, {icon: 2});
        setTimeout(function () {
            window.location.href = 'login.html';
        }, 1000)
    } else {
        layer.msg(result.message, {icon: 2})
    }
}



//回车事件
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        getQuestionnaireList();
    }
});

function getQuestionnaireList() {
    $("#questionnaireTable").bootstrapTable('refresh');
}

function TableInit() {

    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#questionnaireTable').bootstrapTable({
            url: httpRequestUrl + '/queryQuestionnaireList',         //请求后台的URL（*）
            method: 'POST',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            dataType: 'json',
            paginationShowPageGo: true,
            showJumpto: true,
            pageNumber: 1, //初始化加载第一页，默认第一页
            queryParams: queryParams,//请求服务器时所传的参数
            sidePagination: 'server',//指定服务器端分页
            pageSize: 10,//单页记录数
            pageList: [10, 20, 30, 40],//分页步进值
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            silent: true,
            showRefresh: false,                  //是否显示刷新按钮
            showToggle: false,
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列

            columns: [{
                checkbox: true,
                visible: false
            }, {
                field: 'id',
                title: '序号',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
                {
                    field: 'questionName',
                    title: '试卷名称',
                    align: 'center',
                    width: '230px'
                },
                {
                    field: 'endTime',
                    title: '结束时间',
                    align: 'center'
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,//给按钮注册事件
                    formatter: addFunctionAlty//表格中增加按钮
                }],
            responseHandler: function (res) {
                //console.log(res);
                if(res.code == "666"){
                    var questionnaireInfo = res.data;
                    var qLength = questionnaireInfo.pop();
                    //var questionnaireInfo=JSON.parse('[{"password":"1","startTime":"2022-05-12T10:09:28","id":"1","endTime":"2022-05-12T10:09:30","username":"aa","status":"1"},{"password":"123","startTime":"2022-05-12T12:10:37","id":"290e08f3ea154e33ad56a18171642db1","endTime":"2022-06-11T12:10:37","username":"aaa","status":"1"},{"password":"1","startTime":"2018-10-24T09:49:00","id":"8ceeee2995f3459ba1955f85245dc7a5","endTime":"2025-11-24T09:49:00","username":"admin","status":"1"},{"password":"aa","startTime":"2022-05-16T12:01:54","id":"a6f15c3be07f42e5965bec199f7ebbe6","endTime":"2022-06-15T12:01:54","username":"aaaaa","status":"1"}]');
                    var NewData = [];
                    //if (1) {
                    if (questionnaireInfo.length) {
                        //for (var i = 0; i < 1; i++) {
                        for (var i = 0; i < questionnaireInfo.length; i++) {
                            var dataNewObj = {
                                'id': '',
                                "questionName": '',
                                "endTime": '',
                                'questionStop': ''
                            };

                            dataNewObj.qID = questionnaireInfo[i].id;
                            dataNewObj.questionName = questionnaireInfo[i].questionName;
                            dataNewObj.questionContent = questionnaireInfo[i].questionContent;
                            dataNewObj.endTime = questionnaireInfo[i].endTime;//.substring(0,19);
                            dataNewObj.creationDate = questionnaireInfo[i].creationDate;
                            dataNewObj.dataId = questionnaireInfo[i].dataId;
                            dataNewObj.questionStop = questionnaireInfo[i].questionStop;
                            NewData.push(dataNewObj);
                        }
                        //console.log(NewData)
                    }
                    var data = {
                        total: qLength,
                        rows: NewData
                    };

                    return data;
                }

            }

        });
    };

    // 得到查询的参数
    function queryParams(params) {
        //var userName1 = $("#keyWord").val();
        //console.log(userName1);
        //var projectId = getCookie('projectId');
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            userName: getCookie("userName"),
            createdBy: getCookie("userName"),
            projectId: getCookie('projectId')
        };
        return JSON.stringify(temp);
    }

    return oTableInit;
}


window.operateEvents = {
    //编辑
    'click #btn_count': function (e, value, row, index) {
        id = row.id;
        $.cookie('questionId', id);
    }
};


// 表格中按钮
function addFunctionAlty(value, row, index) {
    //console.log(row);
    var btnText = '';

    //btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"resetPassword(" + "'" + row.id + "'" + ")\" style='width: 77px;' class=\"btn btn-default-g ajax-link\">重置密码</button>&nbsp;&nbsp;";

    //btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editQuest(" + "'" + row.id + "')\" class=\"btn btn-default-g ajax-link\">编辑</button>&nbsp;&nbsp;";
    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"editQuest(" + "'" + row.qID + "','" + row.questionName + "','" + row.questionContent + "','" + row.endTime + "','" + row.creationDate +  "','" + row.dataID + "')\" class=\"btn btn-default-g ajax-link\">编辑</button>&nbsp;&nbsp;";

    btnText += "<button type=\"button\" id=\"btn_look\" onclick=\"designQuest(" + "'" + row.qID + "')\" class=\"btn btn-default-g ajax-link\">设计</button>&nbsp;&nbsp;";


    btnText += "<button type=\"button\" id=\"btn_stop" + row.id + "\" onclick=\"deleteQuestionnaire(" + "'" + row.qID + "'" + ")\" class=\"btn btn-danger-g ajax-link\">删除</button>&nbsp;&nbsp;";

    return btnText;
}


//编辑问卷
function editQuest(id, name, content, endTime, creationDate, dataId) {
//function editQuest(row) {
    var data = {
        "id": id
    };
    commonAjaxPost(true, '/queryQuestionnaireStatus', data, function (result) {
        // if (result.code == "666") {
        //     if (result.data != "5") {
        //         layer.msg('问卷已发布，不可修改', {icon: 2});
        //     } else if (result.data == "5") {
        //         deleteCookie("questionId");
        //         deleteCookie("questionName");
        //         deleteCookie("questionContent");
        //         deleteCookie("endTime");
        //         setCookie("questionId", id);
        //         setCookie("questionName", name);
        //         setCookie("questionContent", content);
        //         setCookie("endTime", endTime);
        //         setCookie("creationDate", creationDate);
        //         setCookie("dataId", dataId);
        //         window.location.href = 'editQuestionnaire.html'
        //     }
        // }
        if (result.code == "666") {
            // if (result.data == "1") {
            //     if ($("#operationAll" + m + n).children("a:first-child").text() == '开启') {
            //         judgeIfChangeStatus(m, n);
            //     }
            //     layer.msg('问卷运行中，不可修改', {icon: 2});
            // } else

            // if (result.data != "1") {
            commonAjaxPost(true, '/selectQuestSendStatus', {id: id}, function (result) {
                //发送过问卷
                if (result.code == "40003") {
                    setCookie("ifEditQuestType", "false");
                } else if (result.code == "666") {         //未发送过问卷
                    setCookie("ifEditQuestType", "true");
                }
            });
            deleteCookie("questionId");
            deleteCookie("questionName");
            deleteCookie("questionContent");
            deleteCookie("endTime");
            setCookie("questionId", id);
            setCookie("questionName", name);
            setCookie("questionContent", content);
            setCookie("endTime", endTime);
            setCookie("creationDate", creationDate);
            setCookie("dataId", dataId);
            window.location.href = 'editQuestionnaire.html'
            // }
        }

        else if (result.code == "333") {
            layer.msg(result.message, {icon: 2});
            setTimeout(function () {
                window.location.href = 'login.html';
            }, 1000)
        } else {
            layer.msg(result.message, {icon: 2})
        }
    });
}

// 删除问卷
function deleteQuestionnaire(questionnaireId) {
    layer.confirm('您确认要删除此问卷吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var url = '/deleteQuestionnaireById';
        var data = {
            "id": questionnaireId
        };
        commonAjaxPost(true, url, data, function (result) {
            // //console.log(result);
            if (result.code == "666") {
                layer.msg(result.message, {icon: 1});
                getQuestionnaireList();
            } else if (result.code == "333") {
                layer.msg(result.message, {icon: 2});
                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 1000);
            } else {
                layer.msg(result.message, {icon: 2});
            }
        });
    }, function () {
    });
}

// 设计问卷
function designQuest(id){
    deleteCookie("questionId");
    setCookie("questionId", id);
    window.location.href="designQuestionnaire.html"+"?qId=" + id ;
}
