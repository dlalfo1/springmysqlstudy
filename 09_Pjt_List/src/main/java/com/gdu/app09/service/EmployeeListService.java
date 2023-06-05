package com.gdu.app09.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface EmployeeListService {

	public void getEmployeeListUsingPagination(HttpServletRequest rquest, Model model);
	public Map<String, Object> getEmployeeListUsingScroll(HttpServletRequest request);
	public void getEmployeeListUsingSearch(HttpServletRequest rquest, Model model);
	public Map<String, Object> getAutoComplete(HttpServletRequest request); // 스크롤기능처럼 페이지변화 없이 처리하기 때문에 Map으로 반환한다.
																			// ResponseEntity를 쓸게 아니라면 Map으로 반환하는게 일반적이다.
																			// ResponseEntity를 쓸 거면 그 안에 Map을 넣어도 된다.
	
	
}
