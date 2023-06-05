package com.gdu.app13.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummernoteImageDTO {
	private String filesystemName;
	private int blogNo; // BlogDTO를 가질 수도 있지만 이점이 없음. blogNo만 있으면 된다.
}
