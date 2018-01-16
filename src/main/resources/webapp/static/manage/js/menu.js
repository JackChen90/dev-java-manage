/**
 * @author jackie chen
 * @create 2017/12/31
 * @description home js
 */
$(function () {
    // database.databaseHeader();
    // database.databaseFooter();
    // database.databaseInit();
    menu.init();
});


var menu = {
    url: "menu/queryData",
    init: function () {
        ajaxPostJson(url,true,'admin',createMenu);
    }
};


function createMenu(data) {
    if (!data || !data.data) {
        return;
    }
    var menuData = data.data;

}

/**
 * 封装ajax post方法
 * @param url 请求地址
 * @param async 异步与否标识
 * @param paramdata 请求数据
 * @param callback 回调函数
 */
function ajaxPostJson(url, async, paramdata, callback) {
    if (async == null || async == "undefined" || async == "null") {
        async = true;
    }
    $.ajax({
        type: 'POST',
        cache: false,
        url: url,
        data: paramdata,
        async: false,
        beforeSend: function () {
        },
        success: function (result) {
            if (result != null && result.flag) {
                var data = result.message;
                if (callback) {
                    callback(data);
                }
            } else {
                // parent.layer.confirm('资料库正在维护中，请耐心等待开放~', {
                //     icon: 6,
                //     btn: ['确定'] //按钮
                // }, function () {
                //     gotoHome();
                // });
            }
        },
        error: function (html) {
            // parent.layer.msg('资料库正在维护中，请耐心等待开放~', {time: 2000, icon: 6});
            // setTimeout(gotoHome, 2000);
        },
        complete: function () {
        }
    });
}