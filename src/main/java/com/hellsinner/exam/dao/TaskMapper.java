package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.model.web.TaskInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskid);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskid);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<TaskInfo> selectByUid(@Param("uid") Integer uid);

    TaskInfo selectInfo(@Param("tid") Integer tid);

    List<TaskInfo> selectListInfo(@Param("ids") List<Integer> ids);

    List<TaskInfo> selectByCid(@Param("cid") Integer cid);
}