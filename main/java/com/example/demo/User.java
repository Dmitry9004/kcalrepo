package com.example.demo;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
		
	@Column(nullable = false)
	private String target;
	
	@Column(nullable = false)
	private Integer countCalories;
	
	@Column(nullable = false)
	private String BMI; 
	
	@Column(nullable = false)
	private Integer age;
	
	@Column(nullable = false)
	private Integer growth;
	
	
	@Column(nullable = false)
	private Integer weight;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false)
	private String countAction;
	
	@Enumerated(EnumType.STRING)
	private ERole role;
	
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
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}	
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getPassword() {
		return  password;
	}
		
}
