package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Quesanswer;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuesanswerMapper {
    int deleteByPrimaryKey(Integer answerid);

    int insert(Quesanswer record);

    int insertSelective(Quesanswer record);

    Quesanswer selectByPrimaryKey(Integer answerid);

    int updateByPrimaryKeySelective(Quesanswer record);

    int updateByPrimaryKeyWithBLOBs(Quesanswer record);

    int updateByPrimaryKey(Quesanswer record);

    void insertMany(@Param("list") List<Quesanswer> quesanswers,@Param("id") Integer id);

    void updateMany(@Param("list") List<Quesanswer> quesanswers);

    @MapKey("taskquesid")
    Map<Integer,Quesanswer> selectByids(@Param("ids") List<Integer> ids);

    void updateScore(@Param("qa") Quesanswer quesanswer);
}