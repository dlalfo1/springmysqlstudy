package com.gdu.app12.intercept;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import com.gdu.app12.domain.UserDTO;
import com.gdu.app12.mapper.UserMapper;

@Component
public class Autologininterceptor implements HandlerInterceptor {
  
  @Autowired
  private UserMapper userMapper; 
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    HttpSession session = request.getSession();
    
    if(session != null && session.getAttribute("loginId") == null) { // 세션은 존재하다 세션의 loginId값이 없고
      
      Cookie cookie = WebUtils.getCookie(request, "autoLoginId");
      if(cookie != null) {
        
        String autologinId = cookie.getValue(); // autoLoginId 쿠키값을 가져온다.
        UserDTO loginUserDTO =  userMapper.selectAutologin(autologinId);
        if(loginUserDTO != null) {
          session.setAttribute(autologinId, loginUserDTO.getId());
        }
       
      }
    }
    
    return true;  // 인터셉터를 동작 시킨 뒤 컨트롤러를 계속 동작시킨다.
  }
  

}
