package fpoly.shopbe.jwt;


import fpoly.shopbe.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JwtService service;

    @Autowired
    private CustomUserDetailsService detailsService;

    private String getJwtFromReq(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromReq(request);
            if(StringUtils.hasText(jwt) && service.validateToken(jwt)){

                String username = service.usernameForToken(jwt);

                UserDetails userDetails =  detailsService.loadUserByUsername(username);

                if(userDetails !=null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            log.error("fail on set user authentication",e);
        }
        filterChain.doFilter(request,response);
    }
}
