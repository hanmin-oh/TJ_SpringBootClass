package com.tjoeun.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tjoeun.firstproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
//	특정 게시글의 모든 댓글을 조회하는 메소드
//	 - @Query 어노테이션으로 query를 만들어 사용할 수 있다.
	@Query(value = "select * from comment where article_id = :articleId" , nativeQuery = true)
	List<Comment> findByArticleId(Long articleId);
	
	@Query(value = "select * from comment where nickname = :nickname" , nativeQuery = true)
	List<Comment> findByArticleNickname(String nickname);

//	특정 닉네임의 모든 댓글을 조회하는 메소드
//	 orm은 객체와 관계형 데이터
	List<Comment> findByNickname(String nickname);
}
