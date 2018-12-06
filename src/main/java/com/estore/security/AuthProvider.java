package com.estore.security;

import com.estore.Constants;
import com.estore.co.UserDetailCO;
import com.estore.co.UserLoginCO;
import com.estore.exception.GenericException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by Ashutosh on 01 Jul, 2017.
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    @Value("${endpoint.user.auth}")
    private String userAuthURL;

    @Value("${endpoint.guest.auth}")
    private String guestAuthURL;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal() != null ? authentication.getPrincipal().toString(): null;
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        UserDetailCO userDetailCO = null;
        try {
            if(email == null || password == null || email.length() == 0 || password.length() == 0) {
                throw new GenericException(Constants.EMAIL_PASSWORD_MISSING);
            }else if(email.equals("guest")){
                userDetailCO = loginGuestUser();
                final UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userDetailCO.getUserId(), "", Arrays.asList(
                                new SimpleGrantedAuthority("GUEST")
                        ));
                token.setDetails(userDetailCO);
                return token;
            }else{
                userDetailCO = login(email, password);
                if(userDetailCO == null){
                    throw new GenericException(Constants.EMAIL_PASSWORD_MISMATCH);
                }
                final UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userDetailCO.getUserId(), password, Arrays.asList(
                                new SimpleGrantedAuthority("USER")
                        ));
                token.setDetails(userDetailCO);
                return token;
            }


        }catch(OAuth2Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private UserDetailCO login(String email, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeader = new HttpHeaders();
        UserLoginCO co = new UserLoginCO();
        co.setEmailId(email);
        co.setPassword(password);
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(co, httpHeader);
        ResponseEntity<UserDetailCO> responseEntity = restTemplate.exchange(userAuthURL, HttpMethod.POST, httpEntity, UserDetailCO.class);
        return responseEntity.getBody();
    }

    private UserDetailCO loginGuestUser(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeader = new HttpHeaders();
        UserLoginCO co = new UserLoginCO();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(co, httpHeader);
        ResponseEntity<UserDetailCO> responseEntity = restTemplate.exchange(guestAuthURL, HttpMethod.POST, httpEntity, UserDetailCO.class);
        return responseEntity.getBody();
    }

}
