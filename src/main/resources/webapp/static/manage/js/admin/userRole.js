/**
 * @author jackie chen
 * @create 2018/01/29
 * @description userInfo js
 */
var userRole = {
    contextPath: null,
    //状态值，用户id column编辑时需要disabled
    needDisable: true,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/admin/userRole/operationData",
    //用户角色信息数据url
    queryUrl: "/admin/userRole/queryData",
    //新增/编辑/删除数据url
    editUrl: "/admin/userRole/operateUserRoleData",
    //查询用户列表信息
    queryUsersUrl: "/admin/user/queryUser4Select",
    //查询角色信息
    queryRolesUrl: "/admin/role/queryRole4Select",
    //校验用户角色url
    checkUserRoleUrl: "/admin/userRole/checkUserRole",
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
        $("#user_role_list").jqGrid({
            url: url,
            datatype: "json",
            height: height - 116,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 20,
            rowList: [20, 50, 100],
            rownumbers: true,//group状态下为false
            //表格json数据
            jsonReader: {
                repeatitems: false,
                id: "id",
                subgrid: {
                    repeatitems: false
                }
            },
            colNames: ["用户id", "用户名称", "角色id", "角色名称", "描述", "创建时间", "创建人", "更新时间", "更新人", "角色状态"],
            colModel: [{
                name: "userId",
                index: "user_id",
                width: 60,
                editable: true,
                edittype: "select",
                editoptions: {
                    dataUrl: userRole.contextPath + userRole.queryUsersUrl,
                    buildSelect: function (data) {
                        // var field_id = this.id;
                        var data = typeof data === "string" ?
                            $.parseJSON(data) : data,
                            s = "<select disabled='disabled'>";
                        $.each(data.data, function () {
                            s += '<option value="' + this.id + '">' + this.userName +
                                '</option>';
                        });
                        //定时任务（其实应该也可以写回调方法），用户id 编辑时disable
                        setTimeout(function () {
                            if (userRole.needDisable) {
                                // $('#tr_' + field_id).attr('disabled', 'disabled');
                                $('#userId').attr('disabled', 'disabled');
                            } else {
                                $('#userId').removeAttr('disabled');
                            }
                        }, 50);
                        return s + "</select>";
                    }
                },
                hidden: true,
                editrules: {
                    required: true,
                    edithidden: true
                },
                formoptions: {label: '用户名称<font color=\'red\'> *</font>'}
            }, {
                name: "userName",
                index: "user_name",
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "roleId",
                index: "role_id",
                width: 60,
                editable: true,
                hidden: true,
                editrules: {
                    required: true,
                    edithidden: true
                },
                edittype: "select",
                editoptions: {
                    dataUrl: userRole.contextPath + userRole.queryRolesUrl,
                    buildSelect: function (data) {
                        // var field_id = this.id;
                        var data = typeof data === "string" ?
                            $.parseJSON(data) : data,
                            s = "<select disabled='disabled'>";
                        $.each(data.data, function () {
                            s += '<option value="' + this.id + '">' + this.roleName +
                                '</option>';
                        });
                        return s + "</select>";
                    }
                },
                formoptions: {label: '角色名称<font color=\'red\'> *</font>'}
            }, {
                name: "roleName",
                index: "role_name",
                editable: false,
                width: 80
            }, {
                name: "description",
                index: "ur.description",
                editable: false,
                width: 180
            }, {
                name: "createTime",
                index: "ur.create_time",
                editable: false,
                width: 100,
                formatter: "date"
            }, {
                name: "createUser",
                index: "ur.create_user",
                editable: false,
                width: 100
            }, {
                name: "updateTime",
                index: "ur.update_time",
                editable: false,
                width: 100,
                formatter: "date"
            }, {
                name: "updateUser",
                index: "ur.update_user",
                editable: false,
                width: 100
            }, {
                name: "delFlag",
                index: "ri.del_flag",
                editable: false,
                width: 60,
                search: false,
                formatter: userRole.convertDel
            }],
            pager: "#pager_list",
            viewrecords: true,
            grouping: true,
            groupingView: {
                groupField: ['userName'],
                groupOrder: ['asc'],
                groupText: ['<b>{0} - 共{1}角色 </b>'],
                groupDataSorted: true,
                groupColumnShow: [false]
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
    checkUserRoleCallback: function (data) {
        if (data) {
            return [true, ''];
        } else {
            return [false, '已为该用户配置了此权限'];
        }
    },
    checkUserRole: function (userId, roleId) {
        if (!userId || !roleId) {
            return;
        }
        //调用校验用户角色服务
        return ajaxPostJson(
            userRole.contextPath + userRole.checkUserRoleUrl + "?userId=" + userId + "&roleId=" + roleId,
            false,
            null, userRole.checkUserRoleCallback);
    },
    naviConfig: function (data) {
        $("#user_role_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeSubmit: function (postdata, formid) {
                    //校验用户角色
                    return userRole.checkUserRole(postdata.userId, postdata.roleId);
                },
                beforeShowForm: function (formid) {
                    //编辑时，userId column需要disable
                    // $('#userId',formid).attr('readonly','readonly');
                    userRole.needDisable = true;
                }
            },
            {//add option
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                beforeSubmit: function (postdata, formid) {
                    return userRole.checkUserRole(postdata.userId, postdata.roleId);
                },
                beforeShowForm: function (formid) {
                    //新增时，userId column不需要disable
                    userRole.needDisable = false;
                }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});//搜索条件限制
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_wrapper = $(".jqGrid_wrapper");
            var _user_role_list = $("#user_role_list");
            var width = _jqGrid_wrapper.width();
            _user_role_list.setGridWidth(width);
            var height = _jqGrid_wrapper.height();
            _user_role_list.setGridHeight(height - 116);
        })
    }
};
