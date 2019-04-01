package com.hellsinner.exam.model.web;

import com.hellsinner.exam.component.ExamException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok() {
        return new Result(null);
    }

    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static Result failed(ExamException.ExamExceptionEnum examExceptionEnum){
        return new Result(examExceptionEnum.getErrCode(),examExceptionEnum.getMessage(),null);
    }
}
