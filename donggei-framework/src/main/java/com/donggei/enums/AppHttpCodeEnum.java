package com.donggei.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    //登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503,"邮箱已存在"),
    SYSTEM_ERROR(500,"系统错误"),
    REQUIRE_USERNAME(504,"必须填写用户名"),
    REQUIRE_PASSWORD(506,"必须填写用户密码"),
    CONTENT_NOT_NULL(601,"评论信息不能为空"),
    TAG_NOT_NULL(601,"便签名和备注不弄为空"),
    FILE_TYPE_ERROR(507, "只能上传jpg或png格式的图片"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    TAG_INSERT_ERROR(514,"标签插入错误"),
    TAG_GETINFO_ERROR(516,"标签信息获取失败"),
    TAG_DELETE_ERROR(517,"标签删除失败"),
    TAG_UPDATE_ERROR(518,"标签更新失败" ),
    DELETE_ERROR(520,"删除失败"),
    BLOG_INSERT_ERROR(519, "新增博客失败"),
    CATEGORY_INSERT_FAIL(522,"分类新增失败" ),
    CATEGORY_UPDATE_ERROR(523, "分类更新失败" );
    int code;
    String msg;

    private AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}