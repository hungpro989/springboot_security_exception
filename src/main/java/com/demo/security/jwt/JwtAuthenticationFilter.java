package com.demo.security.jwt;

import com.demo.security.config.CustomerUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        //kiem tra Header Authorization co chua thong tin jwt ko? Có bắt đầu bằng từ khoá "Bearer " hay kô?
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            //nếu có thì cắt lấy chuỗi sau ký tự thứ 7
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // lấy jwt từ request
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt)&& jwtTokenProvider.validateToken(jwt)){
                // lay  username tu chuoi jwt
                String username = jwtTokenProvider.getUsernameFromJwt(jwt);
                //lay thong tin nguoi dung tu userId
                UserDetails userDetails =  customerUserDetailsService.loadUserByUsername(username);
                if(userDetails != null){
                    //Neu nguoi dung hop le set thong tin cho security context
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception e) {
            log.error("Fail on set user authentication", e);
        }
        filterChain.doFilter(request,response);
    }
}
