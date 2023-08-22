package com.tjoeun.firstproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.firstproject.dto.ArticleForm;
import com.tjoeun.firstproject.entity.Article;

@SpringBootTest
class ArticleServiceTest {

	@Autowired
	ArticleService articleService;

	@Test
	void testIndex() {
		// 예상
		Article article1 = new Article(1L, "이순신", "명량해전");
		Article article2 = new Article(2L, "강감찬", "귀주대첩");
		Article article3 = new Article(3L, "을지문덕", "살수대첩");
//		Article article4 = new Article(4L, "홍범도", "독립군만세");
		List<Article> expected = new ArrayList<Article>(Arrays.asList(article1, article2, article3));
		// 실제
		List<Article> articles = articleService.index();
		// 비교
		assertEquals(expected.toString(), articles.toString());

	}

	@Test
	void testShow_성공_존재하는_id() {
		// 예상
		Long id = 1L;
		Article expected = new Article(id, "이순신", "명량해전");
		// 실제
		Article article = articleService.show(id);
		// 비교
		assertEquals(expected.toString(), article.toString());
	}

	@Test
	void testShow_실패_존재하지_않는_id() {
		// 예상
		Long id = -1L;
		Article expected = null;
		// 실제
		Article article = articleService.show(id);
		// 비교
		assertEquals(expected, article);
	}

//	테이블이 변경되는 테스트를 실행하는 경우 이전 테스트의 영향을 받아서 하나씩 테스트 할 떄는 정상적으로 실행되던 테스트가 
//	오류가 발생할 수 있기 때문에 테스트 결과가 테이블을 변경시키는 테스트 @Transactional 어노테이션을 붙인다.
	@Test
	@Transactional
//	트랜잭션 어노테이션을 추가하면 테스트 종료 후 변경된 데이터를 롤백(처음으로 되돌림) 처리를 한다.
	void testCreate_성공_title과_content만_있는_dto_입력() {
		// 예상
		String title = "홍범도";
		String content = "독립군만세";
		ArticleForm dto = new ArticleForm(null, title, content);
		Article expected = new Article(4L, title, content);
		// 실제
		Article article = articleService.create(dto);
		// 비교
		assertEquals(expected.toString(), article.toString());
	}

	@Test
	@Transactional
	void testCreate_실패_id가_포함된_dto_입력() {
		// 예상
		String title = "홍범도";
		String content = "독립군만세";
		ArticleForm dto = new ArticleForm(4L, title, content);
		Article expected = null;
		// 실제
		Article article = articleService.create(dto);
		// 비교
		assertEquals(expected, article);
	}

	@Test
	@Transactional
	void testUpdate_성공_존재하는_id와_title_content가_있는_dto_입력() {
		// 예상
		Long id = 3L;
		String title = "홍범도";
		String content = "독립군만세";
		ArticleForm dto = new ArticleForm(id, title, content);
		Article expected = new Article(3L, "홍범도", "독립군만세");
		// 실제
		Article article = articleService.update(id, dto);
		// 비교
		assertEquals(expected.toString(), article.toString());

	}

	@Test
	@Transactional
	void testUpdate_성공_존재하는_id와_title만_있는_dto_입력() {
		// 예상
		Long id = 3L;
		String title = "고구려전쟁";
		ArticleForm dto = new ArticleForm(id, title, null);
		Article expected = new Article(3L, "고구려전쟁", "살수대첩");
		// 실제
		Article article = articleService.update(id, dto);
		// 비교
		assertEquals(expected.toString(), article.toString());

	}

	@Test
	@Transactional
	void testUpdate_성공_존재하는_id와_content만_있는_dto_입력() {
		// 예상
		Long id = 3L;
		String content = "고구려장군";
		ArticleForm dto = new ArticleForm(id, null, content);
		Article expected = new Article(3L, "을지문덕", "고구려장군");
		// 실제
		Article article = articleService.update(id, dto);
		// 비교
		assertEquals(expected.toString(), article.toString());

	}

	@Test
	@Transactional
	void testUpdate_실패_존재하지_않는_id의_dto_입력() {
		// 예상
		Long id = 4L;
		String content = "고구려장군";
		ArticleForm dto = new ArticleForm(id, null, content);
		Article expected = null;
		// 실제
		Article article = articleService.update(id, dto);
		// 비교
		assertEquals(expected, article);

	}

	@Test
	@Transactional
	void testUpdate_실패_id만_있는_dto_입력() {
		// 예상
		Long id = 3L;
		ArticleForm dto = new ArticleForm(id, null, null);
		Article expected = null;
		// 실제
		Article article = articleService.update(id, dto);
		// 비교
		assertEquals(expected, article);
	}

	@Test
	@Transactional
	void testDelete_성공_존재하는_id_입력() {
		// 예상
		Long id = 1L;
		Article expected = new Article(1L, "이순신", "명량해전");
		// 실제
		Article article = articleService.delete(id);
		// 비교
		assertEquals(expected.toString(), article.toString());

	}

	@Test
	@Transactional
	void testDelete_실패_존재하지_않는_id_입력() {
		// 예상
		Long id = -1L;
		Article expected = null;
		// 실제
		Article article = articleService.show(id);
		// 비교
		assertEquals(expected, article);
	}

}
