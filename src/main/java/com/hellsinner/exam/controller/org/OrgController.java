package com.hellsinner.exam.controller.org;

import com.hellsinner.exam.model.dao.Org;
import com.hellsinner.exam.model.web.OrgForm;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.org.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrgController {

    @Autowired
    private OrgService orgService;

    @GetMapping("/org/parent")
    public Result searchParent(String parentName){
        List<Org> search = orgService.searchParent(parentName);
        return Result.ok(search);
    }

    @GetMapping("/org/child")
    public Result searchChild(OrgForm orgForm){
        List<Org> search = orgService.searchChild(orgForm);
        return Result.ok(search);
    }
}
