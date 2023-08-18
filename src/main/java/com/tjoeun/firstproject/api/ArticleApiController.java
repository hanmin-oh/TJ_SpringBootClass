package com.tjoeun.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.firstproject.dto.ArticleForm;
import com.tjoeun.firstproject.entity.Article;
import com.tjoeun.firstproject.repository.ArticleRepository;
import com.tjoeun.firstproject.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ArticleApiController {
	
//	ArticleRepository를 service 계층으로 넘겨서 실행한다.
//	@Autowired
//	private ArticleRepository articleRepository;
	@Autowired // ArticleService 클래스의 bean을 자동으로 주입받는다.
	private ArticleService articleService;
	
//	모든 글 목록 얻어오기
	@GetMapping("/api/articles")
	public List<Article> index() {
//		return articleRepository.findAll();
		return articleService.index();
	}
	
	@GetMapping("/api/articles/{id}")
	public Article show(@PathVariable Long id) {
//		return articleRepository.findById(id).orElse(null);
		return articleService.show(id);
	}
	
//	글 쓰기
	@PostMapping("/api/articles")
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
//		form에서 데이터를 받아올 때는 커맨드 객체를 받으면 되지만 커맨드 객체에 body에 있는걸 담아야 한다.
		log.info("ArticleApiController의 create메소드");
//		log.info("dto: " + dto);
		Article saved = articleService.create(dto);
//		log.info("{}" , HttpStatus.CREATED);
//		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		return saved != null ?
				ResponseEntity.status(HttpStatus.CREATED).body(saved) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).body(saved);
					
	}
	
//	글 수정
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id , @RequestBody ArticleForm dto) {
		log.info("ArticleApiController의 update메소드");
//		log.info("===== id: {} , dto: {} " , id, dto.toString());
////		수정할 entity를 조회한다.
//		Article article = dto.toEntity();
//		Article target = articleRepository.findById(id).orElse(null);
//		log.info("===== id: {} , target: {} " , id, target.toString());
////		잘못된 요청(수정 대상이 없거나 id가 다른 경우)을 처리한다.
//		if(target == null || id != target.getId()) {
//			log.info(" ======= 잘못된 요청!! id: {} , target: {}" , id , target.toString());
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//		
////		글 수정 후 정상 응답을 보낸다.
////		 - RequestBody를 통해 ArticleForm으로 넘어오는 수정할 데이터의 일부가 넘어오지 않은 데이터는 null로 수정되기 때문에 이 현상을 방지하기 위해서
////		 조회된 수정할 entity를 RequestBody를 통해 넘어온 데이터가 있는 경우만 수정한다.
//		target.patch(article);
//		Article updated = articleRepository.save(target);
//		log.info("===== update : {}" , updated);
		
		Article updated = articleService.update(id, dto);
////		Httpstatus.OK : 200 OK , 응답성공
//		log.info("응답 성공");
//		return ResponseEntity.status(HttpStatus.OK).body(updated);
		return updated != null ?
				ResponseEntity.status(HttpStatus.CREATED).body(updated) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	} 
	
//	글 삭제
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id) {
		log.info("ArticleApiController의 delete메소드");
		Article deleted = articleService.delete(id);
		return deleted != null ?
				ResponseEntity.status(HttpStatus.CREATED).build() :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
//	트랜잭션 => 실패 => 롤백
	@PostMapping("/api/transaction-test")
	public ResponseEntity<List<Article>> transaction(@RequestBody List<ArticleForm> dtos) {
		log.info("===== ArticleApiController의 transaction메소드");
//		log.info("===== dto: {}" , dto);
		List<Article> createList = articleService.transaction(dtos);
		return createList != null ? 
				ResponseEntity.status(HttpStatus.OK).body(createList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
