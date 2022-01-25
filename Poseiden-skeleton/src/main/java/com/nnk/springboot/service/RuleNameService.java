package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;

import java.util.List;

public interface RuleNameService {

    public RuleName saveRuleName(RuleRequest ruleRequest);

    public List<RuleName> findAllRule();

    public RuleName getRuleById(Integer id);

    public RuleName updateRule(RuleRequest ruleRequest, Integer id);

    public void deleteRuleName(Integer id);
}
