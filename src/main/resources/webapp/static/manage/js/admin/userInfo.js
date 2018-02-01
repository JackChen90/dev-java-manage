/**
 * @author jackie chen
 * @create 2018/01/29
 * @description userInfo js
 */
var user = {
    contextPath: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/user/operationData",
    //用户信息数据url
    queryUrl: "/user/queryData",
    convertDel: function (cellvalue, options, rowObject) {
        var newCellValue = null;
        switch (cellvalue) {
            case "0":
            default:
                newCellValue = "<font color='green'>正常</font>";
                break;
            case "1":
                newCellValue = "<font color='red'>停用</font>";
                break;
        }
        return newCellValue;
    },
    createUserGrid: function () {
        var url = user.contextPath + user.queryUrl;
        var height = $(".jqGrid_wrapper").height();
        // $("#user_list").setGridHeight(height);
        $("#user_list").jqGrid({
            url: url,
            datatype: "json",
            height: height - 116,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 20,
            rowList: [20, 50, 100],
            rownumbers: true,
            //表格json数据
            jsonReader: {
                repeatitems: false,
                id: "id",
                subgrid: {
                    repeatitems: false
                }
            },
            colNames: ["id", "用户名", "真实姓名", "密码", "电话", "描述", "创建时间", "创建人", "更新时间", "更新人", "状态"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "userName",
                index: "user_name",
                editable: true,
                width: 90
            }, {
                name: "realName",
                index: "realName",
                editable: true,
                width: 100
            }, {
                name: "password",
                index: "password",
                editable: true,
                width: 80
            }, {
                name: "phone",
                index: "phone",
                editable: true,
                width: 100
            }, {
                name: "description",
                index: "description",
                editable: true,
                width: 120
            }, {
                name: "createTime",
                index: "createTime",
                editable: true,
                width: 100,
                formatter: "date"
            }, {
                name: "createUser",
                index: "createUser",
                editable: true,
                width: 100
            }, {
                name: "updateTime",
                index: "updateTime",
                editable: true,
                width: 100,
                formatter: "date"
            }, {
                name: "updateUser",
                index: "updateUser",
                editable: true,
                width: 100
            }, {
                name: "delFlag",
                index: "delFlag",
                editable: true,
                width: 60,
                formatter: user.convertDel
            }],
            pager: "#pager_list",
            viewrecords: true,
            caption: "用户信息管理",
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //初始化数据
        user.createUserGrid();
        //初始化操作数据
        var operationUrl = user.contextPath + user.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, user.naviConfig);
    },
    naviConfig: function (data) {
        $("#user_list").jqGrid("navGrid", "#pager_list", data, {reloadAfterSubmit: true},
            {},
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});
    },
    resize: function () {
        $(window).bind("resize", function () {
            var width = $(".jqGrid_wrapper").width();
            $("#user_list").setGridWidth(width);
            var height = $(".jqGrid_wrapper").height();
            $("#user_list").setGridHeight(height - 116);
        })
    }
};
