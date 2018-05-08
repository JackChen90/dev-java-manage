/**
 * @author jackie chen
 * @create 2018/01/29
 * @description roleMenuEdit js
 */
var roleMenuEdit = {
    //环境根路径
    contextPath: null,
    //角色菜单前端保存的实时数据(数组对象)
    roleMenuData: [],
    //所有操作权限
    allOperation: null,
    //菜单信息数据url
    queryRoleMenuUrl: "/admin/roleMenu/queryMenuByRoleId",
    //保存角色菜单url
    saveRoleMenuUrl: "/admin/roleMenu/saveRoleMenu",
    //操作字典url
    operationMapUrl: "/operation/allOperation",
    // convertDel: function (cellvalue, options, rowObject) {
    //     var newCellValue = null;
    //     switch (cellvalue) {
    //         case "0":
    //         default:
    //             newCellValue = "<font color='green'>正常</font>";
    //             break;
    //         case "1":
    //             newCellValue = "<font color='red'>停用</font>";
    //             break;
    //     }
    //     return newCellValue;
    // },
    refreshGrid: function (data) {
        console.log(data);
        var index = parent.layer.getFrameIndex(window.name);
        parent.$("#role_list").jqGrid().trigger("reloadGrid");
        parent.layer.close(index);
    },
    save: function () {//页面"保存"按钮点击事件
        // var obj = $("#edit_menu_list").jqGrid("getRowData");
        // alert(obj.length);
        // console.log(obj);
        var data = {};
        data.roleId = roleMenuEdit.roleId;
        data.menuType = 1;
        data.dataStr = JSON.stringify(roleMenuEdit.roleMenuData);
        var path = roleMenuEdit.contextPath + roleMenuEdit.saveRoleMenuUrl;
        // 获取所有选中行，以及btn数据
        ajaxPostJson(path, false, data, roleMenuEdit.refreshGrid);
    },
    cancel: function () {//页面"取消"按钮点击事件
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    },
    convertCategory: function (flag) {
        //true为叶节点
        if (flag) {
            return "<span class=\"label label-success\">菜单</span>";
        } else {
            return "<span class=\"label label-primary\">目录</span>";
        }
    },
    convertOperation: function (cellvalue) {
        var result = '';
        for (var i = 0; i < roleMenuEdit.allOperation.length; i++) {
            //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
            if ((roleMenuEdit.allOperation[i].id & cellvalue) > 0) {
                result += '<span class="label ' + getLabelClass(roleMenuEdit.allOperation[i].id)
                    + '">' + roleMenuEdit.allOperation[i].description + '</span> ';
            }
        }
        return result;
    },
    // operationToStr: function (cellvalue, options, cell) {
    //     var result = '';
    //     var cells = cellvalue.split("/");
    //     for (var j = 0; j < cells.length; j++) {
    //         for (var i = 0; i < roleMenu.allOperation.length; i++) {
    //             //页面操作与操作表每个操作的id做与运算，为0则不具备该操作权限
    //             if (roleMenu.allOperation[i].description == cells[j]) {
    //                 result += roleMenu.allOperation[i].id + ",";
    //             }
    //         }
    //     }
    //     return result.substr(0, result.length - 1);
    // },
    convertCheck: function (flag) {
        //true为有权限
        if (flag) {
            return "<input class='menuCheck' type=\"checkbox\" checked=\"checked\" value=\"1\" offval=\"no\">";
        } else {
            return "<input class='menuCheck' type=\"checkbox\" value=\"0\" offval=\"no\">";
        }
    },
    revertCheck: function (cellvalue, options, cell) {
        return $('.menuCheck', cell).val();
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
            // multiselect: true,//复选框 tree grid 下失效
            //表格json数据
            jsonReader: {
                repeatitems: false,
                id: "id",
                subgrid: {
                    repeatitems: false
                }
            },
            // jsonReader: { repeatitems: false, root: function (obj) { return obj; } },
            colNames: ["", "id", "菜单名称", "type", "分类", "菜单链接", "页面按钮"],
            colModel: [{
                name: "hasRole",
                index: "hasRole",
                width: 20,
                editable: false,
                formatter: roleMenuEdit.convertCheck,
                unformat: roleMenuEdit.revertCheck
            }, {
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
                width: 130,
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
                width: 140
            }, {
                name: "operation",
                index: "operation",
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
                width: 200,
                formatter: roleMenuEdit.convertOperation,
                // unformat: roleMenu.operationToStr,
                // formoptions: {label: '页面按钮<font color=\'red\'> *</font>'}
            }
            ],
            // pager: "#pager_list2",
            sortname: 'id',
            sortorder: "asc",
            treeGrid: true,
            treeGridModel: 'adjacency',
            treedatatype: "json",
            ExpandColumn: 'menuName',
            // loadonce: false,
            treeReader: {
                parent_id_field: "parentId",
                level_field: "menuLevel",
                leaf_field: "menuLeaf",
                expanded_field: "expanded"
            },
            caption: "菜单信息",
            hidegrid: false,
            beforeSelectRow: function (rowid, e) {
                /*用户点击table首列（checkbox列）时，更新roleMenuEdit中对应数据hasRole状态*/
                var iCol = $.jgrid.getCellIndex($(e.target).closest("td")[0]);
                var cm = $(this).jqGrid("getGridParam", "colModel");
                if (cm[iCol].name === "hasRole") {
                    for (var i = 0; i < roleMenuEdit.roleMenuData.length; i++) {
                        if (roleMenuEdit.roleMenuData[i].id == rowid) {
                            //设置页面权限
                            roleMenuEdit.roleMenuData[i].hasRole = $(e.target).prop('checked');

                            /*设置右侧checkbox disable*/
                            var selId = $("#edit_menu_list").getGridParam("selrow");
                            //选中列与当前点击checkbox的列相同时，disabled属性toggle
                            if (rowid === selId){
                                $('.btn-checkbox').prop('disabled', function () {
                                    return ! $(this).prop('disabled');
                                });
                            }
                        }
                    }
                }
                // console.log($(e.target).prop('checked'));
                // alert($(e.target).is(":checked")); //此方法也可选出是否选中
            },
            //事件
            onSelectRow: function (rowid, status, e) {
                var checkboxs = '';
                var checked = 'checked';
                if (rowid) {
                    for (var i = 0; i < roleMenuEdit.roleMenuData.length; i++) {
                        if (rowid == roleMenuEdit.roleMenuData[i].id) {
                            for (var j = 0; j < roleMenuEdit.allOperation.length; j++) {
                                if ((roleMenuEdit.allOperation[j].id & roleMenuEdit.roleMenuData[i].operationAll) > 0) {
                                    checkboxs += '<div class="checkbox checkbox-success">\n' +
                                        '                    <input id="' + rowid + "_" + roleMenuEdit.allOperation[j].id + '" class="styled  btn-checkbox" type="checkbox"';
                                    if ((roleMenuEdit.allOperation[j].id & roleMenuEdit.roleMenuData[i].operation) > 0) {
                                        checkboxs += ' checked';
                                    }
                                    //设置右侧按钮checkbox disabled属性
                                    if (!roleMenuEdit.roleMenuData[i].hasRole) {
                                        checkboxs += ' disabled';
                                    }
                                    checkboxs += '>\n';
                                    checkboxs += '                    <label for="' + rowid + "_" + roleMenuEdit.allOperation[j].id + '">\n';
                                    checkboxs += roleMenuEdit.allOperation[j].description;
                                    checkboxs += '                        \n' +
                                        '                    </label>\n' +
                                        '                </div>';
                                }
                            }
                            $('.jqGrid_btn_wrapper').html(checkboxs);
                            break;
                        }
                    }
                }
            },
            loadComplete: function (data) {
                //存储本地rolemenu data
                var rows = data.rows;
                if (rows) {
                    for (var i = 0; i < rows.length; i++) {
                        roleMenuEdit.roleMenuData[i] = new Object();
                        roleMenuEdit.roleMenuData[i].id = rows[i].id;
                        roleMenuEdit.roleMenuData[i].hasRole = rows[i].hasRole;
                        roleMenuEdit.roleMenuData[i].operation = rows[i].operation;
                        roleMenuEdit.roleMenuData[i].operationAll = rows[i].operationAll;
                    }
                }
                console.log(roleMenuEdit.roleMenuData);

                //绑定checkbox点击事件
                // $('#edit_menu_list').on('change','.menuCheck',function () {
                //     alert($(this).prop('checked'));
                //     if ($(this).prop('checked')){
                //         $(this).val(1);
                //     }else {
                //         $(this).val(0);
                //     }
                // });
            }
        });
    },
    initAllOperation: function (data) {
        roleMenuEdit.allOperation = data;
    },
    init: function (roleId) {
        roleMenuEdit.roleId = roleId;
        //查询所有操作列表
        var operationMapUrl = roleMenuEdit.contextPath + roleMenuEdit.operationMapUrl;
        ajaxPostJson(operationMapUrl, false, null, roleMenuEdit.initAllOperation);

        //初始化菜单数据
        roleMenuEdit.createRoleMenuGrid(roleId);

        //初始化操作数据
        // var operationUrl = roleMenu.contextPath + roleMenu.operationUrl + "?menuId=" + menuId + "&type=" + type;
        // ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, roleMenu.naviConfig);

        //初始化gird width，使得水平滚动条能显示
        // roleMenu.gridResizeWidth();

        //绑定右侧按钮checkbox事件
        $('.jqGrid_btn_wrapper').on('click', '.btn-checkbox', function () {
            var id = $(this).attr('id');
            var ids = id.split('_');
            var menuId = ids[0];
            var op = new Number(ids[1]);

            //获取选中行data
            var _grid =$("#edit_menu_list");
            var selId = _grid.getGridParam("selrow");
            var rowData = _grid.jqGrid('getRowData', selId);
            // console.log(rowData);
            // rowData.Currency = '12321';
            // $("#edit_menu_list").jqGrid('setRowData', rowId, rowData);

            for (var i = 0; i < roleMenuEdit.roleMenuData.length; i++) {
                if (roleMenuEdit.roleMenuData[i].id == menuId) {
                    //如有页面权限，则对按钮权限做增删（number类型加减），选中为增，不选中为减
                    if (roleMenuEdit.roleMenuData[i].hasRole) {
                        if ($(this).prop('checked')) {
                            roleMenuEdit.roleMenuData[i].operation += op;
                        } else {
                            roleMenuEdit.roleMenuData[i].operation -= op;
                        }
                        //更新按钮权限cell数据
                        //$.jgrid.formatter.date.reformatAfterEdit = true;//这句在当前版本不需要了
                        rowData.operation = roleMenuEdit.roleMenuData[i].operation;
                    }
                }
            }
        });

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
