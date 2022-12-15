package com.inhatc.spring.capstone.utiltest;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

@SpringBootTest
public class UtilTest {
	
	@Value(value = "${techIconLocation}")
	private String techLocation;
	
	@Autowired
	TagRepository tagRepository; 
	
//	@Test
	@DisplayName("데이터 넣기")
	void testData() throws IOException {
		ClassPathResource cpr = new ClassPathResource(techLocation);
		File[] files = cpr.getFile().listFiles();
		for (File file : files) {
			try {
				tagRepository.save(Tag.builder().taggedCount(0).name(file.getName()).type(TagType.TECH).build());
			}
			catch (Exception e) {
				continue;
			}
		}
	}
}
