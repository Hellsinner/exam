package com.hellsinner.exam.service.courstudent;

import com.hellsinner.exam.model.dao.Courstudent;

import java.util.List;

public interface CourstudentService {
    void insert(Courstudent courstudent);

    List<Courstudent> getClassStudents(Integer id);

    void delStudent(Integer cid,Integer uid);

    void addComment(Integer uid, Integer cid, String comment);

    Courstudent getClassStudent(Integer id, Integer uid);
}
