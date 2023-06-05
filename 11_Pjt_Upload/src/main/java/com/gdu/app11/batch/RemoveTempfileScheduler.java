package com.gdu.app11.batch;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app11.util.MyFileUtil;

@EnableScheduling
@Component
public class RemoveTempfileScheduler {
	
	// 임시 폴더(temp)의 모든 파일을 매일 새벽 3시에 지우는 스케줄러
	
	@Autowired
	private MyFileUtil myFileUtill;
	
	// @Scheduled(cron="0 0 3 1/1 * ?") // www.cronmaker.com에서 생성한 매일 새벽 3시 정보에서 
	@Scheduled(cron="0 13 12 1/1 * ?")
	public void execute() { // 메소드명은 아무 의미 없다.
		
		// 임시 폴더의 File 객체 
		File dir = new File(myFileUtill.getTempPath());
		
		// 임시 폴더가 존재하면 파일 내의 모든 파일을 가져와서 하나씩 삭제
		if(dir.exists()) {
			for(File file : dir.listFiles()) { //listFiles()는 []배열을 반환한다. - 해당 디렉터리의 모든파일을 반환.
				file.delete();
			}
		}
		
	}
}
