package com.moaview.ep.util;

import com.moaview.ep.constans.RequestParamConst;
import com.moaview.ep.vo.DataEntity;
import com.moaview.ep.vo.PagingInfo;
import com.moaview.ep.vo.SearchParameter;

/**
 * 
 * @author ytkim
 * 
 * paging utils class
 *
 */
public final  class PagingUtils {
	private static final int COUNT_PER_PAGE = 10;
	private static final int UNIT_PAGE = 10;
	
	public static PagingInfo getPageObject(int totalCount, DataEntity param) {
		return getPageObject(totalCount, param.getInt(RequestParamConst.PAGE_NO, 1), param.getInt(RequestParamConst.COUNT_PER_PAGE, COUNT_PER_PAGE), param.getInt(RequestParamConst.UNIT_PAGE,UNIT_PAGE));
	}
	
	public static PagingInfo getPageObject(int totalCount, SearchParameter schParam) {
		return getPageObject(totalCount, getNumValue(schParam.getPageNo(), 1), getNumValue(schParam.getCountPerPage(), COUNT_PER_PAGE), getNumValue(schParam.getUnitPage(), UNIT_PAGE));
	}
	
	public static PagingInfo getPageObject(int totalCount, int currentPageNo) {
		return getPageObject(totalCount, currentPageNo, COUNT_PER_PAGE);
	}

	public static PagingInfo getPageObject(int totalCount, int currentPageNo, int countPerPage) {
		return getPageObject(totalCount, currentPageNo, countPerPage, UNIT_PAGE);
	}

	public static PagingInfo getPageObject(int totalCount, int currPage, int countPerPage, int unitPage) {

		int totalPage = getTotalPage(totalCount, countPerPage);

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
		
		PagingInfo pagingInfo = new PagingInfo();

		pagingInfo.setCurrPage(currPage < 1 ? 1: currPage);
		pagingInfo.setUnitPage(unitPage);
		pagingInfo.setPrePage(prePage);
		pagingInfo.setPrePage_is(prePage_is);
		pagingInfo.setNextPage(nextPage);
		pagingInfo.setNextPage_is(nextPage_is);
		pagingInfo.setCurrStartPage(currStartPage);
		pagingInfo.setCurrEndPage(currEndPage < 1 ? 1: currEndPage);
		pagingInfo.setTotalCount(totalCount);
		pagingInfo.setTotalPage(totalPage);
		pagingInfo.setCountPerPage(countPerPage);
		

		return pagingInfo;
	}
	
	public static int getFirstRow(PagingInfo page) {
		return (page.getCurrPage() - 1) * page.getCountPerPage() + 1;
	}
	
	public static int getEndRow(PagingInfo page) {
		return getFirstRow(page) + page.getCountPerPage() - 1;
	}

	private static int getTotalPage(int totalCount, int countPerPage) {
		if (totalCount % countPerPage == 0) {
			return totalCount / countPerPage;
		}
		return totalCount / countPerPage + 1;
	}
	
	private static int getNumValue(int chkVal, int defaultValue) {
		
		return chkVal > 0 ? chkVal : defaultValue;
	}

}