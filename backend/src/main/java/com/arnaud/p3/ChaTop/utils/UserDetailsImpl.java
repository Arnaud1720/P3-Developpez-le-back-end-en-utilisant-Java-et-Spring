package com.arnaud.p3.ChaTop.utils;

import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private Integer id;
  private String username; // l'email est utilisé comme identifiant
  private String password;
  private Set<Role> roles;

  public static UserDetailsImpl build(Users user) {
    return new UserDetailsImpl(
      user.getId(),
      user.getEmail(),
      user.getPassword(),
      user.getRoles()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
      .collect(Collectors.toSet());
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
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
