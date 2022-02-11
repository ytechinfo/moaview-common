package com.moaview.ep.constans;

/**
 * 
 * @author ytkim
 *
 */
public interface RequestParamConst {
	
	/** 검색 파라미터 관련. */
	// 검색어.
	final String SEARCH_VAL = "searchVal";
	
	// 검색 정려 카테고리
	final String SEARCH_SORT_CATEGORY ="search_sort_category";
	
	// 검색 카테고리
	final String SEARCH_CATEGORY ="search_category";
	
	// 검색 쿼리 조건 값
	final String SEARCH_CONDITION ="condition";
	
	// 내림차순 오름 차순  정렬
	final String SEARCH_SORT_ASC_FLAG  ="sortAscFlag";
	
	//현재 페이지 지정 ( 기본 : 1)
	final String PAGE_NO = "pageNo";
	
	// 한 페이지에 출력할 갯수 지정
	final String COUNT_PER_PAGE = "countPerPage";
	
	// 한 페이지에 출력할 갯수 지정
	final String UNIT_PAGE = "unitPage";
	
	final String START_DT = "start_dt";
	
	final String PAGING_START_ROW = "_startRow";
	
	final String PAGING_END_ROW = "_endRow";
	
	final String REQ_LOCALE = "_locale";
	
	
}
