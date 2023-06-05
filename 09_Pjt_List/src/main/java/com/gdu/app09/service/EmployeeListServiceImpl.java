package com.gdu.app09.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.app09.domain.EmpDTO;
import com.gdu.app09.mapper.EmployeeListMapper;
import com.gdu.app09.util.PageUtill;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor	// final field를 채울 때 사용하는 애너테이션	
@Service
public class EmployeeListServiceImpl implements EmployeeListService {
	
	// field
	private final EmployeeListMapper employeeListMapper;
	private final PageUtill pageUtill;
	
	@Override
	public void getEmployeeListUsingPagination(HttpServletRequest request, Model model) {

		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// 세션에 있는 recordPerPage를 가져온다. 세션에 없는 경우(첫 목록 - 1페이지 뿌릴 때) recordPerPage를=10으로 처리한다.
		HttpSession session = request.getSession();
		// 세션에 올리는 타입은 Object이다. (int든 String이든 올릴 수 있다.)
		Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int)(opt2.orElse(10)); // Object에서 꺼낸걸 int타입으로 캐스팅해준다.
		
		// 파라미터 order가 전달되지 않은 경우 order=ASC로 처리한다.
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
		String order = opt3.orElse("ASC");
		
		// 파라미터 column이 전달되지 않는 경우 column=EMPLOYEE_ID로 처리한다.
		Optional<String> opt4 = Optional.ofNullable(request.getParameter("column"));
		String column = opt4.orElse("EMPLOYEE_ID");
		
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
												   // LIMIT #{begin}, #{recordPerPage}
		map.put("begin", pageUtill.getBegin());    // begin은 0부터 시작한다. (PageUtil.java 참고)
		map.put("recordPerPage", recordPerPage);   // end 대신 recordPerPage를 전달한다.
		map.put("order", order);
		map.put("column", column);
		
		// begin ~ recordPerPage 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward) 정보 저장하기
		model.addAttribute("employees", employees);
		// order의 값을 알고 있는 서비스에서 전달을 해줘야한다.
		model.addAttribute("pagination", pageUtill.getPagination(request.getContextPath() + "/employees/pagination.do?column=" +  column + "&order=" + order));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		
		switch(order) {
		case "ASC" : model.addAttribute("order", "DESC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		case "DESC" : model.addAttribute("order", "ASC"); break;	// 현재 ASC 정렬이므로 다음 정렬은 DESC이라고 Jsp에 알려준다.
		}
		
		model.addAttribute("page", page);
	
		
	}
	
	@Override
	public Map<String, Object> getEmployeeListUsingScroll(HttpServletRequest request) {
		// 실무에선 사실 ArrayList를 반환한다고 해도 반환타입을 List로 두진 않는다.
		// 보통 List를 Map에 담아 반환한다. 왜냐? Map에 담아야 여러가지를 계속 담을 수 있으므로.
		
		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// ecordPerPage = 9로 처리한다.
		int recordPerPage = 9; 
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("begin", pageUtill.getBegin());
		map.put("recordPerPage", recordPerPage);
		
		// begin ~ recordPerPage 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingScroll(map);
		
		// model에 저장해서 forward하는 방식이 아니다. Map에 값을 담아 전달해줘야한다.
		// scroll.jsp로 응답할 데이터
		Map<String, Object> resultMap = new HashMap <String, Object>(); 
		resultMap.put("employees", employees);
		resultMap.put("totalPage", pageUtill.getTotalPage());
		
		// Map을 반환하면 만약 추가로 담아서 보내줘야할 경우 여기에 계속 put메소드 사용해서 집어넣으면 된다.
		
		return resultMap;
		
		/*
		 	resultMap이 json으로 변활될 때의 모습 (이게 곧 resData이다.)
		 	
		 	{
		 		"employees":[
		 			{
	 					"employeeId": 100,
	 					"firstName": "Steven",
	 					"lastName": "King",
		 				...,
		 				"deptDTO": {
		 					"departmentId": 90,
		 					"departmentName": "Executive",
		 					...
		 				}
		 			},
		 			{
	 					"employeeId": 101,
	 					"firstName": "Neena",
	 					"lastName": "Kochhar",
		 				...,
		 				"deptDTO": {
		 					"departmentId": 90,
		 					"departmentName": "Executive",
		 					...
		 				}
		 			},
		 			...
		 		],
		 		"totalPage": 12
		 	}	
		*/
		
	}
	
	@Override
	public void getEmployeeListUsingSearch(HttpServletRequest request, Model model) {

		// 파라미터 column이 전달되지 않는 경우 column=""(빈문자열)로 처리한다.
		// ${contextPath}/employees/search.do 이런식으로만 전달할경우 column값이 없으니 null값 처리를 해줘야한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("column"));
		String column = opt1.orElse(""); 
		
		// 파라미터 query가 전달되지 않는 경우 query=""(빈문자열)로 처리한다.
		// ${contextPath}/employees/search.do 이런식으로만 전달할경우 query값이 없으니 null값 처리를 해줘야한다.
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
		String query = opt2.orElse(""); 
		
		// DB로 보낼 Map 만들기 (column + query)
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("column", column);
		map.put("query", query);
		
		// 파라미터 page가 전달되지 않은 경우 page=1로 처리한다.
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt3.orElse("1"));
		
		// column과 query를 이용해 검색된 레코드 개수를 구한다.
		int totalRecord = employeeListMapper.getEmployeeSearchCount(map);
		
		// recordPerPage=10으로 처리한다.
		int recordPerPage = 10;
		
		// PageUtil(Pagination에 필요한 모든 정보) 계산하기
		pageUtill.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map에 begin, end 추가하기
		map.put("begin", pageUtill.getBegin());
		map.put("recordPerPage", recordPerPage);
		
		// begin ~ recordPerPage 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingSearch(map);
		
		// search.jsp로 전달할(forward) 정보 저장하기
		model.addAttribute("employees", employees);
		
		// column과 query 파라미터를 넘겨줘야 페이지가 바뀌어도 검색한 문자가 유지된다.
		model.addAttribute("pagination", pageUtill.getPagination(request.getContextPath() + "/employees/search.do?column=" + column + "&query=" + query));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		
	}
	
	@Override
	public Map<String, Object> getAutoComplete(HttpServletRequest request) {
		
		// 파라미터 column이 전달되지 않는 경우 column=""(빈문자열)로 처리한다.
		// ${contextPath}/employees/search.do 이런식으로만 전달할경우 column값이 없으니 null값 처리를 해줘야한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("column"));
		String column = opt1.orElse(""); 
		
		// 파라미터 query가 전달되지 않는 경우 query=""(빈문자열)로 처리한다.
		// ${contextPath}/employees/search.do 이런식으로만 전달할경우 query값이 없으니 null값 처리를 해줘야한다.
		Optional<String> opt2 = Optional.ofNullable(request.getParameter("query"));
		String query = opt2.orElse(""); 
		
		// DB로 보낼 Map 만들기 (column + query)
		Map<String, Object> map = new HashMap <String, Object>();
		map.put("column", column);
		map.put("query", query);
		
		// 검색 결과 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getAutoComplete(map);
		
		// search.jsp로 응답할 데이터
		Map<String, Object> resultMap = new HashMap <String, Object>(); 
		resultMap.put("employees", employees);
		
		// 응답
		return resultMap;
		
	}

}
