package com.gdu.app13.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
  private int commentNo;
  private String content;
  private int state;
  private int depth;
  private int groupNo;
  private Date createdAt;
	private int blogNo; // 이미 댓글은 Blog의 상세정보를 다 알고 있다. Blog상세보기에서 댓글이 보이므로. BlogDTO를 가질 필요가 없다.
	private MemberDTO memberDTO; // 댓글에선 회원의 이름이나 아이디를 보여줄것이기 때문에 memberDTO를 갖는다. 
}
