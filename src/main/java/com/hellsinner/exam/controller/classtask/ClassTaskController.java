package com.hellsinner.exam.controller.classtask;

import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.dao.Quesanswer;
import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.*;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ClassTaskController {

    @Autowired
    private ClassTaskService classTaskService;

    @PostMapping("/classtask/create")
    @Authorize(value = 1)
    public Result create(@RequestBody Courseclasstask courseclasstask){
        classTaskService.add(courseclasstask);
        return Result.ok();
    }

    @PostMapping("/classtask/notice/{ctid}")
    @Authorize(value = 1)
    public Result notice(@PathVariable Integer ctid){
        classTaskService.notice(ctid);
        return Result.ok();
    }

    @GetMapping("/classtask/list")
    @Authorize(value = 1)
    public Result list(){
        List<CourseclasstaskInfo> list = classTaskService.list();
        return Result.ok(list);
    }

    @GetMapping("/classtask/list/1")
    @Authorize(value = 1)
    public Result prelist(){
        List<CourseclasstaskInfo> list = classTaskService.list1();
        return Result.ok(list);
    }

    @GetMapping("/classtask/myjoin")
    @Authorize(value = 0)
    public Result myjoin(){
        List<CourseclasstaskInfo> list = classTaskService.myjoin();
        return Result.ok(list);
    }

    @GetMapping("/classtask/myjoin/1")
    @Authorize(value = 0)
    public Result myjoin1(){
        List<CourseclasstaskInfo> list = classTaskService.myjoin1();
        return Result.ok(list);
    }

    @GetMapping("/classtask/start/{ctid}")
    @Authorize(value = 0)
    public Result start(@PathVariable Integer ctid, HttpServletResponse response){
        ExamInfo start = classTaskService.start(ctid,0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format1 = format.format(new Date());
        response.setHeader("date",format1);
        return Result.ok(start);
    }

    @GetMapping("/classtask/view/{ctid}")
    @Authorize(value = 0)
    public Result view(@PathVariable Integer ctid){
        ViewInfo viewInfo = classTaskService.view(ctid, UserContext.getUid());
        return Result.ok(viewInfo);
    }

    @PostMapping("/classtask/submit/{ctid}")
    @Authorize(value = 0)
    public Result submit(@PathVariable Integer ctid, @RequestBody List<Quesanswer> quesanswers){
        classTaskService.submit(ctid,quesanswers);
        return Result.ok();
    }

    @GetMapping("/classtask/examine/{ctid}")
    @Authorize(value = 1)
    public Result examine(@PathVariable Integer ctid){
        List<ExamineInfo> examine = classTaskService.examine(ctid);
        return Result.ok(examine);
    }

    @GetMapping("/classtask/examine/view/{ctid}/{uid}")
    @Authorize(value = 1)
    public Result examineview(@PathVariable Integer ctid,@PathVariable Integer uid){
        ViewInfo view = classTaskService.examineview(ctid, uid);
        return Result.ok(view);
    }

    @PostMapping("/classtask/examine/submit/{ctid}/{uid}")
    @Authorize(value = 1)
    public Result examinesubmit(@PathVariable Integer ctid, @PathVariable Integer uid,
                                @RequestBody List<Taskques> taskques){
        classTaskService.examinesubmit(ctid, uid, taskques);
        return Result.ok();
    }

    @GetMapping("/classtask/notify")
    @Authorize(value = 0)
    public Result notifyMe(){
        List<CourseclasstaskInfo> courseclasstaskInfos = classTaskService.notifyMe();
        return Result.ok(courseclasstaskInfos);
    }
}
