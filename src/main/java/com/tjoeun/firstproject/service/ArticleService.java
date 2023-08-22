package com.tjoeun.firstproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.firstproject.dto.ArticleForm;
import com.tjoeun.firstproject.entity.Article;
import com.tjoeun.firstproject.repository.ArticleRepository;

@Service //Service 어노테이션을 붙여준 클래스는 springBoot가 서비스로 인식하여 객체를 자동으로 생성해 등록한다.
public class ArticleService {
	
//	ArticleApiController 클래스에서 실행했던 데이터베이스 작업으로 서비스 클래스에서 실행한다.
//	 - JPA를 사용한 데이터베이스 작업을 하기 위해서 생성한 JpaRepository 인터페이스를 상속받아 만든 repository 객체를 선언하고 초기화한다.
	@Autowired
	private ArticleRepository articleRepository;

	public List<Article> index() {
		System.out.println("===== ArticleService의 index() 메소드 실행");
		return articleRepository.findAll();
	}

	public Article show(Long id) {
		System.out.println("===== ArticleService의 show() 메소드 실행");
		return articleRepository.findById(id).orElse(null);
	}

	public Article create(ArticleForm dto) {
		System.out.println("===== ArticleService의 create() 메소드 실행");
//		id는 데이터베이스가 자동으로 생성하므로 id가 넘어오는 데이터는 저장하지 않는다.
		Article article = dto.toEntity();
		if(article.getId() != null) {
			return null;
		}
		return articleRepository.save(article);
	}

	public Article update(Long id, ArticleForm dto) {
		System.out.println("===== ArticleService의 update() 메소드 실행");
//		수정할 entity를 조회한다.
		Article article = dto.toEntity();
		Article target = articleRepository.findById(id).orElse(null);
//		 - 잘못된 요청(수정 대상이 없거나 id가 다른 경우)을 처리한다.
//		 - id만 넘어오고 title, content가 null이 넘어오면 null을 리턴하는 코드를 추가한다.
		if(target == null || id != target.getId() || article.getTitle() == null && article.getContent() == null) {
			return null;
		}
		
//		조회된 수정할 entity를 RequestBody를 통해 넘어온 데이터가 있는 경우만 수정한다.
		target.patch(article);
		return articleRepository.save(target);
	}

	public Article delete(Long id) {
		System.out.println("===== ArticleService의 delete() 메소드 실행");
//		삭제할 entity를 조회한다.
		Article target = articleRepository.findById(id).orElse(null);
//		잘못된 요청(삭제 대상이 없는 경우)을 처리한다.
		if(target == null) {
			return null;
		}
		articleRepository.delete(target);
		return target;
	}

//	트랜잭션 => 실패 => 롤백
	@Transactional //해당 어노테이션은 메소드를 트랜잭션을 묶는다.
	public List<Article> transaction(List<ArticleForm> dtos) {
		System.out.println("===== ArticleService의 transaction() 메소드 실행");
//		dto 묶음을 entity 묶음으로 변환한다.
		/*
		List<Article> articleList = new ArrayList<Article>();
		for(int i = 0 ; i<dtos.size() ; i++) {
			Article entity = dtos.get(i).toEntity();
			articleList.add(entity);
		}
		*/
//		dto 객체가 저장된 List를 entity로 변환해 Stream으로 변환한 후 다시 List로 변환한다.
		List<Article> articleList = dtos.stream()
				.map(dto -> dto.toEntity())
				.collect(Collectors.toList());
		System.out.println("articleList : " + articleList);
		
//		entity 묶음을 데이터베이스에 저장한다.
		/*
		for(int i = 0 ; i<articleList.size() ; i++) {
			articleRepository.save(articleList.get(i));
		}
		*/
		articleList.stream().forEach(article -> articleRepository.save(article));
		 
//		강제 예외 발생
		articleRepository.findById(-1L).orElseThrow(
			() -> new IllegalArgumentException("트랜잭션 실패")
		);
		
		return articleList;
	}

	
}
