package br.com.rf;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.rf.domain.Person;
import br.com.rf.mapper.PersonMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Class01Application.class)
@WebAppConfiguration
public class PersonRestControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private List<Person> people = new ArrayList<Person>();

	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.personMapper.deleteAll();
		people = Arrays.asList(new Person(null, "John", "Wick", 30), new Person(null, "John", "McClane", 50),
				new Person(null, "John", "Rambo", 65));

		people.forEach(personMapper::insert);

	}

	@Test
	public void readPeople() throws Exception {
		mockMvc.perform(get("/person")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[0].lastName", is("Wick")))
				.andExpect(jsonPath("$[0].age", is(30)));
	}

	@Test
	public void searchForPersonWithNameJohn() throws Exception {

		Gson gson = new Gson();

		String personJson = gson.toJson(new Person(null, "John", null, null));

		this.mockMvc.perform(post("/person/search").contentType(contentType).content(personJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(3)));

	}

	@Test
	public void searchForPersonWithAge30() throws Exception {

		Gson gson = new Gson();

		String personJson = gson.toJson(new Person(null, null, null, 30));

		this.mockMvc.perform(post("/person/search").contentType(contentType).content(personJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1)));

	}

	@Test
	public void searchForPersonWithLastNameRamboAndAge30() throws Exception {

		Gson gson = new Gson();

		String personJson = gson.toJson(new Person(null, null, "Rambo", 30));

		this.mockMvc.perform(post("/person/search").contentType(contentType).content(personJson))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	public void createBookmark() throws Exception {
		Gson gson = new Gson();

		String personJson = gson.toJson(new Person(null, "Ricardo", "Fideles", 32));

		this.mockMvc.perform(post("/person").contentType(contentType).content(personJson))
				.andExpect(status().isCreated());
	}

}
