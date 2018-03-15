<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理-角色菜单管理</title>
    <meta name="keywords" content="角色菜单管理">
    <meta name="description" content="角色信息管理">
<#--<link rel="shortcut icon" href="favicon.ico">-->
    <link href="${request.contextPath}/webapp/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">

    <!-- jqgrid-->
    <link href="${request.contextPath}/webapp/static/css/plugins/jqgrid/ui.jqgridffe4.css?0820" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/animate.min.css" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/plugins/jqgrid/ui.jqgridffe4.css?0820" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/custom/build.css" rel="stylesheet">
    <style>
        .wrapper {
            height: 100%;
        }

        .row {
            height: 100%;
        }

        .col-sm-3 {
            height: 100%;
        }

        .col-sm-9 {
            height: 100%;
        }

        .jqGrid_wrapper {
            height: 100%;
            background: white;
        }

        .jqGrid_btn_wrapper {
            padding-left: 10px;
            padding-top: 6px;
        }

        .ibox {
            margin-left: 15px;
            margin-bottom: 10px;
            clear: both;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="ibox">
        <div class="row">
            <div class="btnDiv">
                <div class="columns pull-left">
                    <button class="btn  btn-primary" onclick="roleMenuEdit.save()" type="button">
                        <i aria-hidden="true" class="fa fa-plus"></i>保存
                    </button>
                    <button class="btn  btn-danger" onclick="roleMenuEdit.cancel()" type="button">
                        <i aria-hidden="true" class="fa fa-trash"></i>取消
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row row2">
        <div class="col-sm-9">
            <div class="jqGrid_wrapper jqGrid_menu_wrapper">
                <table id="edit_menu_list"></table>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="jqGrid_wrapper jqGrid_btn_wrapper">
            <#--<table id="btn_list"></table>-->
                <div class="checkbox checkbox-success">
                    <input id="checkbox3" class="styled" type="checkbox">
                    <label for="checkbox3">
                        Success
                    </label>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${request.contextPath}/webapp/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${request.contextPath}/webapp/static/manage/js/util/ajax.js"></script>
<script src="${request.contextPath}/webapp/static/manage/js/util/labelUtil.js"></script>
<script src="${request.contextPath}/webapp/static/manage/js/icon.js"></script>
<script src="${request.contextPath}/webapp/static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/jqgrid/i18n/grid.locale-cnffe4.js?0820"></script>
<#--<script src="${request.contextPath}/webapp/static/js/plugins/jqgrid/jquery.jqGrid.minffe4.js?0820"></script>-->
<script src="${request.contextPath}/webapp/static/js/plugins/jqgrid/jquery.jqGrid.js?0820"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/layer/layer.min.js?0820"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/iCheck/icheck.min.js"></script>
<#--<script src="${request.contextPath}/webapp/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>-->
<script src="${request.contextPath}/webapp/static/manage/js/admin/roleMenuEdit.js"></script>

<script>
    var roleId = ${roleId};
    $(function () {
        $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green"});
        //设置gird row高度
        var _row = $(".row2");
        _row.height(_row.parent().height() - 44);
        $.jgrid.defaults.styleUI = "Bootstrap";
        roleMenuEdit.contextPath = "${request.contextPath}";
        roleMenuEdit.init(roleId);
        roleMenuEdit.resize();
    });
</script>
</body>
</html>