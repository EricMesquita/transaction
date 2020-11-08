package com.transaction.transaction.mappers;

import static com.transaction.transaction.enumarations.OperationType.PAID_CASH;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.exceptions.OperationTypeException;

public class TransactionMapperTest {

	private static final BigDecimal AMOUNT = new BigDecimal( "10.99" );
	private static final long ACCOUNT_ID = 1L;
	private static final long OPERATION_TYPE = 1L;
	private static final long OPERATION_TYPE_NONEXISTS = 5L;

	private TransactionMapper mapper = new TransactionMapper();

	@Test
	public void shouldReturnTransactionEntity() {
		TransactionDTO dto = buildTransactionDTO(OPERATION_TYPE);

		Transaction transaction = mapper.toEntity( dto );

		assertThat( transaction.getAccount().getId(), equalTo( ACCOUNT_ID ) );
		assertThat( transaction.getAmount(), equalTo( AMOUNT ) );
		assertThat( transaction.getOperationType(), equalTo( PAID_CASH ) );
	}
	
	@Test(expected = OperationTypeException.class)
	public void shouldReturnOperationTypeExceptionWhenOperationTypeIdNotExists() {
		TransactionDTO dto = buildTransactionDTO( OPERATION_TYPE_NONEXISTS );

		mapper.toEntity( dto );
	}

	private TransactionDTO buildTransactionDTO(Long operationType) {
		return TransactionDTO.builder()
				.accountId( ACCOUNT_ID )
				.amount( AMOUNT )
				.operationType( operationType )
				.build();
	}
}
