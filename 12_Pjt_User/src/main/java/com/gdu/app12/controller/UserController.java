package com.gdu.app12.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app12.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
  
  // field
  @Autowired
  private UserService userService;
  
  @GetMapping("/agree.form")
  public String agreeJsp() {
    return "user/agree";
  }

  @GetMapping("/join.form")
                               // 파라미터 location, event는 필수로 넘어오는 값이 아니기 때문에 required=false 속성을 넣어줘야 한다.
  public String joinJsp(@RequestParam(value="location", required=false) String location // 파라미터 location이 전달되지 않으면 빈 문자열("")이 String location에 저장된다.
                      , @RequestParam(value="event", required=false) String event
                      , Model model) {
    model.addAttribute("location", location);
    model.addAttribute("event", event);
    
    return "/user/join";
  }
  
  @ResponseBody
  @GetMapping(value="/verifyId.do", produces="application/json") 
  public Map<String, Object> verifyId(@RequestParam("id") String id){
    
    return userService.verifyId(id);

  }
  
  @ResponseBody
  @GetMapping(value="/verifyEmail.do", produces="application/json")
  public Map<String, Object> verifyEmail(@RequestParam("email") String email) {
    
    return userService.verifyEmail(email);
  }
  
  
  @ResponseBody
  @GetMapping(value="/sendAuthCode.do", produces="application/json")
  public Map<String, Object> sendAuthCode(@RequestParam("email") String email) {
    return userService.sendAuthCode(email);
  }
  
  @PostMapping("/join.do")
  public void join(HttpServletRequest request, HttpServletResponse response) {
    userService.join(request, response);
  }
  
  @GetMapping("/login.form")
  public String loginForm(@RequestHeader("referer") String url, Model model) {
    
    // 요청 헤더 referer : 로그인 화면으로 이동하기 직전의 주소를 저장하는 헤더 값
    model.addAttribute("url", url);
    
    return "user/login";
    
  }
  
  @PostMapping("/login.do")
  public void login(HttpServletRequest request, HttpServletResponse response) {
    userService.login(request, response);
  }
  
  @GetMapping("/logout.do")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    userService.logout(request, response);
    return "redirect:/index.do"; // 리다이렉트 이동해야 매핑값으로 이동이 가능하다. 로그아웃시 메인화면 보여주기
  }
  
  @GetMapping("/leave.do")
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    userService.leave(request, response);
  }

  // UserController에 있는 모든 메소드 : 조인포인트
  // 선택된 메소드 : 포인트컷
  // 실제 수행될 작업 : 어드바이스 (로그인이 되어있는지 확인하는 코드)
  
  @GetMapping("/wakeup.form")
  public String wakeup() {
    return "user/wakeup";
  }
  
  @GetMapping("/restore.do")
  public void restore() {
    // 복원할 회원의 아이디를 sysout으로 출력해 보시오.
    
    
    
  }
}
