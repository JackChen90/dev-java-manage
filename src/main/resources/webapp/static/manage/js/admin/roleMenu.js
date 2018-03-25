/**
 * @author jackie chen
 * @create 2018/01/29
 * @description roleMenu js
 */
var roleMenu = {
    contextPath: null,
    //所有操作权限
    allOperation: null,
    //操作权限数据url
    operationUrl: "/admin/roleMenu/operationData",
    //角色信息数据url
    queryRoleInfoUrl: "/admin/role/queryData",
    //菜单信息数据url
    queryRoleMenuUrl: "/admin/roleMenu/queryMenuData",
    //新增/编辑/删除数据url
    editUrl: "/admin/roleMenu/operateMenuData",
    //校验用户名不重复
    checkMenuNameUrl: "/admin/roleMenu/checkMenuName",
    //操作字典url
    operationMapUrl: "/operation/allOperation",
    //查询所有父节点信息
    queryParentsUrl: "/admin/roleMenu/queryParents4Select",
    getAllOperation: function () {
        var operationJson = {};
        if (roleMenu.allOperation) {
            for (var i = 0; i < roleMenu.allOperation.length; i++) {
                operationJson[roleMenu.allOperation[i].id] = roleMenu.allOperation[i].description;
            }
        }
        return operationJson;
    },
    initAllOperation: function (data) {
        roleMenu.allOperation = data;
    },
    operationToStr: function (cellvalue, options, cell) {
        var result = '';
        var cells = cellvalue.split("/");
        for (var j = 0; j < cells.length; j++) {
            for (var i = 0; i < roleMenu.allOperation.length; i++) {
                //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
                if (roleMenu.allOperation[i].description == cells[j]) {
                    result += roleMenu.allOperation[i].id + ",";
                }
            }
        }
        return result.substr(0, result.length - 1);
    },
    convertOperation: function (cellvalue) {
        var result = '';
        for (var i = 0; i < roleMenu.allOperation.length; i++) {
            //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
            if ((roleMenu.allOperation[i].id & cellvalue) > 0) {
                result += roleMenu.allOperation[i].description + "/";
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
    iconToText: function (cellvalue, options, cell) {
        return $('i', cell).attr('class');
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
    checkMenuName: function (parentId, menuName, id) {
        if (!menuName) {
            return;
        }
        if (!parentId) {
            parentId = -1;
        }
        var idStr = '';
        if (id) {
            idStr = "&id=" + id;
        }
        //调用校验用户名服务
        return ajaxPostJson(roleMenu.contextPath + roleMenu.checkMenuNameUrl
            + "?menuName=" + menuName + "&parentId=" + parentId + idStr,
            false,
            null, roleMenu.checkMenuNameCallback);
    },
    createRoleGrid: function () {
        var url = roleMenu.contextPath + roleMenu.queryRoleInfoUrl;
        var height = $(".jqGrid_wrapper").height();
        // var height = 300;
        $("#role_list").jqGrid({
            url: url,
            datatype: "json",
            height: height - 116,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 9999,
            rownumbers: true,
            //表格json数据
            jsonReader: {
                repeatitems: false,
                id: "id",
                subgrid: {
                    repeatitems: false
                }
            },
            colNames: ["id", "角色名", "状态"],
            colModel: [{
                name: "id",
                index: "id",
                key: true,
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "roleName",
                index: "role_name",
                editable: false,
                width: 70
            }, {
                name: "delFlag",
                index: "del_flag",
                editable: false,
                width: 40,
                formatter: roleMenu.convertDel
            }],
            viewrecords: true,
            caption: "角色信息列表",
            pager: "#pager_list1",
            pginput: false,
            pgbuttons: false,
            recordtext: "",
            hidegrid: false,
            //事件
            onSelectRow: function (rowid, status, e) {
                if (rowid) {
                    //设置菜单grid url，触发reloadGrid
                    $("#menu_list").jqGrid('setGridParam',
                        {
                            url: roleMenu.contextPath + roleMenu.queryRoleMenuUrl
                            + '?roleId=' + rowid
                        }).trigger("reloadGrid");
                    //设置菜单grid edit url
                }
            }
        });
    },
    createRoleMenuGrid: function () {
        var url = roleMenu.contextPath + roleMenu.queryRoleMenuUrl;
        var height = $(".jqGrid_wrapper").height();
        $("#menu_list").jqGrid({
            url: url,
            mtype: "POST",
            datatype: "json",
            height: height - 77,
            autowidth: true,
            shrinkToFit: true,
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
            colNames: ["id", "父节点", "菜单名称", "图标", "type", "分类",
                "菜单链接", "页面按钮", "状态"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                key: true,
                editable: false,
                hidden: true
            }, {
                name: "parentIdE",
                index: "parent_id",
                hidden: true,
                width: 150,
                formoptions: {label: '父节点<font color=\'red\'> *</font>'},
                sortable: false,
                editable: true,
                edittype: "select",
                editrules: {
                    edithidden: true
                },
                editoptions: {
                    dataUrl: roleMenu.contextPath + roleMenu.queryParentsUrl,
                    buildSelect: function (data) {
                        var data = typeof data === "string" ?
                            $.parseJSON(data) : data,
                            s = "<select disabled='disabled'>";
                        $.each(data.data, function () {
                            s += '<option value="' + this.id + '">' + this.menuName +
                                '</option>';
                        });
                        return s + "</select>";
                    }
                }
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
                name: "menuIcon",
                index: "menuIcon",
                sortable: false,
                editable: true,
                align: "center",
                width: 40,
                formatter: roleMenu.convertIcon,
                unformat: roleMenu.iconToText,
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
                edittype: "select",
                editrules: {
                    required: true
                },
                editoptions: {
                    value: {true: '菜单', false: '目录'}
                },
                formatter: roleMenu.convertCategory
            }, {
                name: "url",
                index: "url",
                sortable: false,
                editable: true,
                editrules: {
                    required: true
                },
                width: 180,
                formoptions: {label: '菜单链接<font color=\'red\'> *</font>'}
            }, {
                name: "operation",
                index: "operation",
                sortable: false,
                editable: true,
                edittype: "select",
                editrules: {
                    required: true
                },
                editoptions: {
                    multiple: true,
                    size: 5,
                    value: roleMenu.getAllOperation
                },
                width: 180,
                formatter: roleMenu.convertOperation,
                unformat: roleMenu.operationToStr,
                formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}
            }, {
                name: "delFlag",
                index: "del_flag",
                editable: false,
                width: 60,
                formatter: roleMenu.convertDel
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
            editurl: roleMenu.contextPath + roleMenu.editUrl + "?param=" + roleMenu.postdata,
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //查询所有操作列表
        var operationMapUrl = roleMenu.contextPath + roleMenu.operationMapUrl;
        ajaxPostJson(operationMapUrl, false, null, roleMenu.initAllOperation);
        //初始化角色数据
        roleMenu.createRoleGrid();
        //初始化菜单数据
        roleMenu.createRoleMenuGrid();

        //初始化操作数据
        var operationUrl = roleMenu.contextPath + roleMenu.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, roleMenu.naviConfig);

        //初始化gird width，使得水平滚动条能显示
        // roleMenu.gridResizeWidth();
    },
    naviConfig: function (data) {
        $("#role_list").jqGrid("navGrid", "#pager_list1", {
                add: false,
                edit: false,
                del: false,
                search: false,
                refresh: false
            },
            {//edit option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,

                // beforeSubmit: function (postdata, formid) {
                //     //菜单类型为目录/菜单时，控制url的disabled
                //     $('#menuLeaf', formid).change(function () {
                //         var selectvalue = $(this).val();
                //         var urlCol = $('#url', formid);
                //         urlCol.val('');
                //         if (selectvalue == true) {
                //             urlCol.removeAttr('disabled');
                //         }
                //         else {
                //             urlCol.attr('disabled', 'disabled');
                //         }
                //     });
                //
                //     //校验同级菜单重名
                //     return roleMenu.checkMenuName(postdata.parentId,
                //         postdata.menuName, postdata.menu_list_id);
                // }
            },
            {//add option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                // beforeCheckValues: function (posdata, formid, mode) {
                //     var selectvalue = $('#menuLeaf', formid).val();
                //     //类型为"菜单"，则url与按钮操作必填；否则不必填
                //     var _menuInfo = $("#menu_list");
                //     if (selectvalue == "false") {
                //         _menuInfo.setColProp('url', {editrules: {required: false}});
                //         _menuInfo.setColProp('operationAll', {editrules: {required: false}});
                //     } else {
                //         _menuInfo.setColProp('url', {editrules: {required: true}});
                //         _menuInfo.setColProp('operationAll', {editrules: {required: true}});
                //     }
                // },
                // beforeInitData: function (formid) {//beforeInitData在form创建前执行，所以
                //     // var selectvalue = $('#menuLeaf', formid).val(); //此处有问题。此时value获取不到，得拿grid中的值
                //     // if (true) {
                //     //     $("#menu_list").setColProp('operationAll',
                //     //         {formoptions: {label: '页面按钮'}});
                //     // }else {
                //     //     $("#menu_list").setColProp('operationAll',
                //     //         {formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}});
                //     // }
                //     //beforeInitData返回true/false，返回false则form不显示
                //     return true;
                // },
                // beforeShowForm: function (formid) {
                //     //菜单类型为目录/菜单时，控制url/operationAll的disabled
                //     $('#menuLeaf', formid).change(function () {
                //         var selectvalue = $(this).val();
                //         var urlCol = $('#url', formid);
                //         var operationCol = $('#operationAll', formid);
                //         urlCol.val('');
                //         if (selectvalue == "true") {
                //             urlCol.removeAttr('disabled');
                //             operationCol.removeAttr('disabled');
                //             $('label[for=url]').html('菜单链接<font color=\'red\'> *</font>');
                //             $('label[for=operationAll]').html('页面按钮<font color=\'red\'> *</font>');
                //         }
                //         else {
                //             urlCol.attr('disabled', 'disabled');
                //             operationCol.attr('disabled', 'disabled');
                //             $('label[for=url]').html('菜单链接');
                //             $('label[for=operationAll]').html('页面按钮');
                //         }
                //     });
                // },
                // beforeSubmit: function (postdata, formid) {
                //     // console.log(postdata);
                //     //校验同级菜单重名
                //     return roleMenu.checkMenuName(postdata.parentId, postdata.menuName);
                // }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});

        //增加separator
        // $("#role_list").jqGrid('navSeparatorAdd', "#pager_list1", {
        //     sepclass: 'ui-separator',
        //     sepcontent: '',
        //     position: 'first'
        // });

        //自定义button
        if (data.del == true) {
            $("#role_list").jqGrid("navButtonAdd", "#pager_list1", {
                caption: "",
                buttonicon: "glyphicon-trash",
                onClickButton: function () {
                    alert("Delete Row");
                },
                position: "first"
            });
        }
        if (data.edit == true) {
            $("#role_list").jqGrid("navButtonAdd", "#pager_list1", {
                caption: "",
                buttonicon: "glyphicon-edit",
                onClickButton: function () {
                    var selRow = $(this).jqGrid("getGridParam", "selrow");
                    if (selRow) {
                        layer.open({
                            type: 2,
                            title: '角色权限编辑',
                            closeBtn: 1,
                            shadeClose: false,
                            skin: 'layer-ext-moon',
                            area: ['900px', '420px'],
                            content: roleMenu.contextPath + '/admin/roleMenu/roleMenuEdit?roleId=' + selRow
                        })
                    } else {
                        // $.jgrid.viewModal("#alertmod_role_list", {toTop: true, jqm: true});
                        $.jgrid.viewModal("#alertmod_role_list", {gbox: "#gbox_role_list", jqm: true});
                        $("#jqg_alrt").focus();
                    }
                },
                position: "first"
            });
        }
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_role_wrapper = $(".jqGrid_role_wrapper");
            var _jqGrid_menu_wrapper = $(".jqGrid_menu_wrapper");
            var _role_list = $("#role_list");
            var _menu_list = $("#menu_list");
            var role_width = _jqGrid_role_wrapper.width();
            var menu_width = _jqGrid_menu_wrapper.width();
            _menu_list.setGridWidth(menu_width);
            _role_list.setGridWidth(role_width);
            var role_height = _jqGrid_role_wrapper.height();
            _menu_list.setGridHeight(role_height - 77);
            _role_list.setGridHeight(role_height - 116);
        })
    },
    gridResizeWidth: function () {
        var _jqGrid_role_wrapper = $(".jqGrid_role_wrapper");
        var _jqGrid_menu_wrapper = $(".jqGrid_menu_wrapper");
        var _role_list = $("#role_list");
        var _menu_list = $("#menu_list");
        var role_width = _jqGrid_role_wrapper.width();
        var menu_width = _jqGrid_menu_wrapper.width();
        _role_list.setGridWidth(role_width);
        _menu_list.setGridWidth(menu_width);
    }
};
