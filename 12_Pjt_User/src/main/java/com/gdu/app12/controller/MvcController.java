package com.gdu.app12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MvcController {
	
	@GetMapping(value={"/", "/index.do"}) // 매핑값 배열처리해서 여러개 처리
	public String welcom() {
		return "index";
	}


   }
