/**
 * 根据操作id返回label class
 * @param operationId
 */
function getLabelClass(operationId) {
    switch (operationId){
        //新增
        case 1:
            return "label-primary";
        //删除
        case 2:
            return "label-default";
        //编辑
        case 4:
            return "label-info";
        //导入
        case 8:
            return "label-warning";
        //导出
        case 16:
            return "label-danger";
        //默认
        default:
            return "label-success";
    }
}