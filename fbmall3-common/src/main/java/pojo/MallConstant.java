package pojo;

public class MallConstant {
    // 对称秘钥，资源服务器使用该秘钥来验证
    public static final String SIGNING_KEY = "jfTZDsfFvFDoz0FY";
    public static final String ORDERS_RESOURCE_ID = "res1";
    public static final String LOGS_RESOURCE_ID = "res2";
    public static final String PRODUCTS_RESOURCE_ID = "res3";
    public static final String REVIEWS_RESOURCE_ID = "res4";
    public static final String USERS_RESOURCE_ID = "res5";

    public static final int FAIL_CODE = 0;
    public static final String FAIL_DESC = "Fail";
    public static final int SUCCESS_CODE = 1;
    public static final String SUCCESS_DESC = "Success";
    public static final int FAIL_CODE_NAME_CONFLICT = 100;
    public static final String FAIL_CODE_NAME_CONFLICT_STRING = "用户名已存在";
    public static final int FAIL_CODE_REQUEST_TIMEOUT = 200;
    public static final String FAIL_CODE_REQUEST_TIMEOUT_STRING = "服务器内部错误或请求超时";
    public static final String REGISTER_SUCCESS = "注册成功";

}
