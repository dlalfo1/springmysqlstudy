package com.gdu.app12.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app12.service.UserService;
  
@Component  
@EnableScheduling
public class SleepUserHandleScheduler {

  @Autowired
  private UserService userService; // 일반유저 테이블의 데이터를 휴먼유저 테이블에 넣어야하기 때문에 서비스가 필요하다.(매퍼사용)
     
  // 매일 새벽 1시
  @Scheduled(cron="0 0 1 1/1 * ?")
  public void execute() {
    userService.sleepUserHandle();
  }
  


}
