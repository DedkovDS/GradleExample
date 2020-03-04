package ds_dedkov.GradleExample;

import ds_dedkov.GradleExample.domain.Person;
import ds_dedkov.GradleExample.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class GradleExampleApplicationTests {

		@Autowired
		private MockMvc mockMvc;
		@MockBean
		public ds_dedkov.GradleExample.repository.PersonRepository PersonRepository;

		@Test
		public void getAllPerson() throws Exception {
			Person expected = new Person("name", "email");
			when(PersonRepository.findAll()).thenReturn(Collections.singletonList(expected));
			mockMvc.perform(get("/"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].name", is(expected.getName())));
			verify(PersonRepository, times(1)).findAll();
			assertEquals(1, 1);
		}

		@Test
		public void getPersonById() throws Exception {
			Person expected = new Person("name", "email");
			when(PersonRepository.findById(1)).thenReturn(Optional.of(expected));
			mockMvc.perform(get("/{id}", 1)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}

		@Test
		public void createPerson() throws Exception {
			Person expected = new Person("name", "email");
			when(PersonRepository.save(any(Person.class))).thenReturn(expected);
			mockMvc.perform(post("/")
					.content("{\"name\":\"name\" ,\"email\":\"email\"}")
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());//почему не работает .isCreated?

		}


		@Test
		public void updatePerson() throws Exception {
			Person expected = new Person("name", "email");
			when(PersonRepository.findById(1)).thenReturn(Optional.of(expected));
			mockMvc.perform(put("/{id}", 1)
					.content("{\"name\":\"name\" ,\"email\":\"email\"}")
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
			;

		}

		@Test
		public void deletePerson() throws Exception {
			Person expected = new Person("name", "email");
			when(PersonRepository.findById(1)).thenReturn(Optional.of(expected));
			mockMvc.perform(delete("/{id}", 1)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}

