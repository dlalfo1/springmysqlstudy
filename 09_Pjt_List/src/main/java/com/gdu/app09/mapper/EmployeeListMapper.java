package com.gdu.app09.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app09.domain.EmpDTO;

@Mapper
public interface EmployeeListMapper {
	public int getEmployeeCount(); // 전체 개수 구하는용도라 전달할 매개변수 X
	public List<EmpDTO> getEmployeeListUsingPagination(Map<String, Object> map);
	public List<EmpDTO> getEmployeeListUsingScroll(Map<String, Object> map);
	public List<EmpDTO> getEmployeeListUsingSearch(Map<String, Object> map);
	public int getEmployeeSearchCount(Map<String, Object> map);
	public List<EmpDTO> getAutoComplete(Map<String, Object> map);
	
	

}
