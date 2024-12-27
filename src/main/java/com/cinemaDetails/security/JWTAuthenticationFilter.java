package com.cinemaDetails.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Used to authenticate the jwt token by using jwttokenprovider utility class
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //JWT will be sent in header
        //get JWT token from HTTP Request
        String tokenFromRequest = getTokenFromRequest(request);

        //validate Token
        if(StringUtils.hasText(tokenFromRequest) && jwtTokenProvider.validateJWTToken(tokenFromRequest)){
            
            //get username from token
            String userNameFromJWT = jwtTokenProvider.getUserNameFromJWT(tokenFromRequest);

            //load the user associated with the token
            //ensures user exists, is active, and has up-to-date roles/permissions
            UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromJWT);

                //userDetails: Represents the authenticated user.
                //null: As there is no password required (we're authenticating via JWT, not credentials).
                //userDetails.getAuthorities(): User roles/permissions.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (userDetails, null, userDetails.getAuthorities());

            //Adds additional details (like the originating IP address or session ID) from the request object to the authentication token
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //This ensures subsequent requests in the same session will recognize the user as authenticated
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }else{
            return null;
        }
    }
}
