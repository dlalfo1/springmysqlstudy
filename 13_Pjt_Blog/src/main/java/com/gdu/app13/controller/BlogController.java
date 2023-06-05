package com.gdu.app13.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.service.BlogService;

@RequestMapping("/blog")
@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
	blogService.loadBlogList(request, model);
	  return "blog/list";
  }
	
  @GetMapping("/write.form")
  public String write() {
	  return "blog/write";
  }
  
  @PostMapping("/add.do")
  public void add(HttpServletRequest request, HttpServletResponse response) {
    blogService.addBlog(request, response);
  }
  
  @ResponseBody
  @PostMapping(value="/imageUpload.do", produces="application/json")
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest){
	  
  // MultipartHttpServletRequest : 요청에 파일첨부가 들어가있을 때 사용한다.
  // MVC : <input type="file"> 
  // ajax : new FormData()     
  // 요청이 MVC든 ajax이든 이 요청을 받는 건 MultipartHttpServletRequest이다.
  // 또한 이 작업을 하려면 FileConfig가 필요하다. - 나중에 부트로 넘어가면 application.properties 파일에 포함시킴
	 return blogService.imageUpload(multipartRequest);
	  
  }
  
  @GetMapping("/increaseHit.do") // 리다이렉트 이동은 주소에 나타나지 않음. 최종적으로는 detail.do로 가는거임..
  public String increaseHit(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo) {
	  // 조회수 증가 서비스
	  int increaseResult =  blogService.increaseHit(blogNo);
	  if(increaseResult == 1) {
		  return "redirect:/blog/detail.do?blogNo=" + blogNo; // 조회수가 증가하지 않으면 상세보기로 넘어갈 필요가 없으니 조회수가 늘어났을 때만 상세보기로 이동하는걸로.
	  } else {
		  return "redirect:/blog/list.do";
	  }
  }
  
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo
                     , Model model) {
    blogService.loadBlog(blogNo, model);
    return "blog/detail";
  }
  
  
}
