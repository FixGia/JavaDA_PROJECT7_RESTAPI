package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;

import java.util.List;

public interface RuleNameService {

    RuleName saveRuleName(RuleRequest ruleRequest);

    List<RuleName> findAllRule();

    RuleName getRuleById(Integer id);

    void updateRule(RuleRequest ruleRequest, Integer id);

    void deleteRuleName(Integer id);
}
