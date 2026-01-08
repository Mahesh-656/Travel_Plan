package org.travel_plan.securityconfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.travel_plan.repository.UserRepository;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          return new UserPrincipal(userRepository.findByEmail(username)
                  .orElseThrow(()->new UsernameNotFoundException("Please provide valid credentials")));
    }
}
