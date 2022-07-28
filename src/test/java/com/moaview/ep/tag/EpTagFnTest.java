package com.moaview.ep.tag;

import org.junit.jupiter.api.Test;

class EpTagFnTest {

	@Test
	void test() {
		System.out.println(EpTagFn.calcMonth("yyyy-MM-dd",-1));
		System.out.println(EpTagFn.calcDay("yyyy-MM-dd",7));
	}

}
