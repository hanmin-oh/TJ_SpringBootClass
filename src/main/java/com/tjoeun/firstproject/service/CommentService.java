package com.tjoeun.firstproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.firstproject.dto.CommentDTO;
import com.tjoeun.firstproject.entity.Article;
import com.tjoeun.firstproject.entity.Comment;
import com.tjoeun.firstproject.repository.ArticleRepository;
import com.tjoeun.firstproject.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;
	
//	댓글 목록 조회
	@Transactional
	public List<CommentDTO> comments(Long articleId) {
		System.out.println("=============CommentService의 comments() 매소드 실행");
		System.out.println("========" + articleId);
		List<Comment> comments = commentRepository.findByArticleId(articleId);
		System.out.println("================" + comments);
//		entity를 dto로 변환
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();
		for(int i = 0 ; i<comments.size() ; i++) {
			Comment comment = comments.get(i);
			CommentDTO dto = CommentDTO.createCommentDTO(comment);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	public List<CommentDTO> nickname(String nickname) {
		System.out.println("=============CommentService의 nickname() 매소드 실행");
//		System.out.println("========" + articleID);
		List<Comment> comments = commentRepository.findByNickname(nickname);
		System.out.println("================" + comments);
//		entity를 dto로 변환
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();
		for(int i = 0 ; i<comments.size() ; i++) {
			Comment comment = comments.get(i);
			CommentDTO dto = CommentDTO.createCommentDTO(comment);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	public CommentDTO create(CommentDTO dto , Long articleId) {
		System.out.println("=============CommentService의 create() 매소드 실행");
//		댓글을 저장하려는 메인글이 있으면 얻어오고 없으면 예외를 발생시킨다.
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글 없음"));
//		댓글 엔티티 생성
//		dto를 entity로 변환하는 메소드를 호출한다.
		Comment comment = Comment.createComment(dto, article);
//		댓글 entity를 저장
		Comment created = commentRepository.save(comment);
//		dto로 변환해서 반환
		return CommentDTO.createCommentDTO(created);
	}

//	댓글 수정
	@Transactional
	public CommentDTO update(Long id, CommentDTO dto) {
		System.out.println("=============CommentService의 update() 매소드 실행");
//		수정하려는 댓글이 있으면 얻어오고 없으면 예외를 발생시킨다.
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() ->
				new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
//		댓글 수정, 댓글을 갱신하는 메소드를 실행한다.
		comment.patch(dto);
//		수정된 댓글로 다시 저장
		Comment updated = commentRepository.save(comment);
//		수정된 댓글 entity를 dto로 변환해서 반환
		return CommentDTO.createCommentDTO(updated);
	}

//	댓글 삭제
	@Transactional
	public CommentDTO delete(Long id) {
		System.out.println("=============CommentService의 delete() 매소드 실행");
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() ->
				new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없습니다."));
		commentRepository.delete(comment);
		return CommentDTO.createCommentDTO(comment);
	}
	
	
	
	
}
