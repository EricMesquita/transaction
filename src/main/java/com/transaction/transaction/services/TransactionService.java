package com.transaction.transaction.services;

import static com.transaction.transaction.enumarations.TypeValue.POSITIVE;
import static java.util.Optional.ofNullable;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.enumarations.OperationType;
import com.transaction.transaction.exceptions.AccountException;
import com.transaction.transaction.mappers.TransactionMapper;
import com.transaction.transaction.repositories.AccountRepository;
import com.transaction.transaction.repositories.TransactionRepository;

@Service
@AllArgsConstructor
public class TransactionService {

	private static final String ACCOUNT_NOT_FOUND = "A conta não foi encontrada";

	private TransactionMapper mapper;

	private TransactionRepository repository;

	private AccountRepository accountRepository;

	public Transaction create(TransactionDTO dto) {
		validateIfAccountExist( dto );
		Transaction transaction = mapper.toEntity( dto );
		treatAmountToPositiveOrNegative( transaction );
		return repository.save( transaction );
	}

	private void treatAmountToPositiveOrNegative(Transaction transaction) {
		if (transactionShouldHasPositiveValue( transaction ))
			transaction.setAmount( transaction.getAmount().abs() );
		else
			transaction.setAmount( transaction.getAmount().negate() );
	}

	private Boolean transactionShouldHasPositiveValue(Transaction transaction) {
		return ofNullable( transaction )
				.map( Transaction::getOperationType )
				.map( OperationType::getTypeValue )
				.map( POSITIVE::equals )
				.orElse( false );
	}

	private void validateIfAccountExist(TransactionDTO dto) {
		accountRepository.findById( dto.getAccountId() )
				.orElseThrow( () -> new AccountException( ACCOUNT_NOT_FOUND ) );
	}
}
