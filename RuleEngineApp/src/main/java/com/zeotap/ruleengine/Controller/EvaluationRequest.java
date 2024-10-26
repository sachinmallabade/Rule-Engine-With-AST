package com.zeotap.ruleengine.Controller;

import java.util.Map;

import com.zeotap.ruleengine.Model.Node;

public class EvaluationRequest {

	private Node ast; 
	private Map<String, Object> data; 
	public Node getAst() {
		return ast;
	}
	public void setAst(Node ast) {
		this.ast = ast;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	

}
