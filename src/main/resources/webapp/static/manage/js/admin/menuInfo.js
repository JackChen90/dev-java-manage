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
    operationUrl: "/admin/menu/operationData",
    //菜单信息数据url
    queryUrl: "/admin/menu/queryData",
    //新增/编辑/删除数据url
    editUrl: "/admin/menu/operateMenuData",
    //校验用户名不重复
    checkMenuNameUrl: "/admin/menu/checkMenuName",
    //操作字典url
    operationMapUrl: "/operation/allOperation",
    //查询所有父节点信息
    queryParentsUrl: "/admin/menu/queryParents4Select",
    getAllOperation: function () {
        var operationJson = {};
        if (menu.allOperation) {
            for (var i = 0; i < menu.allOperation.length; i++) {
                operationJson[menu.allOperation[i].id] = menu.allOperation[i].description;
            }
        }
        return operationJson;
    },
    initAllOperation: function (data) {
        menu.allOperation = data;
    },
    operationToStr: function (cellvalue, options, cell) {
        var result = '';
        var cells = cellvalue.split("/");
        for (var j = 0; j < cells.length; j++) {
            for (var i = 0; i < menu.allOperation.length; i++) {
                //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
                if (menu.allOperation[i].description == cells[j]) {
                    result += menu.allOperation[i].id + ",";
                }
            }
        }
        return result.substr(0, result.length - 1);
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
        var idStr ='';
        if (id) {
            idStr = "&id=" + id;
        }
        //调用校验用户名服务
        return ajaxPostJson(menu.contextPath + menu.checkMenuNameUrl
            + "?menuName=" + menuName + "&parentId=" + parentId + idStr,
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
            colNames: ["id", "父节点", "菜单名称", "图标", "type", "分类",
                "菜单链接", "页面按钮",
                "创建时间", "创建人", "更新时间", "更新人", "状态"],
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
                    dataUrl: menu.contextPath + menu.queryParentsUrl,
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
                formatter: menu.convertIcon,
                unformat: menu.iconToText,
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
                formatter: menu.convertCategory
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
                name: "operationAll",
                index: "operation_all",
                sortable: false,
                editable: true,
                edittype: "select",
                editrules: {
                    required: true
                },
                editoptions: {
                    multiple: true,
                    size: 5,
                    value: menu.getAllOperation
                },
                width: 180,
                formatter: menu.convertOperation,
                unformat: menu.operationToStr,
                formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}
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
            // loadonce: true,
            treeReader: {
                parent_id_field: "parentId",
                level_field: "menuLevel",
                leaf_field: "menuLeaf",
                expanded_field: "expanded"
            },
            caption: "菜单信息管理",
            editurl: menu.contextPath + menu.editUrl + "?param=" + menu.postdata,
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
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeSubmit: function (postdata, formid) {
                    //菜单类型为目录/菜单时，控制url的disabled
                    $('#menuLeaf', formid).change(function () {
                        var selectvalue = $(this).val();
                        var urlCol = $('#url', formid);
                        urlCol.val('');
                        if (selectvalue == true) {
                            urlCol.removeAttr('disabled');
                        }
                        else {
                            urlCol.attr('disabled', 'disabled');
                        }
                    });

                    //校验同级菜单重名
                    return menu.checkMenuName(postdata.parentId,
                        postdata.menuName, postdata.menu_info_list_id);
                }
            },
            {//add option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                beforeCheckValues: function (posdata, formid, mode) {
                    var selectvalue = $('#menuLeaf', formid).val();
                    //类型为"菜单"，则url与按钮操作必填；否则不必填
                    var _menuInfo = $("#menu_info_list");
                    if (selectvalue == "false") {
                        _menuInfo.setColProp('url', {editrules: {required: false}});
                        _menuInfo.setColProp('operationAll', {editrules: {required: false}});
                    } else {
                        _menuInfo.setColProp('url', {editrules: {required: true}});
                        _menuInfo.setColProp('operationAll', {editrules: {required: true}});
                    }
                },
                beforeInitData: function (formid) {//beforeInitData在form创建前执行，所以
                    // var selectvalue = $('#menuLeaf', formid).val(); //此处有问题。此时value获取不到，得拿grid中的值
                    // if (true) {
                    //     $("#menu_info_list").setColProp('operationAll',
                    //         {formoptions: {label: '页面按钮'}});
                    // }else {
                    //     $("#menu_info_list").setColProp('operationAll',
                    //         {formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}});
                    // }
                    //beforeInitData返回true/false，返回false则form不显示
                    return true;
                },
                beforeShowForm: function (formid) {
                    //菜单类型为目录/菜单时，控制url/operationAll的disabled
                    $('#menuLeaf', formid).change(function () {
                        var selectvalue = $(this).val();
                        var urlCol = $('#url', formid);
                        var operationCol = $('#operationAll', formid);
                        urlCol.val('');
                        if (selectvalue == "true") {
                            urlCol.removeAttr('disabled');
                            operationCol.removeAttr('disabled');
                            $('label[for=url]').html('菜单链接<font color=\'red\'> *</font>');
                            $('label[for=operationAll]').html('页面按钮<font color=\'red\'> *</font>');
                        }
                        else {
                            urlCol.attr('disabled', 'disabled');
                            operationCol.attr('disabled', 'disabled');
                            $('label[for=url]').html('菜单链接');
                            $('label[for=operationAll]').html('页面按钮');
                        }
                    });
                },
                beforeSubmit: function (postdata, formid) {
                    // console.log(postdata);
                    //校验同级菜单重名
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

var radio = {
    element: function (value, options) {
        var menu = '<span><input class="fontInput" type="radio" name="RadioGender" value="0"';
        var breakline = '/>菜单';
        var catalog = '&nbsp;<input class="fontInput" type="radio" name="RadioGender" value="1"';
        var end = '/>目录</span>';
        var radiohtml;
        if (!value) {
            radiohtml = menu + ' checked="checked"' + breakline + catalog + end;
            return radiohtml;
        } else {
            radiohtml = menu + breakline + catalog + ' checked="checked"' + end;
            return radiohtml;
        }
    },
    value: function (elem, operation, value) {
        var $elems = $(elem).find("input[name=RadioGender]");
        if (operation === "get") {
            return $elems.first().is(":checked") ? false : true;
        } else if (operation === "set") {
            $elems.val(value);
        }
    }
};
