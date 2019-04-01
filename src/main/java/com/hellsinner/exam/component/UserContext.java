package com.hellsinner.exam.component;

public class UserContext {
    private static ThreadLocal<Integer> userInfo = new ThreadLocal<>();

    public static void setUserInfo(Integer id){
        userInfo.set(id);
    }

    public static Integer getUid(){
        return userInfo.get();
    }
}
