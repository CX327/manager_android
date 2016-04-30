package org.com.manager.bean;

/**
 * Created by jie.hua on 2016/4/23.
 * api错误枚举
 */
public enum ApiErrorCodeEnum {
    /**
     * 未知错误
     */
    UNKNOWN("1", "未知错误"),
    JSONERROR("2", "解析json数据失败"),
    /**
     * 食谱error code
     */
    RECIPES_NONAME("204601", "菜谱名称不能为空"),
    RECIPES_NOINFO("204602", "查询不到相关信息"),
    RECIPES_LANGNAME("204603", "菜谱名过长"),
    RECIPES_ERRORFLAGID("204604", "错误的标签ID"),
    RECIPES_NOTINFO("204605", "查询不到数据"),
    RECIPES_ERRORID("204606", "错误的菜谱ID"),

    /**
     * 列车error code
     */
    TRAIN_NULLNUMBER("202201", "车次不能为空"),
    TRAIN_NONUMBERINFO("202202", "查询不到车次的相关信息"),
    TRAIN_NULLSTATION("202203", "出发站或终点站不能为空"),
    TRAIN_NORESULT("202204", "查询不到结果"),
    TRAIN_ERRORSTART("202205", "错误的出发站名称"),
    TRAIN_ERROREND("202206", "错误的到达站名称"),
    TRAIN_ERRORREQUEST("202208", "错误的请求，请确认传递的参数正确"),
    TRAIN_NETERROR("202209", "请求12306网络错误,请重试"),
    TRAIN_ERRORQUERY("202212", "查询出错"),

    /**
     * 系统 error code
     */
    SYSTEM_ERRORKEY("10001", "错误的请求KEY"),
    SYSTEM_NOAUTHORITY("10002", "该KEY无请求权限"),
    SYSTEM_PASTDUEKEY("10003", "KEY过期"),
    SYSTEM_ERROROPENID("10004", "错误的OPENID"),
    SYSTEM_PASTDUECHECK("10005", "应用未审核超时，请提交认证"),
    SYSTEM_UNKNOWNREQUEST("10007", "未知的请求源"),
    SYSTEM_INTERDICTORYIP("10008", "被禁止的IP"),
    SYSTEM_INTERDICTORYKEY("10009", "被禁止的KEY"),
    SYSTEM_OUTSTRIPPINGLIMITIP("10011", "当前IP请求超过限制"),
    SYSTEM_OUTSTRIPLIMITREQUEST("10012", "请求超过次数限制"),
    SYSTEM_INTERNALERROR("10014", "系统内部异常"),
    SYSTEM_INTERFACEUPHOLD("10020", "接口维护"),
    SYSTEM_INTERFACESTOP("10021", "接口停用");

    private String errorId;
    private String errorDescription;

    ApiErrorCodeEnum() {
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    ApiErrorCodeEnum(String errorId, String errorDescription) {
        this.errorId = errorId;
        this.errorDescription = errorDescription;
    }
}
