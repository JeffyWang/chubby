package com.jeffy.bo;

import java.util.List;

public class PageResult {
	private int totalRows; //总行数
	private int pageSize; //每页显示的行数
	private int currentPage; //当前页号
	private int totalPages; //总页数
	private int startRow; //当前页在数据库中的起始行
	private List eventList;
	
	
	public PageResult(int totalRows,int pageSize) {
		// TODO Auto-generated constructor stub
		 this.totalRows = totalRows;
		 this.pageSize = pageSize;
		 this.totalPages = (int)Math.ceil(totalRows/pageSize);
	}
	 
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	 public List getEventList() {
			return eventList;
		}

	public void setEventList(List eventList) {
		this.eventList = eventList;
	}

}
