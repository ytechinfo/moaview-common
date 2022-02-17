package com.moaview.ep.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.moaview.ep.vo.PagingInfo;

class PagingUtilsTest {

	@Test
	void test() {
		PagingInfo info =  PagingUtils.getPageObject(175, 11);
		assertTrue("start end page check : ",info.getCurrStartPage()== 7 && info.getCurrEndPage()== 16);
	}

}
