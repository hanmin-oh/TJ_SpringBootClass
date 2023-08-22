package com.tjoeun.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.firstproject.dto.CommentDTO;
import com.tjoeun.firstproject.service.CommentService;

@RestController
public class CommentApiController {
	
	@Autowired
	private CommentService commentService;
	
//	댓글 목록 조회 - ID
//	Talend API Tester(GET) => http://localhost:9090/api/comments/id/comments
	@GetMapping("/api/comments/{articleId}/comments")
//	@GetMapping("/hanmin")
	public ResponseEntity<List<CommentDTO>> comments(@PathVariable Long articleId) {
		System.out.println("============CommentApiConroller의 comments() 매소드 실행");
		System.out.println("============articleId : " + articleId);
//		서비스에 위임
		List<CommentDTO> dtos = commentService.comments(articleId);
//		결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
//	댓글 목록 조회 - nickname
	@GetMapping("/api/comments/{nickname}/nickname")
//	@GetMapping("/hanmin")
	public ResponseEntity<List<CommentDTO>> nickname(@PathVariable String nickname) {
		System.out.println("============CommentApiConroller의 nickname() 매소드 실행");
		System.out.println("============nickname : " + nickname);
//		서비스에 위임
		List<CommentDTO> dtos = commentService.nickname(nickname);
//		결과 응답
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
//	댓글 생성
	@PostMapping("/api/comments/{articleId}/comments")
	public ResponseEntity<CommentDTO> create(@PathVariable Long articleId , @RequestBody CommentDTO dto) {
		System.out.println("============CommentApiConroller의 create() 매소드 실행");
		System.out.println("================" + articleId + dto.getNickname() + dto.getBody());
		CommentDTO createDto = commentService.create(dto , articleId);
		
		return ResponseEntity.status(HttpStatus.OK).body(createDto);
	}
	
}
