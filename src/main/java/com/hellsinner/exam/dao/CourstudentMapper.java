package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courstudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourstudentMapper {
    int deleteByPrimaryKey(Integer selcourid);

    int insert(Courstudent record);

    int insertSelective(Courstudent record);

    Courstudent selectByPrimaryKey(Integer selcourid);

    int updateByPrimaryKeySelective(Courstudent record);

    int updateByPrimaryKey(Courstudent record);

    List<Courstudent> selectClassStudents(@Param("id") Integer id);

    void deleteByUserId(@Param("cid") Integer cid,@Param("uid") Integer uid);

    void updateByUid(@Param("uid") Integer uid, @Param("cid") Integer cid, @Param("comment") String comment);

    Courstudent selectByUidAndCid(@Param("cid") Integer id,@Param("uid") Integer uid);
}