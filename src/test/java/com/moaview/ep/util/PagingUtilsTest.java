package com.moaview.ep.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.moaview.ep.vo.PagingInfo;

class PagingUtilsTest {

	@Test
	void test() {
		PagingInfo info =  PagingUtils.getPageObject(101, 11, 102, 5);
		
		System.out.println(info);
		assertTrue("start end page check : ",info.getCurrStartPage()== 7 && info.getCurrEndPage()== 16);
	}

}
