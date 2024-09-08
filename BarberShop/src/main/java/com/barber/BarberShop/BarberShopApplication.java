package com.barber.BarberShop;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.HaircutApointmentRepository;
import com.barber.BarberShop.Repository.UserRepository;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class BarberShopApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HaircutApointmentRepository haircutApointmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(BarberShopApplication.class, args);
	}

	@Override
	public void run(String... args) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		/*User u1 = new User("usrnm3", passwordEncryptor.encryptPassword("pass3"), "test3@emailtest.com", "nam3", "surnam3", "065489785", User.Role.CUSTOMER);
		userRepository.save(u1);
		HaircutApointment ha = new HaircutApointment(LocalDateTime.parse("2024-08-25T14:00:00"), u1);
		ha.setStatus(HaircutApointment.Status.DONE);
		haircutApointmentRepository.save(ha);*/
		/*User barber = new User("barber1", passwordEncryptor.encryptPassword("barberPass1"), "barber1@barbershop.com", "barberNam1", "barberSurnam1", "06354123", User.Role.BARBER);
		userRepository.save(barber);*/
	}

}
