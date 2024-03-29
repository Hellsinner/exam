package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.ExamQuesInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface TaskquesMapper {
    int deleteByPrimaryKey(Integer taskquesid);

    int insert(Taskques record);

    int insertSelective(Taskques record);

    Taskques selectByPrimaryKey(Integer taskquesid);

    int updateByPrimaryKeySelective(Taskques record);

    int updateByPrimaryKey(Taskques record);

    @MapKey("quesid")
    Map<String, ExamQuesInfo> selectByTid(@Param("tid") Integer tid);

    Integer selectCountByTid(@Param("tid") Integer tid);

    void insertMany(@Param("quess") List<Taskques> taskques);

    @MapKey("taskquesid")
    Map<String, Taskques> selectByids(@Param("ids") List<Integer> ids);
}