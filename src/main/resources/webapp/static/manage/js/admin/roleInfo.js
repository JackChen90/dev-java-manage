/**
 * @author jackie chen
 * @create 2018/01/29
 * @description roleInfo js
 */
var role = {
    contextPath: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/admin/role/operationData",
    //用户信息数据url
    queryUrl: "/admin/role/queryData",
    //新增/编辑/删除数据url
    editUrl: "/admin/role/operateRoleData",
    //校验用户名不重复
    checkRoleNameUrl: "/admin/role/checkRoleName",
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
    checkRoleNameCallback: function (data) {
        if (data) {
            return [true, ''];
        } else {
            return [false, '已存在相同用户名'];
        }
    },
    checkRoleName: function (roleName, id) {
        if (!roleName) {
            return;
        }
        var idStr = '';
        alert(id);
        if (id) {
            idStr = "&id=" + id;
        }
        //调用校验用户名服务
        return ajaxPostJson(role.contextPath + role.checkRoleNameUrl
            + "?roleName=" + roleName + idStr, false,
            null, role.checkRoleNameCallback);
    },
    createRoleGrid: function () {
        var url = role.contextPath + role.queryUrl;
        var height = $(".jqGrid_wrapper").height();
        $("#role_list").jqGrid({
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
            colNames: ["id", "角色名", "描述", "创建时间", "创建人", "更新时间", "更新人", "状态"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "roleName",
                index: "role_name",
                editable: true,
                width: 90,
                editrules: {
                    required: true
                },
                formoptions: {label: '角色名<font color=\'red\'> *</font>'}
            }, {
                name: "description",
                index: "description",
                editable: true,
                width: 160
            }, {
                name: "createTime",
                index: "create_time",
                editable: false,
                width: 100,
                formatter: "date"
            }, {
                name: "createUser",
                index: "create_user",
                editable: false,
                width: 100
            }, {
                name: "updateTime",
                index: "update_time",
                editable: false,
                width: 100,
                formatter: "date",
                editoptions: {
                    dataInit: function (elem) {
                        $(elem).datepicker();
                    }
                }
            }, {
                name: "updateUser",
                index: "update_user",
                editable: false,
                width: 100
            }, {
                name: "delFlag",
                index: "del_flag",
                editable: false,
                width: 60,
                formatter: role.convertDel
            }],
            pager: "#pager_list",
            viewrecords: true,
            caption: "角色信息管理",
            editurl: role.contextPath + role.editUrl,
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //初始化数据
        role.createRoleGrid();

        //初始化操作数据
        var operationUrl = role.contextPath + role.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, role.naviConfig);

        //初始化gird width，使得水平滚动条能显示
        role.gridResizeWidth();
    },
    naviConfig: function (data) {
        $("#role_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeSubmit: function (postdata, formid) {
                    //主键是dom元素id+id
                    return role.checkRoleName(postdata.roleName, postdata.role_list_id);
                }
            },
            {//add option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                beforeSubmit: function (postdata, formid) {
                    return role.checkRoleName(postdata.roleName);
                }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_wrapper = $(".jqGrid_wrapper");
            var _role_list = $("#role_list");
            var width = _jqGrid_wrapper.width();
            _role_list.setGridWidth(width);
            var height = _jqGrid_wrapper.height();
            _role_list.setGridHeight(height - 116);
        })
    },
    gridResizeWidth: function () {
        var _jqGrid_wrapper = $(".jqGrid_wrapper");
        var _role_list = $("#role_list");
        var width = _jqGrid_wrapper.width();
        _role_list.setGridWidth(width);
    }
};
