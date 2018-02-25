$(function () {
    //打开图标列表
    $('.gray-bg').on('click', '#ico-btn', function () {
        layer.open({
            type: 2,
            title: '图标列表',
            closeBtn: 1,
            shadeClose: true,
            skin: 'layer-ext-moon',
            area: ['800px', '520px'],
            content: icon.contextPath + '/fontIconList'
        });
    });
});
var icon = {
    bindClick: false,
    contextPath: null,
    element: function (value, options) {
        //自定义元素
        var elemStr = '<div class="fontDiv">\n' +
            '<input id="icon" name="icon" style="width: 60%;" class="form-control fontInput" type="text"\n' +
            ' value="' + value + '"' +
            ' placeholder="例如：fa fa-circle-o">\n' +
            '<input id="ico-btn" style="width: 100px;"  class="btn btn-warning fontInput" type="button" value="选择图标">\n' +
            '<span style="clear:both"></span>'
            '</div>\n';
        //通过jquery返回自定义元素
        return $(elemStr)[0];
    },
    value: function (elem, operation, value) {
        if (!$('input', $(elem)[0]).eq(0)) {
            return '';
        }
        if (operation === 'get') {
            return $("input", $(elem)[0]).eq(0).val();
        } else if (operation === 'set') {
            $('input', $(elem)[0]).eq(0).val(value);
        }
    }
};