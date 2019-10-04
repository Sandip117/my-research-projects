package com.example.demo.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomErrorTypeTests {
	    @Test
	    public void customError() {
	    	CustomErrorType c = new CustomErrorType("index out of bounds exception");
	    	assertEquals(c.getErrorMessage(),"index out of bounds exception");
	    }
}

