package com.revs.demo.junit.programmimngknowledge;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revs.demo.junit.Book;
import com.revs.demo.junit.BookController;
import com.revs.demo.junit.BookService;


@ExtendWith(MockitoExtension.class)
@Description(value = "Youtube URL: https://youtu.be/KYkEMuA50yE")
public class BookControllerTest {

	private MockMvc mockmvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	private List<Book> bookList = new ArrayList<>();
	
	@Mock
	BookService bookService;
	
	@InjectMocks
	BookController underTest;
	
	@BeforeEach
	void setup() {
		this.mockmvc = MockMvcBuilders.standaloneSetup(BookController.class).build();
		
		bookList.add(new Book(1, "Hero", "Heroic charcater", 5));
		bookList.add(new Book(2, "Wings of Fire", "APJ Kalam Ji", 4));
		bookList.add(new Book(3, "Karvalo", "KP Pu.Cha.The", 5));
	}
	
	@Test
	@Disabled
	void test_getAllBooks_success() throws Exception {
		when(bookService.getAll()).thenReturn(bookList);
		
		mockmvc.perform(MockMvcRequestBuilders.get("/api/books")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", hasItems(3)))
		.andExpect(jsonPath("$[2].name", is("Karvalo")));
	}
	
	
	
}
