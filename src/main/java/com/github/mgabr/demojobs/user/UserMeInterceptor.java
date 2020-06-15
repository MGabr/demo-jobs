package com.github.mgabr.demojobs.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Interceptor which replaces "me" path variables with the users id
 */
public class UserMeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var pathVariableChanges = new HashMap<String, String>();
        pathVariables.forEach((name, pathVariable) -> {
            if ("me".equals(pathVariable)) {
                var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null) {
                    pathVariableChanges.put(name, ((IdUserDetails) principal).getId());
                }
            }
        });
        pathVariables.putAll(pathVariableChanges);
        return true;
    }
}
