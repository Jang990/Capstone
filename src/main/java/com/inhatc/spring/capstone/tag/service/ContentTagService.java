package com.inhatc.spring.capstone.tag.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.ContentTag;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.ContentTagRepository;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
/** 컨텐츠에 저장되는 태그를 관리하는 서비스 */
public class ContentTagService {
	
//	private final ContentTagRepository contentTagRepository;
	private final ContentRepository contentRepository;
	private final TagRepository tagRepository;
	
	private final TagService tagService;
	
	/** 컨텐츠에 있는 태그 저장 */
	public List<DisplayedTagDTO> saveContentTags(Long contentId, List<DisplayedTagDTO> contentTags) {
//		if(contentTags == null || contentTags.size() == 0) {
//			return new ArrayList<>();
//		}
//		
//		// 코드가 좀 이상해졌다?
//		Content content = contentRepository.findById(contentId)
//				.orElseThrow(EntityNotFoundException::new);
//		
//		List<ContentTag> savedContentTagList = new ArrayList<>(); // 중간 테이블 저장 변수
//		List<DisplayedTagDTO> savedContentTags = new ArrayList<DisplayedTagDTO>();
//		
//		for (DisplayedTagDTO displayedTagDTO : savedContentTags) {
//			// db에 태그들을 가져오면서 저장이 필요한 태그는 저장
//			DisplayedTagDTO savedTag = tagService.saveCustomTag(displayedTagDTO); 
//			savedContentTags.add(savedTag);
//			savedContentTagList.add(
//					new ContentTag(content, tagRepository.findById(savedTag.getTagId()).orElseThrow(EntityNotFoundException::new))
//				);
//		}
//		
//		contentTagRepository.saveAll(savedContentTagList);
//		
//		return savedContentTags;
		return null;
	}
	/** 컨텐츠에 있는 태그를 불러오거나 저장해서 저장한 값을 가져옴  */
	public Set<Tag> saveContentTags(List<DisplayedTagDTO> tags) {
		Set<Tag> savedTags = new LinkedHashSet<>();
		for (DisplayedTagDTO savedTag : tags) {
			if(savedTag.getTagType().equals(TagType.NEW.toString())) {
				savedTags.add(tagService.createCustomTag(savedTag));
			}
			else {
				savedTags.add(tagService.getExistTag(savedTag));
			}
		}
		return savedTags;
	}
	
}
