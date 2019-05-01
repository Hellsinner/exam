package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Stutaskanswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StutaskanswerMapper {
    int deleteByPrimaryKey(Integer taskanswerid);

    int insert(Stutaskanswer record);

    int insertSelective(Stutaskanswer record);

    Stutaskanswer selectByPrimaryKey(Integer taskanswerid);

    int updateByPrimaryKeySelective(Stutaskanswer record);

    int updateByPrimaryKey(Stutaskanswer record);

    Stutaskanswer selectByTUid(@Param("tid") Integer classtaskid,@Param("uid") Integer uid);
}