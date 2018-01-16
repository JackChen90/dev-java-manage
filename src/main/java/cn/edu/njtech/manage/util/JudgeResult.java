package cn.edu.njtech.manage.util;

/**
 * @author jackie chen
 * @create 2017/10/27
 * @description JudgeResult 校验实体类
 */
public class JudgeResult<T> {
    /**
     * 校验正确性
     */
    private Boolean flag;

    /**
     * 校验过程产生的数据
     */
    private T data;

    /**
     * 校验错误码
     */
    private Integer errCode;

    /**
     * 校验描述
     */
    private String msg;

    public JudgeResult(Boolean flag) {
        this.flag = flag;
    }

    public JudgeResult(Boolean flag, Integer errCode, String msg) {
        this.flag = flag;
        this.errCode = errCode;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}