/**
 * @author jackie chen
 * @create 2018/01/29
 * @description menuInfo js
 */
var menu = {
    contextPath: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/menu/operationData",
    //用户信息数据url
    queryUrl: "/menu/queryData",
    //新增/编辑/删除数据url
    editUrl: "/menu/operatemenuData",
    //校验用户名不重复
    checkmenuNameUrl: "/menu/checkmenuName",
    convertCategory: function (cellvalue) {
        //true为叶节点
        if(cellvalue){
            return "<span class=\"label label-success\">菜单</span>";
        }else {
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
    checkmenuNameCallback: function (data) {
        if (data) {
            return [true, ''];
        } else {
            return [false, '已存在相同用户名'];
        }
    },
    checkmenuName: function (menuName) {
        if (!menuName) {
            return;
        }
        //调用校验用户名服务
        return ajaxPostJson(menu.contextPath + menu.checkmenuNameUrl + "?menuName=" + menuName, false,
            null, menu.checkmenuNameCallback);
    },
    createMenuGrid: function () {
        var url = menu.contextPath + menu.queryUrl;
        var height = $(".jqGrid_wrapper").height();
        $("#menu_info_list").jqGrid({
            url: url,
            mtype: "POST",
            datatype: "json",
            height: height - 116,
            autowidth: true,
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
                name: "menuName",
                index: "menu_name",
                editable: true,
                width: 120,
                editrules: {
                    required: true
                    // custom: true,
                    // custom_func: function (param) {
                    //     return menu.checkmenuName(param)
                    // }
                },
                formoptions: {label: '菜单名称<font color=\'red\'> *</font>'}
            }, {
                name: "menuIcon",
                index: "menuIcon",
                editable: true,
                align: "center",
                width: 40,
                formatter: menu.convertIcon,
                editrules: {
                    required: true
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
                width: 50,
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
                width: 80
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
            }, {
                "name": "parentId",
                "hidden": true
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
        //初始化数据
        menu.createMenuGrid();
        //初始化操作数据
        var operationUrl = menu.contextPath + menu.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, menu.naviConfig);
    },
    naviConfig: function (data) {
        $("#menu_info_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                reloadAfterSubmit: true,
                // beforeSubmit: function (postdata, formid) {
                //     return menu.checkmenuName(postdata.menuName);
                // }
            },
            {//add option
                reloadAfterSubmit: true,
                // beforeSubmit: function (postdata, formid) {
                //     return menu.checkmenuName(postdata.menuName);
                // }
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
    }
};
