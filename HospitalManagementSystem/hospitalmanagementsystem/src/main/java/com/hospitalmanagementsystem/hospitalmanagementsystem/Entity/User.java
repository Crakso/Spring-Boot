package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.ProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
