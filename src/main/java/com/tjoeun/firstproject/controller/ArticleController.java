package com.tjoeun.firstproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tjoeun.firstproject.dto.ArticleForm;
import com.tjoeun.firstproject.entity.Article;
import com.tjoeun.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ArticleController {
	
//	JPA repository 인터페이스 객체를 선언하고 @Autowired 어노테이션으로 초기화한다.
	@Autowired // 스프링 부트가 생성해놓은 객체를 가져다가 자동으로 연결한다.
	private ArticleRepository articleRepository;
	
	@GetMapping("/articles/new")
	public String newArticles(Model model) {
		return "articles/new";
	}
	
	@PostMapping("/articles/create")
//	form에서 넘어오는 데이터는 커맨드 객체로 받는다.
	public String createArticles(ArticleForm form) {
//		System.out.println(form);
//		@slf4j 어노테이션 로그 레벨, 로그는 반드시 문자열로 사용한다.
		
		log.trace(form.toString());
//		DTO의 데이터를 Entity로 변환한다.
		Article article = form.toEntity();
//		repository에게 entity를 데이터베이스에 저장하게 된다. 
//		 - id가 자동으로 생성된다. 
		Article saved = articleRepository.save(article);
//		System.out.println(saved);
//		log.info(saved.toString());
		return "articles/new";
	}
	
//	브라우저에서 "articles/글의ID" 형태로 요청을 받아 처리한다.
//	{}는 변경되는 "/articles/1", "articles/2", "articles/3",  ...와 같이 변화되는 데이터를 받는다는 의미이다.
	@GetMapping("/articles/{id}")
//	{}를 통해서 받은 데이터를 저장할 변수
	public String show(@PathVariable Long id , Model model) {
		log.info("id=" + id);
//		articleRepository의 findById() 메소드로 id에 해당되는 데이터 1건을 테이블에서 가져온다.
//		 - findById() 메소드로 얻어온 데이터가 없을 경우 orElse() 메소드로 null을 리턴시킨다.
		Article articleEntity = articleRepository.findById(id).orElse(null);
		log.info("articleEntity = " + articleEntity);
//		테이블에서 얻어온 데이터를 뷰페이지로 전달하기 위해 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("article" , articleEntity);
		return "articles/show";
	}
	
//	테이블에 저장된 모든 글을 얻어오는 메소드
	@GetMapping("/articles")
	public String index(Model model) {
		List<Article> articleEntityList = articleRepository.findAll();
		System.out.println(articleEntityList);
		model.addAttribute("articleList" , articleEntityList);
		return "articles/index";
	}
	
	
}
