package com.arnaud.p3.ChaTop.utils;

import com.arnaud.p3.ChaTop.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private Integer id;
  private String username;    // on utilisera l’email comme username
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public static UserDetailsImpl build(Users user) {
    return new UserDetailsImpl(
      user.getId(),
      user.getEmail(),
      user.getPassword(),
      user.getRoles().stream()
        .map(r -> (GrantedAuthority) () -> r.getName())
        .toList()
    );
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
