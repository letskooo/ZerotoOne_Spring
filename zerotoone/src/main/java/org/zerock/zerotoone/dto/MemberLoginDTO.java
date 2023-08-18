package org.zerock.zerotoone.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberLoginDTO extends User {

    private String username;
    private String password;

    public MemberLoginDTO(String username, String password, Collection<GrantedAuthority> authorities){

        super(username, password, authorities);
        this.username = username;
        this.password = password;

    }
}
