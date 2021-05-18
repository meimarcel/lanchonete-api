package com.marcel.Lanchonete.config;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.Lanchonete.enums.DetailType;
import com.marcel.Lanchonete.error.MasterDetails;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.util.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = ((HttpServletRequest) request).getHeader(SecurityConstant.HEADER_STRING);
            if(header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            Authentication authentication = this.getAuthentication((HttpServletRequest) request, header);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch(Exception e) {
            e.printStackTrace();
            MasterDetails masterErrorDetails = MasterDetails.Builder
                .newBuilder()
                .title("Invalid token")
                .message("Acesso expirado")
                .type(DetailType.ERROR)
                .timestamp(LocalDateTime.now())
                .build();
            
            HttpServletResponse newResponse = (HttpServletResponse) response;
            newResponse.setStatus(HttpStatus.FORBIDDEN.value());
            newResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            newResponse.getWriter().write(objectMapper.writeValueAsString(masterErrorDetails));
        }
        
    }

    private Authentication getAuthentication(HttpServletRequest request, String header) {
        try {
            String token = header.replace(SecurityConstant.TOKEN_PREFIX, "");
            Manager manager = jwtUtils.decodeToken(token);
            return new UsernamePasswordAuthenticationToken(manager, null, manager.getAuthorities());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    
    
}
