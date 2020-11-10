package com.transaction.transaction.mappers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;

public class AccountMapperTest {

	private static final BigDecimal CREDIT = new BigDecimal( "100" );
	private AccountMapper mapper = new AccountMapper();

	private static final String DOCUMENT_NUMBER = "12345678900";

	@Test
	public void shouldReturnAccountEntity() {
		AccountDTO dto = buildAccountDTO();

		Account entity = mapper.toEntity( dto );

		assertThat( entity.getDocument(), equalTo( DOCUMENT_NUMBER ) );
		assertThat( entity.getCredit(), equalTo( CREDIT ));
	}

	@Test
	public void shouldReturnAccountDTO() {
		Account entity = buildAccount();

		AccountDTO dto = mapper.toDTO( entity );

		assertThat( dto.getDocumentNumber(), equalTo( DOCUMENT_NUMBER ) );
		assertThat( dto.getAvailableCreditLimit(), equalTo( CREDIT ) );
	}

	private Account buildAccount() {
		return Account.builder()
				.credit( CREDIT )
				.document( DOCUMENT_NUMBER )
				.build();
	}

	private AccountDTO buildAccountDTO() {
		return AccountDTO.builder()
				.availableCreditLimit( CREDIT )
				.documentNumber( DOCUMENT_NUMBER )
				.build();
	}
}
