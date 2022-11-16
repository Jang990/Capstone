package com.inhatc.spring.capstone.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
/** public하게(다른 사용자도) 열람할 수 있는 사용자 정보 */
public class DisplayedUserDTO {
	private String name;
	private String email;
}
