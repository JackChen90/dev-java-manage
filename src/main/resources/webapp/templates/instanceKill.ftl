<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>抢购进行时...</title>

    <link rel="stylesheet" href="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <script type="text/javascript" src="${request.contextPath}/webapp/static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/webapp/static/js/jquery.countdown.js"></script>
    <script type="text/javascript" src="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
<div class="container media-middle">
    <header class="gbtags-header">
        <div class="page-header">
            <h1 class="text-primary"><span class="glyphicon glyphicon-th"></span> 抢购进行时 <span class="small">优惠券抢购</span>
            </h1>
        </div>
    </header>

    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading"><h3>INSTANT KILL</h3></div>
            <div class="panel-body text-center fixedheight">
                <h2 class="text-info"><span id="clock"></span></h2>
            </div>
        </div>
    </div>
</div>

<script>
    var time = new Date();
    time.setSeconds(time.getSeconds() + 10);

    $('#clock').countdown(time, function (event) {
        $(this).html(event.strftime('抢购倒计时 %H:%M:%S'));
    }).on('finish.countdown', function () {
        $(this).parent().hide().html('<button class="btn btn-success btn-lg" id="ik_btn">立即抢购</button>').show().addClass('animated bounceIn');
    });
</script>
</body>
</html>
