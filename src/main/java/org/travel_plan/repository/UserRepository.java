package org.travel_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travel_plan.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Override
    long count();
}
