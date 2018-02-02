<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理-用户信息管理</title>
    <meta name="keywords" content="用户信息管理">
    <meta name="description" content="用户信息管理">
<#--<link rel="shortcut icon" href="favicon.ico">-->
    <link href="${request.contextPath}/webapp/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

    <!-- jqgrid-->
    <link href="${request.contextPath}/webapp/static/css/plugins/jqgrid/ui.jqgridffe4.css?0820" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/animate.min.css" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

    <style>
        .wrapper {
            height: 100%;
        }

        .jqGrid_wrapper {
            height: 100%;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
<#--<div class="row">-->
<#--<div class="col-sm-12">-->
<#--<div class="ibox ">-->
<#--<div class="ibox-title">-->
<#--<h5>用户信息管理</h5>-->
<#--</div>-->
<#--<div class="ibox-content">-->

    <div class="jqGrid_wrapper">
        <table id="user_list"></table>
        <div id="pager_list"></div>
    </div>
<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->
</div>
<#--<input type="hidden" value="${menuId}">-->
<script src="${request.contextPath}/webapp/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${request.contextPath}/webapp/static/manage/js/util/ajax.js"></script>
<script src="${request.contextPath}/webapp/static/manage/js/admin/userInfo.js"></script>
<script src="${request.contextPath}/webapp/static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/jqgrid/i18n/grid.locale-cnffe4.js?0820"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/jqgrid/jquery.jqGrid.minffe4.js?0820"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script>
    var menuId = ${menuId};
    var type =${type};
    $(function () {
        $.jgrid.defaults.styleUI = "Bootstrap";
        user.contextPath = "${request.contextPath}";
        user.init(menuId, type);
        user.resize();
    });
</script>
<!--<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>-->
</body>
</html>