package com.hellsinner.exam.service.knowledgeunit;

import com.hellsinner.exam.model.dao.Knowledgeunit;

import java.util.List;

public interface KnowledgeunitService {
    void add(Knowledgeunit knowledgeunit);

    List<Knowledgeunit> get(Integer cid);
}
