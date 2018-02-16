/**
 * @author jackie chen
 * @create 2017/12/31
 * @description home js
 */
$(function () {
    menu.init();
});


var menu = {
    //jqGrid查询数据
    url: "menu/queryMenuData",
    init: function () {
        ajaxPostJson(menu.url, false, {type: 1}, createMenu);
    }
};

/**
 * 添加子菜单
 * @param children
 * @param level
 * @returns {string}
 */
function addChildren(children, level) {
    if (!children) {
        return;
    }
    var navClass;
    switch (level) {
        case 2:
            navClass = "nav-second-level";
            break;
        case 3:
            navClass = "nav-third-level";
            break;
    }
    var result = "<ul class=\"nav " + navClass + " collapse\"  aria-expanded=\"false\">";
    for (var i = 0; i < children.length; i++) {
        if (children[i].children) {
            result += "<li><a href=\"#\">" + children[i].menuName + "<span class=\"fa arrow\"></span></a>";
            result += addChildren(children[i].children, level + 1);
        } else {
            result += "<li><a class=\"J_menuItem\" href=\"" + children[i].url
                + "?menuId=" + children[i].id
                + "&type=" + 1 + "\">" + children[i].menuName + "</a>";
        }
        result += "</li>";
    }
    result += "</ul>";
    return result;
}

function createMenu(data) {
    if (!data) {
        return;
    }
    var menuData = data;
    for (var i = 0; i < menuData.length; i++) {
        var item = menuData[i];
        var result = "<li>";
        if (item.children) {
            result += "<a href=\"#\">";
        } else {
            result += "<a class=\"J_menuItem\" href=\"" + item.url + "?"
                + "menuId=" + item.id
                + "&type=" + 1
                + "\">";
        }
        result += "<i class=\"fa fa-columns\"></i>" +
            " <span class=\"nav-label\">" + item.menuName + "</span>";
        result += " <span class=\"fa " + (item.children ? 'arrow' : '') + "\"></span></a>";

        if (item.children) {
            result += addChildren(item.children, 2);
        }
        result += "</li>";
        $("#side-menu").append(result);
    }
}
