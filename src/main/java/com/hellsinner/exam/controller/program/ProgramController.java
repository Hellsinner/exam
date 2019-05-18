package com.hellsinner.exam.controller.program;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Submission;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.Rank;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Integer SIZE = 10;

    @GetMapping("/program/list")
    @Authorize(value = 0)
    public Result program(Integer page){
        QuestionResult result = programService.programList(page, SIZE);
        return Result.ok(result);
    }


    @GetMapping("/program/info/{quesid}")
    @Authorize(value = 0)
    public Result info(@PathVariable String quesid){
        List<Question> info = programService.info(quesid);
        return Result.ok(info);
    }

    @PostMapping("/program/submit/{quesid}")
    @Authorize(value = 0)
    public Result submit(@PathVariable String quesid,@RequestBody Submission submission){
        programService.submit(quesid,submission);
        return Result.ok();
    }

    @GetMapping("/program/submissions/{type}/{quesid}")
    @Authorize(value = 0)
    public Result submissions(@PathVariable Integer type,@PathVariable String quesid,Integer page){
        Object submissions = programService.submissions(quesid, type, page, SIZE);
        return Result.ok(submissions);
    }

    @GetMapping("/program/submission/info/{id}")
    @Authorize(value = 0)
    public Result submissions(@PathVariable String id){
        Submission submission = programService.submission(id);
        return Result.ok(submission);
    }

    @GetMapping("/program/today/rank")
    @Authorize(value = 0)
    public Result todayRank(){
        List<Rank> ranks = programService.todayRank();
        return Result.ok(ranks);
    }

    @GetMapping("/program/all/rank")
    @Authorize(value = 0)
    public Result allRank(){
        List<Rank> ranks = programService.allRank();
        return Result.ok(ranks);
    }

    @PostMapping("/testSubmit")
    public Result test(@RequestBody Submission submission){
        System.out.println(submission);
        return Result.ok();
    }
}
