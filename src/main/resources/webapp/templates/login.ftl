<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>Login</title>
    <link href="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${request.contextPath}/webapp/static/manage/css/login.css" rel="stylesheet">
    <script src="${request.contextPath}/webapp/static/js/jquery-1.11.3.js"></script>
    <script src="${request.contextPath}/webapp/static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div id="box">
            <h2>Manage Login</h2>
            <hr>
            <form class="form-horizontal" action="${request.contextPath}/login" method="post" id="contact_form">
                <fieldset>
                    <!-- Form Name -->

                    <div class="form-group">
                        <div class="col-md-12" style="display: none" >
                            invalid login invalid login invalid login invalid logininvalid login invalid logininvalid login invalid login
                        </div>
                    </div>
                    <!-- Text input-->

                    <div class="form-group">

                        <div class="col-md-12">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input name="username" placeholder="username" class="form-control" type="text">
                            </div>
                        </div>
                    </div>


                    <!-- Text input-->
                    <div class="form-group">

                        <div class="col-md-12">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input name="password" placeholder="password" class="form-control" type="text">
                            </div>
                        </div>
                    </div>


                    <div class="form-group">

                        <div class="col-md-12">
                            <button type="submit" class="btn btn-md btn-danger pull-right">Login</button>
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>
