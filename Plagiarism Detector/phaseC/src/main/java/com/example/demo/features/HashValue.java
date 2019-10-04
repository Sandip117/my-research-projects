/*
 * Created on Jun 22, 2005
 */
package com.example.demo.features;

/**
 * Simple class that stores a hash value.
 * 
 * @author team211
 */
public class HashValue implements Comparable {

	
	/**
	 * Mask used to retrieve the value of the least significant byte of an integer number.  
	 */
	public static final int MASK = 255;
		
	private String val; 
	
	/**
	 * Initialize the hash value with a string.
	 * 
	 * @param hashvalue hash value as a string
	 */
	public HashValue(String hashvalue) {
		this.val = hashvalue;
	}

	/**
	 * Initialize the hash value with an integer number.
	 * 
	 * @param h hash value as an integer 
	 * @param length length of the hash value in bytes
	 */
	public HashValue(long h, int length) {
		StringBuilder s = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = (char)(h & MASK);
			s.append(c);
			h = h >>> 8;
		}
		val = s.toString();
		
	}
	
	/**
	 * @return hash value as a string
	 */
	@Override
	public String toString() {
		if (val.charAt(val.length() -1) == ' ') {
			return val.substring(0, val.length() - 1) + (char)0;
		} else {
			return val;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		return this.val.compareTo(((HashValue)arg0).toString());
	}
	
	public static HashValue maxValue(int length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			s += Character.MAX_VALUE; 
		}
		return new HashValue(s);
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object arg0) {
//		return (this.toString()).equals(((HashValue)arg0).toString());
//	}
	
	
	
}
