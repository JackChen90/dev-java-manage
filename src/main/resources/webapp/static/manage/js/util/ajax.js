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
    var syncData = null;
    $.ajax({
        type: 'POST',
        cache: false,
        url: url,
        data: paramdata,
        async: async,
        beforeSend: function () {
        },
        success: function (result) {
            if (result != null && result.status) {
                var data = result.data;
                if (callback) {
                    if (!async){
                        syncData = callback(data);
                    }else {
                        callback(data);
                    }
                }
            }
        },
        error: function (html) {
            // parent.layer.msg('资料库正在维护中，请耐心等待开放~', {time: 2000, icon: 6});
            // setTimeout(gotoHome, 2000);
        },
        complete: function () {
        }
    });
    return syncData;
}