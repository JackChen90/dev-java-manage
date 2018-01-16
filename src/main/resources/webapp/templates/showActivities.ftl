<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>可参与活动列表</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="${request.contextPath}/webapp/static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/js/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet"
          href="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/css/bootstrap-table.css">
</head>
<body>
<header class="gbtags-header">
    <div class="page-header">
        <h1 class="text-primary"><span class="glyphicon glyphicon-th"></span> 抢购活动列表 <span class="small">可参与活动</span>
        </h1>
    </div>
</header>

<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading"><h3>INSTANT KILL</h3></div>
        <div class="panel-body text-center fixedheight">
            <table id="t_supervision_item">
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#t_supervision_item').bootstrapTable({
            columns: [{
                visible: false,
                field: 'id',
                title: '主键'
            }, {
                title: '序号',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'startDate',
                title: '活动开启时间'
            }, {
                field: 'expireDate',
                title: '活动过期时间'
            }, {
                field: 'name',
                title: '活动名称'
            }],
            url: '${request.contextPath}' + '/a_getActivities',
            pagination: true,
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            responseHandler: function(res) {
                return res.data;
            }
    })

    });
</script>
</body>
</html>