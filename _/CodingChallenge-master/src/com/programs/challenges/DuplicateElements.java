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
 * @date Feb 11, 2020
 */
package com.programs.challenges;

public class DuplicateElements {

	public static void main(String[] args) {
		int [] arr = {1,2,3,5,4,2,5};
		findDuplicateElement(arr,arr.length);
	}
	
	static void findDuplicateElement(int [] arr, int length) {
		int [] count = new int[length];
		for(int i = 0; i < length; i++) {
			if(count[arr[i]] == 1) {
				System.out.println(arr[i]+"");
				break;
			}else {
				count[arr[i]]++;
			}
		}
	}

}
