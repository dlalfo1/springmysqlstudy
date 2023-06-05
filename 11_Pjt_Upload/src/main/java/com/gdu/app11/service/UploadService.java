package com.gdu.app11.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.domain.UploadDTO;

public interface UploadService {


  public void getUploadList(HttpServletRequest request, Model model);
  public int addUpload(MultipartHttpServletRequest multipartRequest); //첨부시엔 MultipartHttpServletRequest 이걸 사용한다.
  public void getUploadByNo(int uploadNo, Model model);
  public ResponseEntity<byte[]> display(int attachNo);
  public ResponseEntity<Resource> download(int attachNo, String userAgent);
  public ResponseEntity<Resource> downloadAll(int uploadNo);
  public int removeUpload(int uploadNo);
  public int modifyUpload(MultipartHttpServletRequest multipartRequest);
  public int removeAttach(int attachNo);
}
