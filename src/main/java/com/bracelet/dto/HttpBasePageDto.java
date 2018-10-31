package com.bracelet.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HttpBasePageDto extends HttpBaseDto {

    // 总条数
    protected int count;
    // 总页数
    protected int totalPages;
    // 每页数量
    protected int numsPerPage;
    // 当前页
    protected int currentPage;

    public HttpBasePageDto() {
        super();
    }

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the numsPerPage
	 */
	public int getNumsPerPage() {
		return numsPerPage;
	}

	/**
	 * @param numsPerPage the numsPerPage to set
	 */
	public void setNumsPerPage(int numsPerPage) {
		this.numsPerPage = numsPerPage;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
