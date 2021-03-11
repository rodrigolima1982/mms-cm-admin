package com.mms.dto;

import java.util.List;

public class Pagination<T> {
	private long totalPages;
	private long pageNumber;
	private long totalElements;
	private List<T> elements;

	public Pagination() {
	}

	public Pagination(long totalPages, long totalElements, long pageNumber, List<T> elements) {
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;

		this.elements = elements;
	}

	public void setTotalPages(long l) {
		this.totalPages = l;
	}

	public long getTotalPages() {
		return this.totalPages;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageNumber() {
		return this.pageNumber;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}