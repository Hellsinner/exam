package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrgMapper {
    int deleteByPrimaryKey(Integer orgid);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer orgid);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

    List<Org> searchByName(@Param("parentName") String parentName);
//
//    List<Org> searchByForm(OrgForm orgForm);
//
//    OrgForm getOrgByChildId(@Param("childId") Integer childId);
//
//    List<OrgForm> selectByIds(@Param("orgids") List<Integer> orgids);
}