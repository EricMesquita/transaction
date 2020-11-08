package com.transaction.transaction.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.transaction.TransactionApplication;
import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.mappers.AccountMapper;
import com.transaction.transaction.repositories.AccountRepository;
import com.transaction.transaction.services.AccountService;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TransactionApplication.class)
public class AccountControllerTest {

	private static final String DOCUMENT_NUMBER = "12345678900";
	private static final String URL = "/accounts";
	private static final Long ID = 1l;

	private MockMvc mvc;

	@InjectMocks
	private AccountController controller;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private AccountMapper mapper;

	@Mock
	private AccountService service;

	@MockBean
	private AccountRepository repository;

	@Before
	public void setUp() throws Exception {
		initMocks( this );
		mvc = standaloneSetup( controller ).build();
	}

	@Test
	public void create() throws Exception {
		AccountDTO accountDTO = buildAccountDTO( DOCUMENT_NUMBER );
		Account account = buildAccount();
		when( service.create( accountDTO ) ).thenReturn( accountDTO );
		when( mapper.toEntity( accountDTO ) ).thenReturn( account );
		when( mapper.toDTO( account ) ).thenReturn( accountDTO );

		mvc.perform( post( URL )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper
						.writeValueAsString( buildAccountDTO( DOCUMENT_NUMBER ) ) ) )
				.andExpect( MockMvcResultMatchers.status().isOk() )
				.andExpect( jsonPath( "$.account_id", is( ID.intValue() ) ) )
				.andExpect( jsonPath( "$.document_number", equalTo( DOCUMENT_NUMBER ) ) );
	}

	@Test
	public void find() throws Exception {
		AccountDTO accountDTO = buildAccountDTO( DOCUMENT_NUMBER );
		when( service.find( ID ) ).thenReturn( accountDTO );
		when( mapper.toDTO( buildAccount() ) ).thenReturn( accountDTO );

		mvc.perform( get( URL + "/" + ID )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper
						.writeValueAsString( buildAccountDTO( DOCUMENT_NUMBER ) ) ) )
				.andExpect( MockMvcResultMatchers.status().isOk() )
				.andExpect( jsonPath( "$.account_id", is( ID.intValue() ) ) )
				.andExpect( jsonPath( "$.document_number", equalTo( DOCUMENT_NUMBER ) ) );
	}

	private Account buildAccount() {
		return Account.builder()
				.id( ID )
				.document( DOCUMENT_NUMBER )
				.build();
	}

	private AccountDTO buildAccountDTO(String documentNumber) {
		return AccountDTO.builder()
				.id( ID )
				.documentNumber( documentNumber )
				.build();
	}
}
