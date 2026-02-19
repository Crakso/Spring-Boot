package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.ProviderType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Security.RolePermissionMapper;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String providerId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream().map(
//                role->
//                        new SimpleGrantedAuthority("ROLE_"+role.name())
//        ).collect(Collectors.toSet());
//    }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
                    Set<SimpleGrantedAuthority> permission = RolePermissionMapper.getAuthorities(role);
                    authorities.addAll(permission);
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
                }
        );

        return authorities;
    }

}
