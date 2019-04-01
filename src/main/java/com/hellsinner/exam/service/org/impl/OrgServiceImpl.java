package com.hellsinner.exam.service.org.impl;

import com.hellsinner.exam.dao.OrgMapper;
import com.hellsinner.exam.model.dao.Org;
import com.hellsinner.exam.model.web.OrgForm;
import com.hellsinner.exam.service.org.OrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgMapper orgMapper;

    @Override
    public List<Org> searchParent(String parentName) {
        if (StringUtils.isBlank(parentName)){
            return new ArrayList<>();
        }
        return orgMapper.searchByName(parentName);
    }

    @Override
    public List<Org> searchChild(OrgForm orgForm) {
        if (orgForm.getParentId() == null){
            return new ArrayList<>();
        }
        return orgMapper.searchByForm(orgForm);
    }

    @Override
    public void addOrg(Org org) {
        orgMapper.insert(org);
    }

    @Override
    public OrgForm getUserOrg(Integer childId) {
        return orgMapper.getOrgByChildId(childId);
    }

    @Override
    public Org getOrg(Integer orgId) {
        return orgMapper.selectByPrimaryKey(orgId);
    }

    @Override
    public List<OrgForm> batchgetOrg(List<Integer> orgids) {
        return orgMapper.selectByIds(orgids);
    }
}
