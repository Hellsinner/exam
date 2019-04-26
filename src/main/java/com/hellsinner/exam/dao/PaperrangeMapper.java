package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Paperrange;
import com.hellsinner.exam.model.web.PaperrangeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaperrangeMapper {
    int deleteByPrimaryKey(Integer paperrangeid);

    int insert(Paperrange record);

    int insertSelective(Paperrange record);

    Paperrange selectByPrimaryKey(Integer paperrangeid);

    int updateByPrimaryKeySelective(Paperrange record);

    int updateByPrimaryKey(Paperrange record);

    void batchInsert(@Param("tid") Integer tid,@Param("ids") List<Integer> ids);

    List<PaperrangeInfo> selectByTid(@Param("tid") Integer tid);
}