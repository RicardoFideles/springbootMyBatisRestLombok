package br.com.rf.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rf.domain.Person;
import br.com.rf.mapper.PersonMapper;

@RestController
@RequestMapping("/person")
public class PersonRestController {

	@Autowired
	PersonMapper personMapper;

	@RequestMapping(method = RequestMethod.GET)
	public List<Person> findAll() {
		return (List<Person>) personMapper.selectAll();
	}

	@RequestMapping(method = RequestMethod.POST,value = "/{search}")
	public List<Person> searchBy(@RequestBody Person person) {
		return (List<Person>) personMapper.search(person.getFirstName(), person.getLastName(), person.getAge());
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(@RequestBody Person person) {
		Long id = personMapper.insert(person);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id)
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
