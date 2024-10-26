package com.zeotap.ruleengine.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Rule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ruleString;

	@Lob 
	private String ast;
	
	
	public Rule(String ruleString, String ast) {
        this.ruleString = ruleString;
        this.ast = ast;
    }

	public Rule() {
	}

	public Rule(String ruleString) {
		this.ruleString = ruleString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleString() {
		return ruleString;
	}

	public void setRuleString(String ruleString) {
		this.ruleString = ruleString;
	}

	public String getAst() {
		return ast;
	}

	public void setAst(String ast) {
		this.ast = ast;
	}

}
