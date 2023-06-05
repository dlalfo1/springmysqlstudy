package com.gdu.app13.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {
  
  public Map<String, Object> addComment(HttpServletRequest request);
  public Map<String, Object> getCommentCount(int blogNo);  // 해당 게시글에 대한 댓글만 가져와야 하므로 blogNo 매개변수로 받아야한다.
  public Map<String, Object> getCommentList(HttpServletRequest request);
}
