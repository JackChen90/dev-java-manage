/**
 * @author jackie chen
 * @create 2018/01/29
 * @description userInfo js
 */
var userRole = {
    contextPath: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/userRole/operationData",
    //用户角色信息数据url
    queryUrl: "/userRole/queryData",
    //新增/编辑/删除数据url
    editUrl: "/userRole/operateUserRoleData",
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
    createUserRoleGrid: function () {
        var url = userRole.contextPath + userRole.queryUrl;
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
            colNames: ["id", "用户id", "角色id", "角色名称", "描述", "创建时间", "创建人", "更新时间", "更新人", "角色状态"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "userId",
                index: "user_id",
                width: 60,
                editable: true,
                hidden: true,
                editrules: {
                    required: true
                },
                formoptions: {label: '用户名称<font color=\'red\'> *</font>'}
            }, {
                name: "roleId",
                index: "role_id",
                width: 60,
                editable: true,
                hidden: true,
                editrules: {
                    required: true
                },
                formoptions: {label: '角色名称<font color=\'red\'> *</font>'}
            }, {
                name: "roleName",
                index: "roleName",
                editable: false,
                width: 80
            }, {
                name: "description",
                index: "description",
                editable: false,
                width: 120
            }, {
                name: "createTime",
                index: "createTime",
                editable: false,
                width: 100,
                formatter: "date"
            }, {
                name: "createUser",
                index: "createUser",
                editable: false,
                width: 100
            }, {
                name: "updateTime",
                index: "updateTime",
                editable: false,
                width: 100,
                formatter: "date"
            }, {
                name: "updateUser",
                index: "updateUser",
                editable: false,
                width: 100
            }, {
                name: "delFlag",
                index: "delFlag",
                editable: false,
                width: 60,
                formatter: userRole.convertDel
            }],
            pager: "#pager_list",
            viewrecords: true,
            grouping: true,
            groupingView: {
                groupField: ['userId'],
                groupDataSorted: true
            },
            caption: "用户权限信息管理",
            editurl: userRole.contextPath + userRole.editUrl,
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //初始化数据
        userRole.createUserRoleGrid();
        //初始化操作数据
        var operationUrl = userRole.contextPath + userRole.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, userRole.naviConfig);
    },
    naviConfig: function (data) {
        $("#user_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                reloadAfterSubmit: true,
                beforeSubmit: function (postdata, formid) {
                    return [true, ''];
                }
            },
            {//add option
                reloadAfterSubmit: true,
                beforeSubmit: function (postdata, formid) {
                    return [true, ''];
                }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_wrapper = $(".jqGrid_wrapper");
            var _user_list = $("#user_list");
            var width = _jqGrid_wrapper.width();
            _user_list.setGridWidth(width);
            var height = _jqGrid_wrapper.height();
            _user_list.setGridHeight(height - 116);
        })
    }
};
