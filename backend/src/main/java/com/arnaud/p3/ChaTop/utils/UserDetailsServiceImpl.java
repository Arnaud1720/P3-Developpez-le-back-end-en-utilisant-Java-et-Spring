package com.arnaud.p3.ChaTop.utils;

import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UsersRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Users user = userRepo.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    return UserDetailsImpl.build(user);
  }
}
