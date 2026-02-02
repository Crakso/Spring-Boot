package com.hospitalmanagementsystem.hospitalmanagementsystem.Security;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Util.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class jwtSecurityAuthFilter extends OncePerRequestFilter {
    private final UserRepository userRepo;
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestHeader = request.getHeader("Authorization");

        if(requestHeader==null || requestHeader.isBlank()) {
           filterChain.doFilter(request,response);
        }

        String token = requestHeader.split("Bearer ")[1];
        String username = authUtil.validateTokenAndGetUsername(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user = userRepo.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            filterChain.doFilter(request,response);
    }

}
