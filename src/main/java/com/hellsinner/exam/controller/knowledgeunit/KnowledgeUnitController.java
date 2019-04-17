package com.hellsinner.exam.controller.knowledgeunit;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Knowledgeunit;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.knowledgeunit.KnowledgeunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KnowledgeUnitController {

    @Autowired
    private KnowledgeunitService knowledgeunitService;

    @PostMapping("/knowledgeunit/add/{cid}")
    @Authorize(value = 1)
    public Result add(Knowledgeunit knowledgeunit, @PathVariable Integer cid){
        knowledgeunit.setCourid(cid);
        knowledgeunitService.add(knowledgeunit);
        return Result.ok();
    }

    @GetMapping("/knowledgeunit/all/{cid}")
    @Authorize(value = 1)
    public Result list(@PathVariable Integer cid){
        List<Knowledgeunit> knowledgeunits =  knowledgeunitService.get(cid);
        return Result.ok(knowledgeunits);
    }
}
