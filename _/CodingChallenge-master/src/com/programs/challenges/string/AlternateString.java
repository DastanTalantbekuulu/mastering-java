/**
 * These materials are confidential and proprietary to Intellect Design Arena Ltd. and no part of these
 * materials should be reproduced, published, transmitted or distributed in any form or by any means,
 * electronic, mechanical, photocopying, recording or otherwise, or stored in any information storage or
 * retrieval system of any nature nor should the materials be disclosed to third parties or used in any
 * other manner for which this is not authorized, without the prior express written authorization of
 * Intellect Design Arena Ltd.
 *
 * Copyright 2017 Intellect Design Arena Limited. All rights reserved.
 *
 * @author pranshu.shrivastava
 * @date Mar 2, 2020
 */
package com.programs.challenges.string;

public class AlternateString {
	
	private static void printAlternateChars(String a, String b) {
		
		if(a == null || b == null) {
			return;
		}
		
		int len = (a.length() > b.length()) ? b.length() : a.length();
		String result = "";
		int i;
		for(i = 0; i < len;i++) {
			result += String.valueOf(a.charAt(i)) + String.valueOf(b.charAt(i));
		}
		
		result += a.substring(i) + b.substring(i);
		System.out.println("Result is : "+result);
	}
	

	public static void main(String[] args) {
		String a = "acegi";
		String b = "bdfhjklm";
		printAlternateChars(a, b);
	}

}
