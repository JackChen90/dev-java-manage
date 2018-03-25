<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <title>管理 - 主页</title>

    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${request.contextPath}/webapp/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/animate.min.css" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/css/custom/home.css" rel="stylesheet">
    <link>
    <style>
        .profile-element{
            padding-left: 20px;
        }
    </style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" height="70" width="70"
                                   src="../webapp/static/img/profile_small.jpg"/></span>
                        <h3 class="" style="color: #ffffff">
                            </i>管理系统
                        </h3>
                    </div>
                    <div class="dropdown profile-element hidden">
                        <span><img alt="image" class="img-circle" src="webapp/static/img/profile_small.jpg"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs"><strong class="font-bold">Beaut-zihan</strong></span>
                            <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
                        </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="form_avatar.html">修改头像</a>
                            </li>
                            <li><a class="J_menuItem" href="profile.html">个人资料</a>
                            </li>
                            <li><a class="J_menuItem" href="contacts.html">联系我们</a>
                            </li>
                            <li><a class="J_menuItem" href="mailbox.html">信箱</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="login.html">安全退出</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">M
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->

    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <!--标题以及收缩button-->
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                        <i class="fa fa-bars"></i>
                    </a>
                    <div class="home_title">
                        <span class="title">管理系统</span>
                    </div>
                </div>
            </nav>
        </div>

        <!--tab框开始-->
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <!--退出按钮-->
            <a href="login.html" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <!--tab框结束-->

        <!--首页固定tab开始-->
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="index_v2.html?v=4.0"
                    frameborder="0" data-id="index_v2.html" seamless></iframe>
        </div>
        <!--首页固定tab结束-->

        <!--尾栏开始-->
        <div class="footer">
            <div class="pull-right">&copy; 2017-2018 <a href="http://www.njtech.edu.cn/" target="_blank">南京工业大学</a>
            </div>
        </div>
        <!--尾栏结束-->
    </div>
    <!--右侧部分结束-->

</div>

<script src="${request.contextPath}/webapp/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${request.contextPath}/webapp/static/manage/js/util/ajax.js"></script>
<script src="${request.contextPath}/webapp/static/manage/js/admin/menu.js"></script>
<script src="${request.contextPath}/webapp/static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/layer/layer.min.js"></script>
<script src="${request.contextPath}/webapp/static/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="${request.contextPath}/webapp/static/js/contabs.min.js"></script>
<script src="${request.contextPath}/webapp/static/js/plugins/pace/pace.min.js"></script>
</body>
</html>