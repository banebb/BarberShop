package com.barber.BarberShop;

import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarberShopApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BarberShopApplication.class, args);
	}

	@Override
	public void run(String... args) {
		/*User u1 = new User("username1", "password1", "email1", "name1", "surname1", 0L, "123456789");
		userRepository.save(u1);*/
	}

}
