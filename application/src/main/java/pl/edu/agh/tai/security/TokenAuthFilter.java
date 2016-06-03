package pl.edu.agh.tai.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    @Value("${auth.token.name}")
    private String tokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

        SecurityContext context = SecurityContextHolder.getContext();

        try {
            // check if header contains auth token
            String authToken = request.getHeader(tokenName);

            // if there is an auth token, create an Authentication object
            if (authToken != null)  {
                // we are sending the credentials similar to basic HTTP <username>:<password>
                // split the token by :
                String[] creds = authToken.split(":");
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds[0], creds[1]);
                SecurityContextHolder.getContext().setAuthentication(token);

            }
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }

        // forward the request
        filterChain.doFilter(request, response);
    }
}
