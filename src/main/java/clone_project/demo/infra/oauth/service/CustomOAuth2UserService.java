package clone_project.demo.infra.oauth.service;

import clone_project.demo.infra.oauth.dto.OAuth2MemberInfo;
import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.domain.member.mapper.MemberMapper;
import clone_project.demo.infra.oauth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1.  유저 정보 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // 2. registrationId 가져오기 (third party id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 유저 정보 dto 생성
        OAuth2MemberInfo oAuth2MemberInfo = OAuth2MemberInfo.of(registrationId, oAuth2UserAttributes);

        Member member = getOrSave(oAuth2MemberInfo);
        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }

    private Member getOrSave(OAuth2MemberInfo oAuth2MemberInfo) {
        Member byEmail = memberMapper.findByEmail(oAuth2MemberInfo.email());
        if (byEmail != null) {
            return byEmail;
        }
        return memberMapper.save(oAuth2MemberInfo.toEntity());
    }
}
