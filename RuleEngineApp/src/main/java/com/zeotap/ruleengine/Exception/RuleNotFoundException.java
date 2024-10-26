package com.zeotap.ruleengine.Exception;

public class RuleNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public RuleNotFoundException(String message) {
        super(message);
    }
}
