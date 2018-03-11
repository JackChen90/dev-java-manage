/**
 * @author jackie chen
 * @create 2018/01/29
 * @description roleMenuEdit js
 */
var roleMenuEdit = {
    contextPath: null,
    //菜单信息数据url
    queryRoleMenuUrl: "/roleMenu/queryMenuByRoleId",
    //保存角色菜单url
    saveRoleMenuUrl: "/roleMenu/saveRoleMenu",
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
    convertCategory: function (flag) {
        //true为叶节点
        if (flag) {
            return "<span class=\"label label-success\">菜单</span>";
        } else {
            return "<span class=\"label label-primary\">目录</span>";
        }
    },
    createRoleMenuGrid: function (roleId) {
        var url = roleMenuEdit.contextPath + roleMenuEdit.queryRoleMenuUrl + "?roleId=" + roleId;
        var height = $($(".jqGrid_wrapper")[0]).height();
        $("#edit_menu_list").jqGrid({
            url: url,
            mtype: "POST",
            datatype: "json",
            height: height - 77,
            autowidth: true,
            shrinkToFit: true,
            // autoScroll: false,
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
            colNames: ["id", "菜单名称", "type", "分类", "菜单链接", "页面按钮", "状态"],
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
                sortable: false,
                editable: true,
                width: 140,
                editrules: {
                    required: true
                },
                formoptions: {label: '菜单名称<font color=\'red\'> *</font>'}
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
                edittype: "select",
                editrules: {
                    required: true
                },
                editoptions: {
                    value: {true: '菜单', false: '目录'}
                },
                formatter: roleMenuEdit.convertCategory
            }, {
                name: "url",
                index: "url",
                sortable: false,
                editable: true,
                editrules: {
                    required: true
                },
                width: 180
            }, {
                name: "operationAll",
                index: "operation_all",
                sortable: false,
                editable: false,
                // edittype: "select",
                // editrules: {
                //     required: true
                // },
                // editoptions: {
                //     multiple: true,
                //     size: 5,
                //     value: roleMenu.getAllOperation
                // },
                // width: 180,
                // formatter: roleMenu.convertOperation,
                // unformat: roleMenu.operationToStr,
                // formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}
            }, {
                name: "delFlag",
                index: "del_flag",
                editable: false,
                width: 60,
                formatter: roleMenuEdit.convertDel
            }],
            // pager: "#pager_list2",
            sortname: 'id',
            sortorder: "asc",
            treeGrid: true,
            treeGridModel: 'adjacency',
            treedatatype: "json",
            ExpandColumn: 'menuName',
            // loadonce: true,
            treeReader: {
                parent_id_field: "parentId",
                level_field: "menuLevel",
                leaf_field: "menuLeaf",
                expanded_field: "expanded"
            },
            caption: "菜单信息",
            hidegrid: false
        });
    },
    init: function (roleId) {
        //初始化菜单数据
        roleMenuEdit.createRoleMenuGrid(roleId);

        //初始化操作数据
        // var operationUrl = roleMenu.contextPath + roleMenu.operationUrl + "?menuId=" + menuId + "&type=" + type;
        // ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, roleMenu.naviConfig);

        //初始化gird width，使得水平滚动条能显示
        // roleMenu.gridResizeWidth();
    },
    resize: function () {
        $(window).bind("resize", function () {
            // var _jqGrid_btn_wrapper = $(".jqGrid_btn_wrapper");
            var _jqGrid_menu_wrapper = $(".jqGrid_menu_wrapper");
            var _menu_list = $("#edit_menu_list");
            var menu_width = _jqGrid_menu_wrapper.width();
            _menu_list.setGridWidth(menu_width);
            // _role_list.setGridWidth(role_width);
            var menu_height = _jqGrid_menu_wrapper.height();
            _menu_list.setGridHeight(menu_height - 77);
            // _role_list.setGridHeight(role_height - 116);
        })
    }
};