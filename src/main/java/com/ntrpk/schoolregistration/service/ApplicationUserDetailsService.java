package com.ntrpk.schoolregistration.service;

import com.ntrpk.schoolregistration.model.Student;
import com.ntrpk.schoolregistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Value("${application.admin.username}")
    private String adminUser;

    @Value("${application.admin.password}")
    private String adminPassword;

    private final GrantedAuthority GA_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    private final GrantedAuthority GA_STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (name.equals(adminUser)) {
            return new CustomUserDetails(adminUser, adminPassword, GA_ADMIN);
        }
        Student student = studentRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(student.getUsername(), student.getPassword(), GA_STUDENT);
    }

    private static class CustomUserDetails implements UserDetails {

        private final String username;
        private final String password;
        private final Collection<? extends GrantedAuthority> authorities;

        private CustomUserDetails(String username, String password, GrantedAuthority authority) {
            this.username = username;
            this.password = password;
            this.authorities = Collections.singletonList(authority);
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
}