package org.zerock.zerotoone.security.filter;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Log4j2
public class MemberLoginFilter extends AbstractAuthenticationProcessingFilter {

    public MemberLoginFilter(String defaultFilterProcessUrl){
        super(defaultFilterProcessUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        log.info("-----------------MemberLoginFilter----------------");

        if(request.getMethod().equalsIgnoreCase("GET")){
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }

        Map<String, String > jsonData = parseRequestJSON(request);

        log.info(jsonData);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                        jsonData.get("username"),
                        jsonData.get("password"));

        // AuthenticationManager에게 인증 위임
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request){

        try(Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);
        } catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
