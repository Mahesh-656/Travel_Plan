package org.travel_plan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.travel_plan.projection.Role;
import org.travel_plan.projection.UserStatus;
import org.travel_plan.util.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

        String name;
        @Column(unique = true,nullable = false)
        String email;
        @JsonIgnore
        @Column(nullable = false)
        String password;
        @JsonIgnore
        @Enumerated(EnumType.STRING)
        Role role;
       @Enumerated(EnumType.STRING)
        UserStatus status;
        @CreationTimestamp
        LocalDateTime createdAt;
}
