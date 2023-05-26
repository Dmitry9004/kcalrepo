package com.example.demo;

import java.util.HashMap;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService{

	HashMap<String, Double> countAct  = new HashMap<>();
	{
		countAct.put("oneTwo", 1.2);
		countAct.put("oneThreeSeventFive", 1.375);
		countAct.put("oneFiveFive", 1.55);
		countAct.put("oneSeventTwoFive", 1.725);
		countAct.put("oneNine", 1.9);
	};
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	private UsersRepository userRepo;
	
	@Override
	@Transactional	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("пользователь не найден");
		}
		return user;
	}

	@Override
	public void saveUser(User user, UserDto userDto) {
		User userSave = new User();
		userSave.setUsername(userDto.getUsername());
		userSave.setPassword(encoder.encode(userDto.getPassword()));
		userSave.setCountCalories(countCalc(userDto));
		userSave.setGender(userDto.getGender());
		userSave.setAge(userDto.getAge());
		userSave.setGrowth(userDto.getGrowth());
		userSave.setWeight(userDto.getWeight());
		userSave.setCountAction(userDto.getCountAction());
		userSave.setTarget(userDto.getTarget());
		userSave.setRole(ERole.USER);
		userSave.setBMI(countBMI(userDto));
		userRepo.save(userSave);
	}
	
	public String countBMI(UserDto user) {
		return String.format("%.1f", 10000 * (user.getWeight()/Math.pow(user.getGrowth(), 2)));
}
	
	private int countCalc(UserDto user) {
		double caloriesNormal = 0;
		caloriesNormal = ((user.getWeight()*10) + (6.25 * user.getGrowth()) - (5 * user.getAge())); 
	if(user.getGender() == "female") {
		caloriesNormal -= 161;
	}else {caloriesNormal += 5;}
	
	caloriesNormal = caloriesNormal * countAct.get(user.getCountAction());
	return (int) (user.getTarget().equals("weightLoss")
			?(caloriesNormal - ((int) Math.round((caloriesNormal*20)/100)))
			:user.getTarget().equals("weightMore")
				?(int) caloriesNormal + 200: caloriesNormal);
	}
	

	@Override
	public void updateUser(Long id, UserDto userDto) {
		User userSave = userRepo.getReferenceById(id);
		userSave.setUsername(userDto.getUsername());
		userSave.setPassword(encoder.encode(userDto.getPassword()));
		userSave.setCountCalories(countCalc(userDto));
		userSave.setGender(userDto.getGender());
		userSave.setAge(userDto.getAge());
		userSave.setGrowth(userDto.getGrowth());
		userSave.setWeight(userDto.getWeight());
		userSave.setCountAction(userDto.getCountAction());
		userSave.setTarget(userDto.getTarget());
		userSave.setRole(ERole.USER);
		userSave.setTarget(userDto.getTarget());
		userSave.setBMI(countBMI(userDto));
		userRepo.save(userSave);
	}
}
