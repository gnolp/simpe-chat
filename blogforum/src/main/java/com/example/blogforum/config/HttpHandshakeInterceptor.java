package com.example.blogforum.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.blogforum.security.JwtDecoder;
import com.example.blogforum.security.JwtProperties;
import com.example.blogforum.security.JwtToPrincipalConverter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
	private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    @Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
	                               WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
	
	    if (request instanceof ServletServerHttpRequest servletRequest) {
	        String authHeader = servletRequest.getServletRequest().getParameter("Authorization");
	        if (authHeader == null) {
	            authHeader = servletRequest.getServletRequest().getHeader("Authorization");
	        }
	
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            String token = authHeader.substring(7);
	            System.out.println(token);
	            try {
	                var decodedJwt = jwtDecoder.decode(token); // có thể ném lỗi nếu token hết hạn
	                var principal = jwtToPrincipalConverter.convert(decodedJwt);
	                System.out.println(principal.getUserId());
	                attributes.put("userPrincipal", principal);
	
	                return true;
	            } catch (Exception e) {
	                System.out.println("JWT token invalid or expired: " + e.getMessage());
	                return false;
	            }
	        } else {
	            return false;
	        }
	    }
	
	    return false;
	}


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {}

}
