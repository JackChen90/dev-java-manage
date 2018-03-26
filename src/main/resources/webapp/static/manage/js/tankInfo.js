/**
 * @author jackie chen
 * @create 2018/03/27
 * @description tankInfo js
 */
var tank = {
    contextPath: null,
    //默认第一页
    pageNum: 1,
    //默认每页20条
    pageSize: 20,
    //操作权限数据url
    operationUrl: "/tank/operationData",
    //用户信息数据url
    queryUrl: "//tank/queryData",
    //新增/编辑/删除数据url
    editUrl: "//tank/operateTankData",
    //校验用户名不重复
    checkTankNumberUrl: "/tank/checkTankNumber",
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
    checkTankNumberCallback: function (data) {
        if (data) {
            return [true, ''];
        } else {
            return [false, '已存在相同储罐编码'];
        }
    },
    checkTankNumber: function (tankNumber) {
        if (!tankNumber) {
            return;
        }
        //调用校验储罐编码服务
        return ajaxPostJson(tank.contextPath + tank.checkTankNumberUrl + "?tankNumber=" + tankNumber, false,
            null, tank.checkTankNumberCallback);
    },
    createTankGrid: function () {
        var url = tank.contextPath + tank.queryUrl;
        var height = $(".jqGrid_wrapper").height();
        $("#tank_list").jqGrid({
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
            colNames: ["id", "储罐名称", "储罐编码", "出厂日期", "上次检验日期", "下次检验日期",
                "创建时间", "创建人", "更新时间", "更新人", "状态"],
            colModel: [{
                name: "id",
                index: "id",
                width: 60,
                editable: false,
                hidden: true
            }, {
                name: "name",
                index: "name",
                editable: true,
                width: 90,
                editrules: {
                    required: true
                },
                formoptions: {label: '储罐名称<font color=\'red\'> *</font>'}
            }, {
                name: "number",
                index: "number",
                editable: true,
                width: 100,
                editrules: {
                    required: true
                },
                formoptions: {label: '储罐编码<font color=\'red\'> *</font>'}
            }, {
                name: "productDate",
                index: "product_date",
                editable: true,
                width: 100,
                editoptions: {
                    dataInit: function (elem) {
                        $(elem).datepicker();
                    }
                },
                formoptions: {label: '出厂日期<font color=\'red\'> *</font>'}
            }, {
                name: "lastCheckDate",
                index: "last_check_date",
                editable: true,
                width: 100,
                editoptions: {
                    dataInit: function (elem) {
                        $(elem).datepicker();
                    }
                },
                formoptions: {label: '上次检验日期<font color=\'red\'> *</font>'}
            }, {
                name: "nextCheckDate",
                index: "next_check_date",
                editable: true,
                width: 100,
                editoptions: {
                    dataInit: function (elem) {
                        $(elem).datepicker();
                    }
                },
                formoptions: {label: '下次检验日期<font color=\'red\'> *</font>'}
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
                name: "state",
                index: "state",
                editable: false,
                width: 60,
                formatter: tank.convertDel
            }],
            pager: "#pager_list",
            viewrecords: true,
            caption: "储罐信息管理",
            editurl: tank.contextPath + tank.editUrl,
            hidegrid: false
        });
    },
    init: function (menuId, type) {
        //初始化数据
        tank.createTankGrid();
        //初始化操作数据
        var operationUrl = tank.contextPath + tank.operationUrl + "?menuId=" + menuId + "&type=" + type;
        ajaxPostJson(operationUrl, true, {menuId: menuId, type: type}, tank.naviConfig);
    },
    naviConfig: function (data) {
        $("#tank_list").jqGrid("navGrid", "#pager_list", data,
            {//edit option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeSubmit: function (postdata, formid) {
                    return tank.checkTankNumber(postdata.number);
                }
            },
            {//add option
                recreateForm: true,
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                beforeSubmit: function (postdata, formid) {
                    return tank.checkTankNumber(postdata.number);
                }
            },
            {},
            {sopt: ['eq', 'ne', 'cn', 'nc']});
    },
    resize: function () {
        $(window).bind("resize", function () {
            var _jqGrid_wrapper = $(".jqGrid_wrapper");
            var _tank_list = $("#tank_list");
            var width = _jqGrid_wrapper.width();
            _tank_list.setGridWidth(width);
            var height = _jqGrid_wrapper.height();
            _tank_list.setGridHeight(height - 116);
        })
    }
};
