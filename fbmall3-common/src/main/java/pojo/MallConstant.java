package pojo;

public class MallConstant {
    // 对称秘钥，资源服务器使用该秘钥来验证
    public static final String SIGNING_KEY = "jfTZDsfFvFDoz0FY";
    public static final String UAA_RESOURCE_ID = "res0";
    public static final String ORDERS_RESOURCE_ID = "res1";
    public static final String LOGS_RESOURCE_ID = "res2";
    public static final String PRODUCTS_RESOURCE_ID = "res3";
    public static final String REVIEWS_RESOURCE_ID = "res4";
    public static final String USERS_RESOURCE_ID = "res5";

    public static final int FAIL_CODE = 0;
    public static final String FAIL_DESC = "Fail";
    public static final int SUCCESS_CODE = 1;
    public static final String    SUCCESS_DESC = "Success";

    public static final int FAIL_CODE_REQUEST_TIMEOUT = 10000;
    public static final String FAIL_CODE_REQUEST_TIMEOUT_DESC = "服务器内部错误或请求超时";
    /*
    * Users服务代码
    * */
    // 成功状态
    public static final String REGISTER_SUCCESS = "恭喜注册成功";
    // 失败状态
    public static final int FAIL_CODE_NAME_CONFLICT = 20000;
    public static final String FAIL_CODE_NAME_CONFLICT_DESC = "用户名已存在";
    public static final int FAIL_CODE_DB_ERROR = 20001;
    public static final String FAIL_CODE_DB_ERROR_DESC = "数据库错误";
    public static final int FAIL_CODE_ILLEGAL_FIELD = 20002;
    public static final String FAIL_CODE_ILLEGAL_FIELD_DESC = "非法提交数据";
}
