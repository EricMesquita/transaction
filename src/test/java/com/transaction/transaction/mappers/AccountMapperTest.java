package com.transaction.transaction.mappers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;

public class AccountMapperTest {

	private AccountMapper mapper = new AccountMapper();

	private static final String DOCUMENT_NUMBER = "12345678900";

	@Test
	public void shouldReturnAccountEntity() {
		AccountDTO dto = AccountDTO.builder().documentNumber( DOCUMENT_NUMBER ).build();

		Account entity = mapper.toEntity( dto );

		assertThat( entity.getDocument(), equalTo( DOCUMENT_NUMBER ) );
	}

	@Test
	public void shouldReturnAccountDTO() {
		Account entity = Account.builder().document( DOCUMENT_NUMBER ).build();

		AccountDTO dto = mapper.toDTO( entity );

		assertThat( dto.getDocumentNumber(), equalTo( DOCUMENT_NUMBER ) );
	}
}
