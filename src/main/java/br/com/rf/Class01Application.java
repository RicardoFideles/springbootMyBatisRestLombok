package br.com.rf;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.rf.domain.Person;
import br.com.rf.mapper.PersonMapper;

@SpringBootApplication
public class Class01Application {

	public static void main(String[] args) {
		SpringApplication.run(Class01Application.class, args);
	}

	@Bean
	CommandLineRunner mYBatisDemoExampleRunningData(PersonMapper personMapper) {
		return args -> {

			List<Person> people = Arrays.asList(new Person(null, "John", "Wick", 30),
					new Person(null, "John", "McClane", 50), new Person(null, "John", "Rambo", 65));

			// people.forEach(p -> {
			// personMapper.insert(p);
			// System.out.println(p);
			// });

			people.forEach(personMapper::insert);

			System.out.println("-------->> SELECT ALL <<--------");
			personMapper.selectAll().forEach(System.out::println);

			// System.out.println("-------->> DELETE BY ID <<--------");
			// personMapper.deleteById(1L);
			// personMapper.selectAll().forEach(System.out::println);

			System.out.println("-------->> SEARCH BY FIRSTNAME <<--------");
			personMapper.search("John", null, 0).forEach(System.out::println);

			System.out.println("-------->> SEARCH BY LASTNAME<<--------");
			personMapper.search(null, "McClane", 0).forEach(System.out::println);

			System.out.println("-------->> SEARCH BY FIRSTNAME AND AGE<<--------");
			personMapper.search("John", null, 65).forEach(System.out::println);

		};

	}
}
