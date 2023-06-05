package com.gdu.app09.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app09.service.EmployeeListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final field를 채울 때 사용하는 애너테이션
@Controller
public class EmployeeController {
	
	private final EmployeeListService employeeListService; 
	// 필드에 final 키워드를 추가한다. 수정하지 않기 위해서.
	
	@GetMapping("/employees/pagination.do")
	public String pagination(HttpServletRequest request, Model model) {
		employeeListService.getEmployeeListUsingPagination(request, model);
		return "employees/pagination";
		
	}
	/*	
	  - 기존에 사용하던 코드 -
		@GetMapping("/employees/change/record.do")
		public String changeRecord(HttpServletRequest request) { 
			
			
			Optional<String> opt = Optional.ofNullable(request.getParameter("recordPerPage"));
			int recordPerPage = Integer.parseInt(opt.orElse("10"));
			
			HttpSession session = request.getSession();
			session.setAttribute("recordPerPage", recordPerPage);
			return "";
		}
	*/
	
	@GetMapping("/employees/change/record.do")
	
	public String changeRecord(HttpSession session 		  // Session을 사용하고 싶다면 HttpServletRequest에서 가져오지 않아도 바로 선언해서 사용하면 된다.
							, HttpServletRequest request  // 여기에서 HttpServletRequest을 사용하고 싶으면 매개변수에 또 선언해주면 되는거임~!
							, @RequestParam(value="recordPerPage", required = false, defaultValue="10") int recordPerPage) { 
												   // recordPerPage 파라미터를 받기 위해 @RequestParam 애너테이션을 사용한다.
												  
		session.setAttribute("recordPerPage", recordPerPage);
		// return "redirect:" + request.getHeader("referer"); // 현재 주소(/employees/change/record.do)의 이전 주소(Referer)로 이동하시오.
														   // /change/record.do 하기전에 니가 보고 있던 페이지로 돌아가라.
		return "redirect:/employees/pagination.do?column=EMPLOYEE_ID&order=ASC&page=1";
	}
	@GetMapping("/employees/scroll.page")
	public String scrollPage() {
		return "employees/scroll";
	}
	
	@GetMapping(value="/employees/scroll.do", produces="application/json")
	@ResponseBody	// 응답해주는게 jsp이름이 아니라 ajax로 반환하는 데이터(응답 본문)이란걸 알려주기 위한 애너테이션
	public Map<String, Object> scroll(HttpServletRequest request){ 
		return employeeListService.getEmployeeListUsingScroll(request);
	}
	
	
	@GetMapping("/employees/search.do")
	public String searchPage(HttpServletRequest request, Model model) {
		employeeListService.getEmployeeListUsingSearch(request, model);
		
		return "employees/search";
	}
	
	@GetMapping(value="/employees/autoComplete.do", produces="application/json")
	@ResponseBody
	public Map<String, Object> autoComplete(HttpServletRequest request){ 
		return employeeListService.getAutoComplete(request);
	 
	}
	
}