package com.hellsinner.exam.service.paperrange.impl;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.dao.PaperrangeMapper;
import com.hellsinner.exam.service.paperrange.PaperRangeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperRangeServiceImpl implements PaperRangeService {
    @Autowired
    private PaperrangeMapper paperrangeMapper;

    @Override
    public void add(Integer tid, List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_KNOWLEDGEUNIT);
        }
        paperrangeMapper.batchInsert(tid,ids);
    }
}
