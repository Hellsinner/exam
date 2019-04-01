package com.hellsinner.exam.controller.knowledgeunit;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Knowledgeunit;
import com.hellsinner.exam.model.web.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnowledgeUnitController {

    @PostMapping("/knowledgeunit/add/{cid}")
    @Authorize(value = 1)
    public Result add(Knowledgeunit knowledgeunit){
        return Result.ok();
    }

}
