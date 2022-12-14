package com.inhatc.spring.capstone.tag.constant;

public enum TagType {
	TECH, // 서버에서 넣은 Java, Spring, Bootstrap 관련 데이터. 아이콘 불러오기 가능 
	CUSTOM, // 다른 사용자가 생성한 데이터
	NEW, // 서버에 없는 사용자가 새로 만들길 원하는 데이터
	UNKNOWN
}
