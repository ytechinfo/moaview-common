package com.moaview.ep.vo;

public class PagingInfo {
	private int currPage; 
	private int unitPage; 
	private int prePage; 
	private boolean prePage_is; 
	private int nextPage; 
	private boolean nextPage_is; 
	private int currStartPage; 
	private int currEndPage; 
	private int totalCount; 
	private int totalPage;
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getUnitPage() {
		return unitPage;
	}
	public void setUnitPage(int unitPage) {
		this.unitPage = unitPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public boolean isPrePage_is() {
		return prePage_is;
	}
	public void setPrePage_is(boolean prePage_is) {
		this.prePage_is = prePage_is;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public boolean isNextPage_is() {
		return nextPage_is;
	}
	public void setNextPage_is(boolean nextPage_is) {
		this.nextPage_is = nextPage_is;
	}
	public int getCurrStartPage() {
		return currStartPage;
	}
	public void setCurrStartPage(int currStartPage) {
		this.currStartPage = currStartPage;
	}
	public int getCurrEndPage() {
		return currEndPage;
	}
	public void setCurrEndPage(int currEndPage) {
		this.currEndPage = currEndPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	} 
	
	@Override
	public String toString() {
		
		return new StringBuilder()
				.append("currPage : ").append(currPage)
				.append(", unitPage : ").append(unitPage)
				.append(", prePage : ").append(prePage)
				.append(", prePage_is : ").append(prePage_is)
				.append(", nextPage : ").append(nextPage)
				.append(", nextPage_is : ").append(nextPage_is)
				.append(", currStartPage : ").append(currStartPage)
				.append(", currEndPage : ").append(currEndPage)
				.append(", totalCount : ").append(totalCount)
				.append(", totalPage : ").append(totalPage)
				.toString();
	}
}
