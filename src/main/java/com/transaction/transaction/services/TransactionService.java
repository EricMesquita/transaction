package com.transaction.transaction.services;

import static com.transaction.transaction.enumarations.TypeValue.POSITIVE;
import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.enumarations.OperationType;
import com.transaction.transaction.exceptions.AccountException;
import com.transaction.transaction.exceptions.TransactionException;
import com.transaction.transaction.mappers.TransactionMapper;
import com.transaction.transaction.repositories.AccountRepository;
import com.transaction.transaction.repositories.TransactionRepository;

@Service
@AllArgsConstructor
public class TransactionService {

	private static final String ACCOUNT_NOT_FOUND = "A conta não foi encontrada";
	private static final String ACCOUNT_WITHOUT_CREDIT_AVAILABLE = "Conta sem credito para realizar a transação";
	private static final String ACCOUNT_WITHOUT_CREDIT = "Valor de crédito inexistente";

	private TransactionMapper mapper;

	private TransactionRepository repository;

	private AccountRepository accountRepository;

	@Transactional
	public Transaction create(TransactionDTO dto) {
		Optional<Account> account = findAccount( dto );
		validateIfAccountExist( account );
		Transaction transaction = mapper.toEntity( dto );
		treatAmountToPositiveOrNegative( transaction );
		Transaction resultTransaction = repository.save( transaction );
		saveNewCredit( account, resultTransaction );
		return resultTransaction;
	}

	private void saveNewCredit(Optional<Account> account,
			Transaction transaction) {
		Account accountResult = account.get();
		account.get().setCredit( getNewCreditAccount( accountResult, transaction ) );
		accountRepository.save( accountResult );
	}

	private BigDecimal getNewCreditAccount(Account account, Transaction transaction) {
		BigDecimal resultValue = getNewCreditValue( account, transaction );
		if (verifyHasCredit( resultValue ))
			throw new TransactionException( ACCOUNT_WITHOUT_CREDIT_AVAILABLE );
		return resultValue;
	}

	private boolean verifyHasCredit(BigDecimal resultValue) {
		return resultValue.compareTo( BigDecimal.ZERO ) < 0;
	}

	private BigDecimal getNewCreditValue(Account account, Transaction transaction) {
		return ofNullable( account )
				.map( Account::getCredit )
				.map( credit -> credit.add( transaction.getAmount() ) )
				.orElseThrow( () -> new TransactionException( ACCOUNT_WITHOUT_CREDIT ) );
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

	private void validateIfAccountExist(Optional<Account> account) {
		account.orElseThrow( () -> new AccountException( ACCOUNT_NOT_FOUND ) );
	}

	private Optional<Account> findAccount(TransactionDTO dto) {
		return accountRepository.findById( dto.getAccountId() );
	}
}
