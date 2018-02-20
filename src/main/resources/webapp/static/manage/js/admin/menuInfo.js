/**
 * @author jackie chen
 * @create 2018/01/29
 * @description menuInfo js
 */
var menu = {
    contextPath: null,
    //所有操作权限
    allOperation: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/menu/operationData",
    //菜单信息数据url
    queryUrl: "/menu/queryData",
    //新增/编辑/删除数据url
    editUrl: "/menu/operateMenuData",
    //校验用户名不重复
    checkMenuNameUrl: "/menu/checkMenuName",
    //操作字典url
    operationMapUrl: "/operation/allOperation",
    //查询所有父节点信息
    queryParentsUrl: "/menu/queryParents4Select",
    initAllOperation: function (data) {
        menu.allOperation = data;
    },
    convertOperation: function (cellvalue) {
        var result = '';
        for (var i = 0; i < menu.allOperation.length; i++) {
            //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
            if ((menu.allOperation[i].id & cellvalue) > 0) {
                result += menu.allOperation[i].description + "/";
            }
        }
        return result.substr(0, result.length - 1);
    },
    convertCategory: function (flag) {
        //true为叶节点
        if (flag) {
            return "<span class=\"label label-success\">菜单</span>";
        } else {
            return "<span class=\"label label-primary\">目录</span>";
        }
    },
    convertIcon: function (cellvalue) {
        return "<i class=\"" + cellvalue + "\"></i>";
    },
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
    checkMenuNameCallback: function (data) {
        if (data) {
            return [true, ''];
        } else {
            return [false, '该父节点下已存在同名菜单'];
        }
    },
    checkMenuName: function (parentId, menuName) {
        if (!parentId) {
            parentId = -1;
        }
        if (!menuName) {
            return;
        }
        //调用校验用户名服务
        return ajaxPostJson(menu.contextPath + menu.checkMenuNameUrl
            + "?menuName=" + menuName + "&parentId=" + parentId,
            false,
            null, menu.checkMenuNameCallback);
    },
    createMenuGrid: function () {
        var url = menu.contextPath + menu.queryUrl;
        var height = $(".jqGrid_wrapper").height();
        $("#menu_info_list").jqGrid({
            url: url,
            mtype: "POST",
            datatype: "json",
            height: height - 116,
            shrinkToFit: false,
            // forceFit: true,
            //表格json数据
            jsonReader: {
                repeatitems: false,
                id: "id",
                subgrid: {
                    repeatitems: false
                }
            },
            // jsonReader: { repeatitems: false, root: function (obj) { return obj; } },
            colNames: ["id", "菜单名称", "图标", "type", "分类",
                "菜单链接", "页面按钮",
                "创建时间", "创建人", "更新时间", "更新人", "状态", "parentId"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                key: true,
                editable: false,
                hidden: true
            }, {
                name: "parentId",
                index: "parentId",
                hidden: true,
                width: 150,
                formoptions: {label: '父节点<font color=\'red\'> *</font>'},
                editable: true,
                edittype: "select",
                editrules: {
                    required: true,
                    edithidden: true
                },
                editoptions: {
                    dataUrl: menu.contextPath + menu.queryParentsUrl,
                    buildSelect: function (data) {
                        var data = typeof data === "string" ?
                            $.parseJSON(data) : data,
                            s = "<select disabled='disabled'>";
                        $.each(data.data, function () {
                            s += '<option value="' + this.id + '">' + this.menuName +
                                '</option>';
                        });
                        // //定时任务（其实应该也可以写回调方法），用户id 编辑时disable
                        // setTimeout(function () {
                        //     if (userRole.needDisable) {
                        //         // $('#tr_' + field_id).attr('disabled', 'disabled');
                        //         $('#userId').attr('disabled', 'disabled');
                        //     } else {
                        //         $('#userId').removeAttr('disabled');
                        //     }
                        // }, 50);
                        return s + "</select>";
                    }
                }
            }, {
                name: "menuName",
                index: "menu_name",
                editable: true,
                width: 140,
                editrules: {
                    required: true
                },
                formoptions: {label: '菜单名称<font color=\'red\'> *</font>'}
            }, {
                name: "menuIcon",
                index: "menuIcon",
                editable: true,
                align: "center",
                width: 40,
                formatter: menu.convertIcon,
                edittype: "custom",
                editrules: {
                    required: true
                },
                editoptions: {
                    custom_element: icon.element,
                    custom_value: icon.value
                },
                formoptions: {label: '图标<font color=\'red\'> *</font>'}
            }, {
                name: "menuType",
                index: "menu_type",
                editable: false,
                width: 80,
                hidden: true
            }, {
                name: "menuLeaf",
                index: "menu_leaf",
                editable: true,
                sortable: false,
                width: 60,
                editrules: {
                    required: true
                },
                formatter: menu.convertCategory,
                formoptions: {label: '分类<font color=\'red\'> *</font>'}
            }, {
                name: "url",
                index: "url",
                editable: true,
                width: 180
            }, {
                name: "operationAll",
                index: "operation_all",
                editable: true,
                width: 180,
                formatter: menu.convertOperation
            }, {
                name: "createTime",
                index: "create_time",
                editable: false,
                width: 100,
                formatter: "date"
                // editoptions: {
                //     dataInit : function (elem) {
                //         $(elem).datepicker();
                //     }
                // }
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
                formatter: "date"
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
                formatter: menu.convertDel
            }],
            pager: "#pager_list",
            sortname: 'id',
            sortorder: "asc",
            treeGrid: true,
            treeGridModel: 'adjacency',
            treedatatype: "json",
            ExpandColumn: 'menuName',
            loadonce: true,
            treeReader: {
                parent_id_field: "parentId",
                level_field: "menuLevel",
                leaf_field: "menuLeaf",
                expanded_field: "expanded"
            },
            caption: "菜单信息管理",
            editurl: menu.contextPath + menu.editUrl,
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //查询所有操作列表
        var operationMapUrl = menu.contextPath + menu.operationMapUrl;
        ajaxPostJson(operationMapUrl, false, null, menu.initAllOperation);
        //初始化数据
        menu.createMenuGrid();

        //初始化操作数据
        var operationUrl = menu.contextPath + menu.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, menu.naviConfig);

        //初始化gird width，使得水平滚动条能显示
        menu.gridResizeWidth();
    },
    naviConfig: function (data) {
        $("#menu_info_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                reloadAfterSubmit: true,
                beforeSubmit: function (postdata, formid) {
                    return menu.checkMenuName(postdata.parentId, postdata.menuName);
                }
            },
            {//add option
                reloadAfterSubmit: true,
                beforeSubmit: function (postdata, formid) {
                    return menu.checkMenuName(postdata.parentId, postdata.menuName);
                }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_wrapper = $(".jqGrid_wrapper");
            var _menu_list = $("#menu_info_list");
            var width = _jqGrid_wrapper.width();
            _menu_list.setGridWidth(width);
            var height = _jqGrid_wrapper.height();
            _menu_list.setGridHeight(height - 116);
        })
    },
    gridResizeWidth: function () {
        var _jqGrid_wrapper = $(".jqGrid_wrapper");
        var _menu_list = $("#menu_info_list");
        var width = _jqGrid_wrapper.width();
        _menu_list.setGridWidth(width);
    }
};
