package com.inhatc.spring.capstone.github;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
public class GitHubTest {
	
	@Autowired
	private GitHub github;
	
	@Test
	@DisplayName("레포지토리 내용 불러오기1")
	@Description("김기태 교수님 hello-colyseus 레포지토리 내용 불러오기")
	public void gitRepoTest1() throws IOException {
		GHRepository repo = github.getUser("kitae104").getRepository("hello-colyseus"); // 레포지토리 불러오기 
		String readmeContent = new String(repo.getReadme().read().readAllBytes(), StandardCharsets.UTF_8);
		System.out.println("제일 많이 사용한 언어: " + repo.getLanguage()); 
		System.out.println("============ README.md 내용 ==============");
		System.out.println(readmeContent);
		System.out.println("========================================");
	}
	
	@Test
	@DisplayName("레포지토리 내용 불러오기2")
	@Description("스프링수업 저자 레포지토리 내용 불러오기")
	public void gitRepoTest2() throws IOException {
		GHRepository repo = github.getUser("roadbook2").getRepository("shop"); // 레포지토리 불러오기 
		String readmeContent = new String(repo.getReadme().read().readAllBytes(), StandardCharsets.UTF_8);
		System.out.println("제일 많이 사용한 언어: " + repo.getLanguage()); 
		System.out.println("============ README.md 내용 ==============");
		System.out.println(readmeContent);
		System.out.println("========================================");
	}
	
	@Test
	@DisplayName("레포지토리 내용 불러오기3")
	@Description("콜라보레이터 불러오기")
	public void gitRepoTest3() throws IOException {
		GHRepository repo = github.getUser("Jang990").getRepository("Spring-Capstone-Project"); // 레포지토리 불러오기
		System.out.println("==========콜라보레이터 출력 =========");
		try {
			repo.getCollaboratorNames().forEach(System.out::println);
		} catch(HttpException e) {
			System.out.println("getCollaboratorNames 메서드는 권한이 필요합니다.");
			System.out.println("오류 메시지:" + e.getMessage());
		}
		System.out.println("========================================");
	}
	

}
