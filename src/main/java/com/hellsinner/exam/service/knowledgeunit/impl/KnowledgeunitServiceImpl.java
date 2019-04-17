package com.hellsinner.exam.service.knowledgeunit.impl;

import com.hellsinner.exam.dao.KnowledgeunitMapper;
import com.hellsinner.exam.model.dao.Knowledgeunit;
import com.hellsinner.exam.service.knowledgeunit.KnowledgeunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeunitServiceImpl implements KnowledgeunitService {
    @Autowired
    private KnowledgeunitMapper knowledgeunitMapper;

    @Override
    public void add(Knowledgeunit knowledgeunit) {
        knowledgeunitMapper.insert(knowledgeunit);
    }

    @Override
    public List<Knowledgeunit> get(Integer cid) {
        return knowledgeunitMapper.selectByCid(cid);
    }
}
