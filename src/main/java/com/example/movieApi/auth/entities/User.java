package com.example.movieApi.auth.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message="The name field can't be empty")
    private String name;

    @NotBlank(message="The email field can't be empty")
    @Column(unique = true)
    @Email(message = "Please enter email in proper format")
    private String email;

    @NotBlank(message="The userName field can't be empty")
    @Column(unique = true)
    private String userName;

    @NotBlank(message="The password field can't be empty")
    @Size(min = 5 ,message = "Password must have at least 5 characters")
    private String password;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)// values will be treated as string
    private UserRole role;

    //by default the following values should be true such that user should not be locked
    private boolean isEnabled = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isAccountNonLocked = true ;

    private boolean isAccountNonExpired = true ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
