package com.inhatc.spring.capstone.test.controller;


import org.kohsuke.github.GHEventPayload.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.test.service.boardservice;

import groovy.util.logging.Log4j2;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Controller

public class BoardController {
	
	@Autowired
	private boardservice boardservice;
	
    @GetMapping("editor/editor3")
    public String editorboard() {
		
		return "editor/editor3";
	}
	@PostMapping("/editor/editor4")
	public String boardWritePro(board board){
		
		//boardservice.write(board);
		System.out.println("-----------------------------------------------------------------------");
		//System.out.println(formtest.getPeople());
		System.out.println("백엔드,프론트엔드:"+board.getSelectbox());
		System.out.println("언어:"+board.getLanguage());
		System.out.println("백엔드 프론트엔드(언어별 인원수):"+board.getPeople());
		System.out.println("프로젝트 제목:"+board.getTitle());
        System.out.println("텍스트에디터 값:"+board.getContent());
		System.out.println("-----------------------------------------------------------------------");
		return "";
	
	}
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		JsonObject jsonObject = new JsonObject();
		
        /*
		 * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
		 */
		
		// 내부경로로 저장
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String fileRoot = contextRoot+"resources/fileupload/";
		
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(fileRoot + savedFileName);	
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "/summernote/resources/fileupload/"+savedFileName); // contextroot + resources + 저장할 내부 폴더명
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		return a;
	}
	
}



