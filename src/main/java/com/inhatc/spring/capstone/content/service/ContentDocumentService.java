package com.inhatc.spring.capstone.content.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;

import lombok.NoArgsConstructor;

/** 
 * Jsoup을 이용해서 Content에 본문 수정 및 요소 추출 
 */
@Service
@NoArgsConstructor
public class ContentDocumentService {
	
	/** 이미지 요소의 내용을 추출 */
	public List<DisplayedImageDTO> extractImageElement(String contentBody) {
		System.out.println("=============");
		System.out.println(contentBody);
		Document doc = Jsoup.parse(contentBody);
		Elements elements =  doc.getElementsByTag("img");
		
		List<DisplayedImageDTO> tempImgs = new ArrayList<>();
//		String[] styles;
		int width, height;
		for (Element element : elements) {
			// style 속성에서 width와 heigth 뽑아내기
//			styles = element.attr("style").split(";");
			width = 0;
			height = 0;
//			for (String str : styles) {
//				if(str.contains("width") && !str.contains("width-") && !str.contains("-width")) {
//					width = Integer.valueOf(str.split("=")[1].replace("px", "").trim());
//				}
//				else if(str.contains("height") && !str.contains("height-") && !str.contains("-height")) {
//					height = Integer.valueOf(str.split("=")[1].replace("px", "").trim());
//				}
//			}
			
			tempImgs.add(DisplayedImageDTO.builder()
					.savedPath(element.attr("src"))
					.originalName(element.attr("data-filename"))
					.byteSize(null)
//					.byteSize(Long.valueOf(element.attr("bytesize")))
					.width(width)
					.height(height)
					.build());
		}
		
		return tempImgs;
	}
	
	/** 이미지 src를 해당 위치로 변경 */
	public String changeImageSorce(String contentBody, String movedFolderName) {
		Element doc = Jsoup.parseBodyFragment(contentBody).body();
		Elements elements =  doc.getElementsByTag("img");
		
		String tempSrc, savedSrc;
		for (Element element : elements) {
			tempSrc = element.attr("src");
			savedSrc = tempSrc.replaceFirst("temporary", movedFolderName);
			element.attr("src", savedSrc);
		}
		
		return doc.select("body").html();
	}
	
	/* 나중에 <>같은 태그를 &lt &gt 로 변경하는 메소드도 필요할 수 있다. */
}
