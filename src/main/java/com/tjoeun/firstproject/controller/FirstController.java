package com.tjoeun.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //이 클래스는 컨트롤러임을 나타낸다.
public class FirstController {
	
//	브라우저에 "/hi"라는 요청이 들어오면 ntmy() 메소드가 실행된다.
	@GetMapping("/hi")
//	컨트롤러에서 뷰페이지로 데이터를 전달하기 위해서 model을 사용한다. 
	public String ntmy(Model model) {
		model.addAttribute("username" , "이순신");
		return "greetings"; //viewpage 이름
	}
	
	@GetMapping("/bye")
	public String bye(Model model) {
		model.addAttribute("nickname" , "이순신");
		return "goodbye"; //viewpage 이름
	}
	
}
