package com.dmm.projectManagementSystem.model;
import com.dmm.projectManagementSystem.enums.Role;
import jakarta.persistence.*;
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

    @Column(unique = true)
    private String idNum;

    private String name;
    private int age;
    private String password;
    private String dob;
    private String cccd;
    private String email;
    private String phoneNumber;
    private String sex;
    private String avatarUrl;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "departmentID")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "majorID")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "dutyID")
    private Duty duty;

    @ManyToOne
    @JoinColumn(name = "councilID")
    private Council council;

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
}
