package com.tjoeun.firstproject.dto;

import com.tjoeun.firstproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {
	
	private Long id; //id 필드 추가
	private String title;
	private String content;
	
	/*
	 * public ArticleForm() { }
	 * 
	 * public ArticleForm(String title, String content) { super(); this.title =
	 * title; this.content = content; }
	 * 
	 * public String getTitle() { return title; }
	 * 
	 * public void setTitle(String title) { this.title = title; }
	 * 
	 * public String getContent() { return content; }
	 * 
	 * public void setContent(String content) { this.content = content; }
	 * 
	 * @Override public String toString() { return "ArticleForm [title=" + title +
	 * ", content=" + content + "]"; }
	 */
	
//	DTO 데이터를 Entity(테이블과 매핑되는 클래스, Article)로 변환하는(객체를 만드는) 메소드
	public Article toEntity() {
		return new Article(id, title, content);
	}
	
	
}
