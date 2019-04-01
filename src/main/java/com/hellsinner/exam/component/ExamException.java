package com.hellsinner.exam.component;

public class ExamException extends RuntimeException{
    private int errorCode;

    public ExamException(ExamExceptionEnum examExceptionEnum){
        super(examExceptionEnum.message);
        this.errorCode = examExceptionEnum.errCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public enum ExamExceptionEnum{
        LOGIN_PARAMS_Insufficient(100000,"登录参数不足"),
        REGISTER_PARAMS_Insufficient(100001,"注册参数不足"),
        KAPTCHA_LOAD_FAILED(100002,"验证码加载失败"),
        KAPTCHA_INPUT_ERROR(100003,"验证码输入错误"),
        NOT_FOUND_USER(100004,"未查找到对应邮箱的用户,请检查你的邮箱是否正确"),
        PASSWORD_INPUT_ERROR(100005,"密码输入错误,请重新输入密码"),
        TOKEN_EXPIRED(100006,"登录已过期,请重新登录"),
        TOKEN_INVALID(100007,"登录信息无法识别"),
        EMAIL_HAS_REGISTER(100008,"邮箱已经被注册"),
        PASSWORD_IS_BLANK(100009,"密码不能为空"),
        OLDPASS_SAME_NEWPASS(100010,"新旧密码相同,请输入新的密码"),
        CREATE_CLASS_PARAMS_Insufficient(100011,"创建班级参数不足"),
        AGAIN_JOIN_CLASS(100012,"你已经加入该班级"),
        UPLOAD_PICTURE_FAILE(100013,"图片上传失败"),
        SERVER_ERROR(500,"服务器出错啦");

        private int errCode;
        private String message;

        ExamExceptionEnum(int errCode,String message){
            this.errCode = errCode;
            this.message = message;
        }

        public int getErrCode() {
            return errCode;
        }

        public String getMessage() {
            return message;
        }
    }
}
