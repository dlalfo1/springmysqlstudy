package com.gdu.app13.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app13.domain.MemberDTO;

@Mapper
public interface MemberMapper {
  public MemberDTO selectMemberByMap(Map<String, Object> map);
}