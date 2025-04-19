package com.dmm.projectManagementSystem.model;
import com.dmm.projectManagementSystem.dto.user.CreateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UpdateUserRequest;
import com.dmm.projectManagementSystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_num", unique = true)
    private String idNum;

    private String name;
    private int age;
    private String password;
    private String dob;
    private String cccd;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String sex;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String address;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name().toUpperCase()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return idNum;
    }

    static public User fromCreateUserRequest(
            CreateUserRequest request,
            Department department,
            Role role
    ) {
        return User.builder()
                .role(role)
                .name(request.getName())
                .age(request.getAge())
                .dob(request.getDob())
                .cccd(request.getCccd())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .sex(request.getSex())
                .address(request.getAddress())
                .department(department)
                .active(true)
                .build();
    }

    static public User fromUpdateUserRequest(
            UpdateUserRequest request
    ) {
        return User.builder()
                .name(request.getName())
                .age(request.getAge())
                .dob(request.getDob())
                .cccd(request.getCccd())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .sex(request.getSex())
                .address(request.getAddress())
                .active(true)
                .build();
    }
}
