package com.inhatc.spring.capstone.user.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService; 
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.user.dto.OAuthAttributes;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	private final UsersRepository userRepository;
//    private final HttpSession httpSession;

	/** 타 플랫폼으로 부터 받은 UserRequest를 처리해서 사용자로 회원가입 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		 OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		 OAuth2User oAuth2User = delegate.loadUser(userRequest);

		 String registrationId = userRequest.getClientRegistration().getRegistrationId();
		 String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		 
		 OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		 
		 Users user = saveOrUpdate(attributes);
//		 httpSession.setAttribute("user", new SessionUser(user)); // 여기는 이해가 잘 안된다. reuturn하면 알아서 시큐리티 세션으로 가지않나? 
		 
		 Set<GrantedAuthority> userRoles = new HashSet<>();
		 user.getRoles()
		 	.stream().map(role -> role.getRole().toString())
		 	.forEach(s -> userRoles.add(new SimpleGrantedAuthority(s))); 
		 
		 return new DefaultOAuth2User(
					 userRoles, 
					 attributes.getAttributes(), 
					 attributes.getNameAttributeKey()
				 );
	}
	
	/** 
	 * DB에 없는 사용자는 새로 만들어서 추가 
	 * 기존 사용자는 이름과 사용자 이미지 업데이트
	 */
	private Users saveOrUpdate(OAuthAttributes attributes) {
        Users user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateOAuthUser(attributes.getName(), attributes.getPicture()))
        		.orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
