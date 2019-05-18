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
        SERVER_ERROR(500,"服务器出错啦"),
        LOGIN_PARAMS_Insufficient(100000,"登录参数不足"),
        REGISTER_PARAMS_Insufficient(100001,"注册参数不足"),
        KAPTCHA_LOAD_FAILED(100002,"验证码加载失败"),
        KAPTCHA_INPUT_ERROR(100003,"验证码输入错误"),
        NOT_FOUND_USER(100004,"未查找到对应邮箱的用户,请检查输入的邮箱是否正确"),
        PASSWORD_INPUT_ERROR(100005,"密码输入错误,请重新输入密码"),
        TOKEN_EXPIRED(100006,"登录已过期,请重新登录"),
        TOKEN_INVALID(100007,"登录信息无法识别"),
        EMAIL_HAS_REGISTER(100008,"邮箱已经被注册"),
        PASSWORD_IS_BLANK(100009,"密码不能为空"),
        OLDPASS_SAME_NEWPASS(100010,"新旧密码相同,请输入新的密码"),
        CREATE_CLASS_PARAMS_Insufficient(100011,"创建班级参数不足"),
        AGAIN_JOIN_CLASS(100012,"你已经加入该班级"),
        UPLOAD_PICTURE_FAILE(100013,"图片上传失败"),
        NOT_A_PICTURE(100015,"你上传的并不是图片"),
        NOT_HAVE_QUESTION(100014,"你并没有提交任何试题"),
        NOT_HAVE_KNOWLEDGEUNIT(100016,"你并没有选择任何知识点"),
        CONDITION_NOT_ENOUGH(100017,"组卷条件不足"),
        AUTH_NOT_ENOUGH(100018,"你并不是该试卷的创建者,无权操作"),
        AGAIN_TASK_AUTH(100019,"该用户已经已经被授权过了,无需再次操作"),
        AI_QUESTION_NOT_ENOUTH(100020,"很抱歉,你提交的组卷条件无法达成,请前往题库增加满足你条件的题目"),
        AI_QUESTION_TYPE_COUNT_GT_MAX(100021,"单一类型题目数量过大"),
        NOT_HAVE_TASK(100022,"没有找到该试卷"),
        AUTH_CLASS_TASK_NOT_ENOUGH(100023,"你无权操作"),
        CLASS_TASK_TIME_ERROR(100024,"考试结束时间大于开始时间"),
        CLASS_TASK_NOT_HAVE_QUESTION(100025,"请前往关联题目后通知学生考试"),
        TASK_POINT_NOT_EQUAL(100026,"很抱歉,你提交的组卷条件无法达成,请注意你设置的分数"),
        QUESTION_NOT_HAVE_TYPE(100027,"没有这样的题目类型"),
        TASK_HAD_QUESTION(100028,"该试卷已经关联过试题了,不能重复关联"),
        OPER_HAS_MISTAKE(100029,"操作错误");


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
