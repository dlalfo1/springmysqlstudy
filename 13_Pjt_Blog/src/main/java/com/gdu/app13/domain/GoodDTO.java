package com.gdu.app13.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.sql.DATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDTO {
	private int memberNo;
	private int blogNo;
	private DATE markedAt;
}
