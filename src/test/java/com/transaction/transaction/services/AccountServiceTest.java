package com.transaction.transaction.services;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.exceptions.AccountException;
import com.transaction.transaction.mappers.AccountMapper;
import com.transaction.transaction.repositories.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	private static final String DOCUMENT_NUMBER = "12345678900";
	private static final long ID = 1l;

	@InjectMocks
	private AccountService service;

	@Mock
	private AccountRepository repository;

	@Mock
	private AccountMapper mapper;

	@Captor
	private ArgumentCaptor<Account> accountCaptor;

	@Test
	public void shouldCreateAccount() {
		AccountDTO accountDTO = buildAccountDTO();
		Account account = buildAccount();
		when( mapper.toEntity( accountDTO ) ).thenReturn( account );
		when( mapper.toDTO( account ) ).thenReturn( accountDTO );
		when( repository.save( account ) ).thenReturn( account );

		AccountDTO dto = service.create( accountDTO );

		verify( repository ).save( accountCaptor.capture() );
		assertThat( accountCaptor.getValue().getDocument(), equalTo( DOCUMENT_NUMBER ) );
		assertThat( dto.getId(), equalTo( ID ) );
		assertThat( dto.getDocumentNumber(), equalTo( DOCUMENT_NUMBER ) );
	}

	@Test(expected = AccountException.class)
	public void shouldReturnAccountException() {
		AccountDTO accountDTO = buildAccountDTO();
		Account account = buildAccount();
		when( repository.findByDocument( accountDTO.getDocumentNumber() ) ).thenReturn( account );

		service.create( accountDTO );

		verifyNoInteractions( mapper );
	}

	@Test
	public void shouldReturnAccountDTO() {
		Optional<Account> account = of( buildAccount() );
		when( repository.findById( ID ) ).thenReturn( account );
		when( mapper.toDTO( account.get() ) ).thenReturn( buildAccountDTO() );

		AccountDTO dto = service.find( ID );

		assertThat( dto.getId(), equalTo( ID ) );
		assertThat( dto.getDocumentNumber(), equalTo( DOCUMENT_NUMBER ) );
	}

	@Test(expected = AccountException.class)
	public void shouldReturnAccountExceptionWhenAccountNotFound() {
		when( repository.findById( ID ) ).thenReturn( empty() );

		service.find( ID );

		verifyNoInteractions( mapper );
	}

	@Test(expected = AccountException.class)
	public void shouldReturnAccountExceptionWhenAccountIdIsNull() {
		service.find( null );

		verifyNoInteractions( mapper );
		verifyNoInteractions( repository );
	}

	private AccountDTO buildAccountDTO() {
		return AccountDTO.builder()
				.id( ID )
				.documentNumber( DOCUMENT_NUMBER )
				.build();
	}

	private Account buildAccount() {
		return Account.builder()
				.document( DOCUMENT_NUMBER )
				.build();
	}
}
