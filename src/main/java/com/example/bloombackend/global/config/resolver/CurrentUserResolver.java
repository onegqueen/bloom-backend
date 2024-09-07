package com.example.bloombackend.global.config.resolver;

import com.example.bloombackend.global.config.JwtTokenProvider;
import com.example.bloombackend.global.config.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider tokenProvider;

    public CurrentUserResolver(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Long.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = getAccessTokenFrom(webRequest.getHeader("Authorization"));
        return tokenProvider.getUserIdFromToken(accessToken);
    }

    private String getAccessTokenFrom(String header) {
        if (header != null && header.startsWith("Authentication ")) {
            return header.substring(7);
        }
        return null;
    }
}