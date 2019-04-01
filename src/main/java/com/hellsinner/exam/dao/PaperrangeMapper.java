package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Paperrange;

public interface PaperrangeMapper {
    int deleteByPrimaryKey(Integer paperrangeid);

    int insert(Paperrange record);

    int insertSelective(Paperrange record);

    Paperrange selectByPrimaryKey(Integer paperrangeid);

    int updateByPrimaryKeySelective(Paperrange record);

    int updateByPrimaryKey(Paperrange record);
}