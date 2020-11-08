package com.transaction.transaction.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.util.Optional;

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
import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.mappers.TransactionMapper;
import com.transaction.transaction.repositories.AccountRepository;
import com.transaction.transaction.repositories.TransactionRepository;
import com.transaction.transaction.services.TransactionService;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TransactionApplication.class)
public class TransactionControllerTest {

	private static final String URL = "/transactions";
	private static final Long ID = 1l;
	private static final long OPERATION_TYPE = 1l;
	private static final BigDecimal AMOUNT = new BigDecimal( "10.99" );

	private MockMvc mvc;

	@InjectMocks
	private TransactionController controller;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private TransactionMapper mapper;

	@Mock
	private TransactionService service;

	@MockBean
	private TransactionRepository repository;

	@MockBean
	private AccountRepository accountRepository;

	@Before
	public void setUp() throws Exception {
		initMocks( this );
		mvc = standaloneSetup( controller ).build();
	}

	@Test
	public void create() throws Exception {
		TransactionDTO dto = buildTransactionDTO();
		Transaction transaction = buildTransaction();
		when( accountRepository.findById( ID ) ).thenReturn( Optional.of( Account.builder().build() ) );
		when( mapper.toEntity( dto ) ).thenReturn( transaction );

		mvc.perform( post( URL )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper
						.writeValueAsString( dto ) ) )
				.andExpect( MockMvcResultMatchers.status().isOk() );
	}

	@Test
	public void createWithoutDTOInformation() throws Exception {
		TransactionDTO dto = TransactionDTO.builder().build();
		Transaction transaction = buildTransaction();
		when( accountRepository.findById( ID ) ).thenReturn( Optional.of( Account.builder().build() ) );
		when( mapper.toEntity( dto ) ).thenReturn( transaction );

		mvc.perform( post( URL )
				.contentType( MediaType.APPLICATION_JSON )
				.content( objectMapper
						.writeValueAsString( dto ) ) )
				.andExpect( MockMvcResultMatchers.status().is4xxClientError() );
	}

	private TransactionDTO buildTransactionDTO() {
		return TransactionDTO.builder()
				.accountId( ID )
				.operationType( OPERATION_TYPE )
				.amount( AMOUNT )
				.build();
	}

	private Transaction buildTransaction() {
		return Transaction.builder().build();
	}
}
