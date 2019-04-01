package com.hellsinner.exam.component;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.enums.TokenState;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.user.UserService;
import com.hellsinner.exam.utils.JsonUtils;
import com.hellsinner.exam.utils.JwtUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Authorize authorize = handlerMethod.getMethodAnnotation(Authorize.class);
        if (authorize == null)
            return true;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        int value = authorize.value();
        String token = request.getHeader("Authorization");
        Map<String, Object> map = JwtUtils.validToken(token);
        TokenState state = (TokenState) map.get("state");

        if (state == TokenState.EXPIRED){
            response.getWriter().write(
                    JsonUtils.objectToJson(Result.failed(ExamException.ExamExceptionEnum.TOKEN_EXPIRED)));
            return false;
        }else if (state == TokenState.INVALID){
            response.getWriter().write(
                    JsonUtils.objectToJson(Result.failed(ExamException.ExamExceptionEnum.TOKEN_EXPIRED)));
            return false;
        }else {
            JSONObject jsonObject = (JSONObject) map.get("data");
            Number idNum = jsonObject.getAsNumber("id");
            int id = idNum.intValue();
            User user = userService.getUser(id);
            if (user == null){
                response.getWriter().write(
                        JsonUtils.objectToJson(Result.failed(ExamException.ExamExceptionEnum.TOKEN_INVALID)));
                return false;
            }
            if (value == -1){
                UserContext.setUserInfo(id);
                return true;
            }else{
                if (user.getUsertype() != value){
                    response.getWriter().write(
                            JsonUtils.objectToJson(Result.failed(ExamException.ExamExceptionEnum.TOKEN_INVALID)));
                    return false;
                }
                UserContext.setUserInfo(id);
                return true;
            }
        }
    }
}
