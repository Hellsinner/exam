package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Image;

public interface ImageMapper {
    int deleteByPrimaryKey(Integer imageid);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer imageid);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}