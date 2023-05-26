package com.example.demo;


import java.io.Serializable;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Calories {

	HashMap<String, Double> countAct  = new HashMap<>();
	{
		countAct.put("oneTwo", 1.2);
		countAct.put("oneThreeSeventFive", 1.375);
		countAct.put("oneFiveFive", 1.55);
		countAct.put("oneSeventTwoFive", 1.725);
		countAct.put("oneNine", 1.9);
	};
	
	@NotBlank(message = "выберите свой пол")
	private String gender;
	
	@NotNull(message = "введите свой возраст")
	@Min(15)@Max(80)
	private Integer age;
	
	@NotNull(message = "введите свой рост")
	@Min(140)@Max(210)
	private Integer growth;
	
	@NotNull(message = "введите свой вес")
	@Min(45)@Max(120)
	private Integer weight;
	
	@NotNull(message = "выберите количество вашей активности")
	private String countAction;
	
	private double caloriesNormal;
	
	
	public String countBMI() {
			return String.format("%.1f", 10000 * (weight/Math.pow(growth, 2)));
	}
	
	public int getCalories() {
		caloriesNormal = (weight*10) + (6.25 * growth) - (5 * age); 
		if(gender == "female") {
			caloriesNormal -= 161;
		}else {caloriesNormal += 5;}
		
		caloriesNormal = caloriesNormal * countAct.get(countAction);
		return (int) Math.round(caloriesNormal); 
	}
	public int getCaloriesMin() {
		return (int) (caloriesNormal - ((int) Math.round((caloriesNormal*20)/100))); 
	}
	public int getCaloriesMax() {
		return (int) caloriesNormal + 200;
	}
}
