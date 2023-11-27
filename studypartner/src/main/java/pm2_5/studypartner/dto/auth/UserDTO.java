package pm2_5.studypartner.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserDTO extends User {

    private String username;
    private String password;

    public UserDTO(String username, String password, Collection<GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.username = username;
        this.password = password;
    }
}
