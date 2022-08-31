package com.moaview.ep.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.moaview.ep.vo.PagingInfo;

class PagingUtilsTest {

	@Test
	void test() {
		PagingInfo info =  PagingUtils.getPageObject(110, 8, 10);
		
		System.out.println(info);
		
		
		assertTrue("start end page check : ",info.getCurrStartPage()== 2 && info.getCurrEndPage()== 11);
	}

}
