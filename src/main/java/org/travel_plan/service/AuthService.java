package org.travel_plan.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.travel_plan.dto.AuthRequest;
import org.travel_plan.dto.AuthResponse;
import org.travel_plan.model.User;
import org.travel_plan.projection.Role;
import org.travel_plan.projection.UserStatus;
import org.travel_plan.repository.TravelPlanRepository;
import org.travel_plan.repository.UserRepository;
import org.travel_plan.util.JwtUtil;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TravelPlanRepository travelPlanRepository;

    public void createUser(User user) {
        Role role=userRepository.count()==0?Role.ADMIN:Role.USER;
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("No credentials found!!.."));
          if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
              throw new RuntimeException("Please enter valid password");
          }
          String token=jwtUtil.generateToken(user.getEmail(), user.getRole().name());
          return new AuthResponse(token);
    }



}
