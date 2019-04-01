package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgForm {
    private Integer parentId;

    @NotBlank
    private String parentName;

    private Integer childId;

    @NotBlank
    private String childName;

    public static Org adapterChildOrg(OrgForm orgForm){
        Org org = new Org();
        org.setOrgname(orgForm.getChildName());
        org.setOrgOrgid(orgForm.getParentId());
        return org;
    }

    public static Org adapterParentOrg(OrgForm orgForm){
        Org org = new Org();
        org.setOrgOrgid(1);
        org.setOrgname(orgForm.getParentName());
        return org;
    }
}
