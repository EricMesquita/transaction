package com.transaction.transaction.services;

import static java.util.Optional.ofNullable;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.exceptions.AccountException;
import com.transaction.transaction.mappers.AccountMapper;
import com.transaction.transaction.repositories.AccountRepository;

@Service
public class AccountService {

	private static final String DOCUMENT_EXCEPTION_MESSAGE = "Já existe uma conta cadastrada com o documento!";

	@Autowired
	private AccountRepository repository;

	@Autowired
	private AccountMapper mapper;

	public AccountDTO create(AccountDTO accountDTO) {
		if (verifyIfExistsAlreadyAccount( accountDTO ))
			throw new AccountException( DOCUMENT_EXCEPTION_MESSAGE );
		Account account = repository.save( mapper.toEntity( accountDTO ) );
		return mapper.toDTO( account );
	}

	private boolean verifyIfExistsAlreadyAccount(AccountDTO accountDTO) {
		return ofNullable( repository.findByDocument( accountDTO.getDocumentNumber() ) )
				.isPresent();
	}
}
