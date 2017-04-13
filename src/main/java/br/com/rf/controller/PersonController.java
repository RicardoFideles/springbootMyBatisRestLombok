package br.com.rf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.rf.domain.Person;
import br.com.rf.mapper.PersonMapper;

@RestController
public class PersonController {

	@Autowired
	PersonMapper personMapper;

	@RequestMapping(path = "/person", method = RequestMethod.GET)
	public List<Person> findAll() {
		return (List<Person>) personMapper.selectAll();
	}

}
