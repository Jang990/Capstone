package com.inhatc.spring.capstone.user.dto;

import java.util.Map;

import com.inhatc.spring.capstone.user.entity.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
/** OAuth 처리를 위한 DTO */
public class OAuthAttributes {
	private String name;
	private String email;
	private String picture;
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	
	@Builder
	public OAuthAttributes(String name, String email, String picture, Map<String, Object> attributes,
			String nameAttributeKey) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		if ("naver".equals(registrationId)) {
			return ofNaver("id", attributes);
	    }
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
        		.name((String) attributes.get("family_name") + (String) attributes.get("given_name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    
    public Users toEntity() {
    	return Users.createUser(new UsersJoinDTO(this.email, this.name, "OAuthUser"));
    }

}
