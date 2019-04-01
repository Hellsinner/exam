package com.hellsinner.exam.service.org;

import com.hellsinner.exam.model.dao.Org;
import com.hellsinner.exam.model.web.OrgForm;

import java.util.List;

public interface OrgService {
    List<Org> searchParent(String parentName);

    List<Org> searchChild(OrgForm orgForm);

    void addOrg(Org org);

    OrgForm getUserOrg(Integer childId);

    Org getOrg(Integer orgId);

    List<OrgForm> batchgetOrg(List<Integer> orgids);
}
