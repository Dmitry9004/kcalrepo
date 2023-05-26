package com.example.demo;

import java.util.List;

public interface UserService{

	public void saveUser(User user, UserDto userDto);
	
	public void updateUser(Long id, UserDto userDto);
}
