package com.hellsinner.exam.service.courstudent.impl;

import com.hellsinner.exam.dao.CourstudentMapper;
import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.service.courstudent.CourstudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourstudentServiceImpl implements CourstudentService {

    @Autowired
    private CourstudentMapper courstudentMapper;

    @Override
    public void insert(Courstudent courstudent) {
        courstudentMapper.insert(courstudent);
    }

    @Override
    public List<Courstudent> getClassStudents(Integer id) {
        return courstudentMapper.selectClassStudents(id);
    }

    @Override
    public void delStudent(Integer cid, Integer uid) {
        courstudentMapper.deleteByUserId(cid,uid);
    }

    @Override
    public void addComment(Integer uid, Integer cid, String comment) {
        courstudentMapper.updateByUid(uid,cid,comment);
    }

    @Override
    public Courstudent getClassStudent(Integer id, Integer uid) {
        return courstudentMapper.selectByUidAndCid(id,uid);
    }
}
