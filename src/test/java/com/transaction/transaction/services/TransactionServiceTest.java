package com.transaction.transaction.services;

import static com.transaction.transaction.enumarations.OperationType.PAID_CASH;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.exceptions.AccountException;
import com.transaction.transaction.exceptions.TransactionException;
import com.transaction.transaction.mappers.TransactionMapper;
import com.transaction.transaction.repositories.AccountRepository;
import com.transaction.transaction.repositories.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	private static final long OPERATION_TYPE_ID = 1l;
	private static final long ACCOUNT_ID = 1L;
	private static final BigDecimal AMOUNT = new BigDecimal( "10" );
	private static final BigDecimal CREDIT = new BigDecimal( "100" );
	private static final BigDecimal NEW_CREDIT = new BigDecimal( "90" );
	private static final BigDecimal TRANSACTION_VALUE = new BigDecimal( "110" );

	@Mock
	private TransactionMapper mapper;

	@InjectMocks
	private TransactionService service;

	@Mock
	private TransactionRepository repository;

	@Mock
	private AccountRepository accountRepository;

	@Captor
	private ArgumentCaptor<Account> accountArgumentCaptor;

	@Test
	public void shouldCreateTransaction() {
		TransactionDTO transactionDTO = buildTransactionDTO();
		Transaction transaction = buildTransaction( AMOUNT );
		when( mapper.toEntity( transactionDTO ) ).thenReturn( transaction );
		when( repository.save( transaction ) ).thenReturn( transaction );
		when( accountRepository.findById( ACCOUNT_ID ) ).thenReturn( buildAccount( CREDIT ) );

		Transaction transactionResult = service.create( transactionDTO );

		assertThat( transactionResult.getAccount().getId(), equalTo( ACCOUNT_ID ) );
		assertThat( transactionResult.getOperationType(), equalTo( PAID_CASH ) );
		assertThat( transactionResult.getAmount(), equalTo( AMOUNT.negate() ) );
		verify( accountRepository ).save( accountArgumentCaptor.capture() );
		assertThat( accountArgumentCaptor.getValue().getCredit(), equalTo( NEW_CREDIT ) );
	}

	@Test
	public void shouldCreateTransactionWithPositiveAmount() {
		TransactionDTO transactionDTO = buildTransactionDTO();
		Transaction transaction = buildTransaction( AMOUNT );
		when( mapper.toEntity( transactionDTO ) ).thenReturn( transaction );
		when( repository.save( transaction ) ).thenReturn( transaction );
		when( accountRepository.findById( ACCOUNT_ID ) ).thenReturn( buildAccount( CREDIT ) );

		Transaction transactionResult = service.create( transactionDTO );

		assertThat( transactionResult.getAccount().getId(), equalTo( ACCOUNT_ID ) );
		assertThat( transactionResult.getOperationType(), equalTo( PAID_CASH ) );
		assertThat( transactionResult.getAmount(), equalTo( AMOUNT ) );
	}

	@Test(expected = AccountException.class)
	public void shouldReturnAccountExceptionWhenAccountNotFound() {
		when( accountRepository.findById( ACCOUNT_ID ) ).thenReturn( empty() );

		service.create( buildTransactionDTO() );

		verifyNoInteractions( mapper );
		verifyNoInteractions( repository.save( any() ) );
	}

	@Test(expected = TransactionException.class)
	public void shouldReturnTransactionExceptionWhenCreditAccountIsNagative() {
		TransactionDTO transactionDTO = buildTransactionDTO();
		Transaction transaction = buildTransaction( TRANSACTION_VALUE );
		when( mapper.toEntity( transactionDTO ) ).thenReturn( transaction );
		when( repository.save( transaction ) ).thenReturn( transaction );
		when( accountRepository.findById( ACCOUNT_ID ) ).thenReturn( buildAccount( CREDIT ) );

		service.create( transactionDTO );
	}

	private Optional<Account> buildAccount(BigDecimal credit) {
		return ofNullable( Account.builder()
				.id( ACCOUNT_ID )
				.credit( credit )
				.build() );
	}

	private Transaction buildTransaction(BigDecimal amount) {
		return Transaction.builder()
				.account( buildAccountId() )
				.operationType( PAID_CASH )
				.amount( amount )
				.build();
	}

	private Account buildAccountId() {
		return Account.builder()
				.id( ACCOUNT_ID )
				.credit( CREDIT )
				.build();
	}

	private TransactionDTO buildTransactionDTO() {
		return TransactionDTO.builder()
				.operationType( OPERATION_TYPE_ID )
				.amount( AMOUNT )
				.accountId( ACCOUNT_ID )
				.build();
	}
}
