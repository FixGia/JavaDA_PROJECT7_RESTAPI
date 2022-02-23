package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RuleNameServiceImpl implements RuleNameService {

    private RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public RuleName saveRuleName(RuleRequest ruleRequest) {

        try {
            RuleName ruleName = new RuleName();
            ruleName.setName(ruleRequest.getName());
            ruleName.setDescription(ruleRequest.getDescription());
            ruleName.setJson(ruleRequest.getJson());
            ruleName.setTemplate(ruleRequest.getTemplate());
            ruleName.setSqlStr(ruleRequest.getSqlStr());
            ruleName.setSqlPart(ruleRequest.getSqlPart());
            ruleNameRepository.save(ruleName);
            log.info("add a new rule");
            return ruleName;
        } catch (NotConformDataException e) {
            log.error("can't add a new rule");
            return null;
        }
    }

    @Override
    public List<RuleName> findAllRule() {
       try {
           log.info("displayed RuleList");
           return ruleNameRepository.findAll();
       } catch (DataNotFoundException e) {
           log.error("Error to displayed RuleList");
           return Collections.emptyList();
       }
    }

    @Override
    public RuleName getRuleById(Integer id) {
        try {
            Optional<RuleName> getRule = ruleNameRepository.findById(id);
            log.info("Rule with id {} was found", id);
            return getRule.get();
        } catch (DataNotFoundException e) {
            log.error("Rule with id {} doesn't exist in DB", id);
            return  null;
        }
    }

    @Override
    public void updateRule(RuleRequest ruleRequest, Integer id) {
        Optional<RuleName> ruleToUpdate = ruleNameRepository.findById(id);
        if (ruleToUpdate.isPresent()) {

            ruleToUpdate.get().setId(id);

            ruleToUpdate.get().setName(ruleRequest.getName());
            ruleToUpdate.get().setDescription(ruleRequest.getDescription());
            ruleToUpdate.get().setJson(ruleRequest.getJson());
            ruleToUpdate.get().setTemplate(ruleRequest.getTemplate());
            ruleToUpdate.get().setSqlStr(ruleRequest.getSqlStr());
            ruleToUpdate.get().setSqlPart(ruleRequest.getSqlPart());
            ruleNameRepository.save(ruleToUpdate.get());
            log.info("Rule with id {} was updated", id);
            ruleToUpdate.get();
            return;
        } throw new DataNotFoundException("Rule doesn't exist in Db");
    }

    @Override
    public void deleteRuleName(Integer id) {
        try {
            ruleNameRepository.deleteById(id);
            log.info("Rule with id{} was deleted", id);
        } catch (DataNotFoundException e) {
            log.error("Rule with id{} doesn't found in DB", id);
        }
    }
}
