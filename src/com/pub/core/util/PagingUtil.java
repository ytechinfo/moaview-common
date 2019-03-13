package com.pub.core.util;

import java.util.HashMap;
import java.util.Map;

import com.pub.core.constans.RequestParamConst;
import com.pub.core.entity.DataEntity;

public final  class PagingUtil {
	private static final int countPerPage = 10;
	private static final int unitPage = 10;
	
	public static void main(String[] args) {
		System.out.println(getPageObject(380, 6,10 , 6));
	}

	public static Map getPageObject(int totalCount, DataEntity param) {
		return getPageObject(totalCount, param.getInt(RequestParamConst.PAGE_NO,1), param.getInt(RequestParamConst.COUNT_PER_PAGE,countPerPage), param.getInt(RequestParamConst.UNIT_PAGE,unitPage));
	}
	
	public static Map getPageObject(int totalCount, int currentPageNo) {
		return getPageObject(totalCount, currentPageNo, countPerPage);
	}

	public static Map getPageObject(int totalCount, int currentPageNo, int countPerPage) {
		return getPageObject(totalCount, currentPageNo, countPerPage, unitPage);
	}

	public static Map getPageObject(int totalCount, int currPage, int countPerPage, int unitPage) {
		int unitCount = 100;

		if (totalCount == 0) {
			countPerPage = unitCount;
		} else if (totalCount < countPerPage) {
			countPerPage = totalCount / unitCount * unitCount;
			if (totalCount % unitCount > 0) {
				countPerPage += unitCount;
			}
		}

		int totalPage = getMaxNum(totalCount, countPerPage);

		if (totalPage < currPage)
			currPage = totalPage;
		int currEndCount;
		if (currPage != 1) {
			currEndCount = currPage * countPerPage;
		} else {
			currEndCount = countPerPage;
		}

		if (currEndCount > totalCount)
			currEndCount = totalCount;
		int currStartPage;
		int currEndPage;
		
		if (totalPage <= unitPage) {
			currEndPage = totalPage;
			currStartPage = 1;
		} else {
			if(currPage < (unitPage /2)){
				currEndPage = (currPage - 1) / unitPage * unitPage + unitPage;
				currStartPage = currEndPage - unitPage + 1;
			}else{
				currEndPage = (currPage + unitPage /2);
				
				if(currEndPage > totalPage){
					currEndPage =totalPage;
				}
				currStartPage = currEndPage - unitPage + 1;
			}
		}

		if (currEndPage > totalPage)
			currEndPage = totalPage;
		int prePage;
		boolean prePage_is;
		if (currStartPage != 1) {
			prePage_is = true;
			prePage = currStartPage - 1;
		} else {
			prePage_is = false;
			prePage = 0;
		}
		int nextPage;
		boolean nextPage_is;
		if (currEndPage != totalPage) {
			nextPage_is = true;
			nextPage = currEndPage + 1;
		} else {
			nextPage_is = false;
			nextPage = 0;
		}

		Map tempJSON = new HashMap();
		tempJSON.put("currPage", (currPage < 1 ? 1: currPage));
		tempJSON.put("unitPage", unitPage);
		tempJSON.put("prePage", prePage);
		tempJSON.put("prePage_is", prePage_is);
		tempJSON.put("nextPage", nextPage);
		tempJSON.put("nextPage_is", nextPage_is);
		tempJSON.put("currStartPage", currStartPage);
		tempJSON.put("currEndPage", (currEndPage < 1 ? 1: currEndPage));
		tempJSON.put("totalCount", totalCount);
		tempJSON.put("totalPage", totalPage);

		return tempJSON;
	}

	private static int getMaxNum(int allPage, int list_num) {
		if (allPage % list_num == 0) {
			return allPage / list_num;
		}
		return allPage / list_num + 1;
	}

}