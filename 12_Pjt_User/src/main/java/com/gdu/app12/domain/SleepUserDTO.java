package com.gdu.app12.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepUserDTO {

  private int userNo;
  private String id;
  private String pw;
  private String name;
  private String gender;
  private String email;
  private String mobile;
  private String birthYear;
  private String birthDate;
  private String postCode;
  private String roadAddress;
  private String jibunAddress;
  private String detailAddress;
  private String extraAddress;
  private int agreeCode;
  private Date joinedAt;
  private Date pwModifiedAt;
  private Date sleptAt;
}
