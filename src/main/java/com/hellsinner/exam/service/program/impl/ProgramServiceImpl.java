package com.hellsinner.exam.service.program.impl;

import com.google.common.collect.Lists;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Submission;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.Rank;
import com.hellsinner.exam.service.program.ProgramService;
import com.hellsinner.exam.service.user.UserService;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    private static final String suffix = "%";
    private static final String ZERO = "0";

    @Override
    public QuestionResult programList(Integer page, Integer size) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("typenum",7);
        //指定返回的字段
        BasicDBObject fieldsObject = new BasicDBObject();

        fieldsObject.put("qdata.data.option.answer", false);
        fieldsObject.put("qdata.data.Analysis", false);
        fieldsObject.put("answer", false);

        Query query = new BasicQuery(dbObject.toJson(), fieldsObject.toJson());
        query.skip((page-1)*size).limit(size);
        long count1 = mongoTemplate.count(new Query().addCriteria(Criteria.where("typenum").is(7)), Question.class);
        QuestionResult result = new QuestionResult();
        result.setCount(count1);
        List<Question> questions = mongoTemplate.find(query, Question.class);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        for (Question question : questions){
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("quesid").is(question.getId()));
            long count = mongoTemplate.count(query1, Submission.class);
            query1.addCriteria(Criteria.where("result").is(0));
            long acCount = mongoTemplate.count(query1, Submission.class);

            query1.addCriteria(Criteria.where("user_id").is(UserContext.getUid()));
            long count2 = mongoTemplate.count(query1, Submission.class);

            String format = numberFormat.format(((double) acCount / (double) count)*100);
            if (format.equals("NaN")){
                format = ZERO;
            }
            question.setSubmitTotal(count);
            question.setAcRate(format+suffix);
            question.setIsAc(count2 != 0);
        }
        result.setQuestions(questions);
        return result;
    }

    @Override
    public List<Question> info(String quesid) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("_id",quesid);

        BasicDBObject fieldsObject = new BasicDBObject();

        fieldsObject.put("qdata.data.option.answer", false);
        fieldsObject.put("qdata.data.Analysis", false);
        fieldsObject.put("answer", false);

        Query query = new BasicQuery(dbObject.toJson(), fieldsObject.toJson());

        return mongoTemplate.find(query,Question.class);
    }

    @Override
    public void submit(String quesid, Submission submission) {
        submission.setId(null);
        submission.setQuesid(quesid);
        Integer uid = UserContext.getUid();
        submission.setUser_id(uid);
        User user = userService.getUser(uid);
        submission.setUsername(user.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        submission.setSubmitTime(format1);

        mongoTemplate.insert(submission);
    }

    @Override
    public Object submissions(String quesid, Integer type, Integer page, Integer size) {
        if (type==1){
            //我的提交
            return mySubmissions(quesid,page,size);
        }else {
            //通过的提交
            return acSubmissions(quesid,page,size);
        }
    }

    @Override
    public Submission submission(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,Submission.class);
    }

    @Override
    public List<Rank> todayRank() {
        DateTime startTime = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime endTime = DateTime.now().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(0);
        Date start = startTime.toDate();
        Date end = endTime.toDate();


        ObjectId startId = new ObjectId(start);
        ObjectId endId = new ObjectId(end);

        MatchOperation matchstartId = Aggregation.match(Criteria.where("_id").gte(startId));
        MatchOperation matchendId = Aggregation.match(Criteria.where("_id").lte(endId));
        MatchOperation matchresult = Aggregation.match(Criteria.where("result").is(0));

        Aggregation agg = Aggregation.newAggregation(
                matchstartId,
                matchendId,
                matchresult,
                Aggregation.group("username").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC,"count"),
                Aggregation.limit(5)
        );


        AggregationResults<Rank> submissions =
                mongoTemplate.aggregate(agg, "submissions", Rank.class);
        return submissions.getMappedResults();
    }

    @Override
    public List<Rank> allRank() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("username").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC,"count"),
                Aggregation.limit(5)
        );


        AggregationResults<Rank> submissions =
                mongoTemplate.aggregate(agg, "submissions", Rank.class);
        return submissions.getMappedResults();
    }

    private Object mySubmissions(String quesid,Integer page,Integer size){
        Integer uid = UserContext.getUid();
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(uid));
        query.addCriteria(Criteria.where("quesid").is(quesid));
        query.skip((page-1)*size).limit(size);

        return mongoTemplate.find(query,Submission.class);
    }

    private Object acSubmissions(String quesid,Integer page,Integer size){
        Query query = new Query();
        query.addCriteria(Criteria.where("result").is(0));
        query.addCriteria(Criteria.where("quesid").is(quesid));
        query.skip((page-1)*size).limit(size);
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(new Sort.Order(Sort.Direction.ASC,"statistic_info.time_cos"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"statistic_info.memory_cost"));
        query.with(new Sort(orders));

        return mongoTemplate.find(query,Submission.class);
    }
}
