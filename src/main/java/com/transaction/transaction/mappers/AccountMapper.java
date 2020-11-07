package com.transaction.transaction.mappers;

import org.springframework.stereotype.Component;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;

@Component
public class AccountMapper {

	public Account toEntity(AccountDTO dto) {
		return Account.builder()
				.document( dto.getDocumentNumber() )
				.build();
	}

	public AccountDTO toDTO(Account account) {
		return AccountDTO.builder()
				.id( account.getId() )
				.documentNumber( account.getDocument() )
				.build();
	}
}
