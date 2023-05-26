package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;


@Data
public class UserDto{
	
	public UserDto(String username, Integer age, Integer growth, Integer weight, String countAction, String target, String gender) {
		this.username = username;
		this.age = age;
		this.growth = growth;
		this.weight = weight;
		this.countAction = countAction;
		this.target = target;
		this.gender = gender;
	}
	public UserDto() {}
	
	@NotBlank(message = "поле не должно быть пусто")
	@Size(min = 4, max = 10, message = "имя пользователя должно быть от 4 до 10 символов")
	private String username;

	@NotBlank(message = "поле не должно быть пусто")
	@Size(min = 8, message = "пароль должен быть не менее 8 символов")
	private String password;
	
	@NotBlank(message = "введите пароль повторно")
	private String passwordConfirm;
	

	@NotBlank(message = "выберите свой пол")
	private String gender;

	@NotNull(message = "поле не должно быть пустым")
	@Min(15)@Max(80)
	@Transient
	private Integer age;
	
	@NotNull(message = "поле не должно быть пустым")
	@Min(140)@Max(210)
	@Transient
	private Integer growth;
	

	@NotNull(message = "поле не должно быть пустым")
	@Min(45)@Max(120)
	private Integer weight;
	
	@NotEmpty(message = "выберите количество вашей активности")
	private String countAction;
	
	@NotEmpty(message = "выберите вашу цель")
	private String target;
	
}
