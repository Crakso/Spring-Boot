package com.todomanager.todos.Security;

import com.todomanager.todos.Entity.ProfileEntity;
import com.todomanager.todos.Repository.ProfileRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwtSecurityFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final ProfileRepository profileRepo;

    public jwtSecurityFilter(AuthUtil authUtil, ProfileRepository profileRepository) {
        this.authUtil = authUtil;
        this.profileRepo= profileRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
        }

        String token = requestTokenHeader.split("Bearer ")[1];

        String username = authUtil.validateTokenAndgetUsernameBytoken(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            ProfileEntity profile = profileRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found."));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(profile,null,profile.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        filterChain.doFilter(request,response);
    }
}
