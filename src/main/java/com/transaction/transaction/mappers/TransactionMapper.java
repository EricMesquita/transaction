package com.transaction.transaction.mappers;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.entities.Account;
import com.transaction.transaction.entities.Transaction;
import com.transaction.transaction.enumarations.OperationType;
import com.transaction.transaction.exceptions.OperationTypeException;

@Component
public class TransactionMapper {

	public Transaction toEntity(TransactionDTO dto) {
		return Transaction.builder()
				.amount( dto.getAmount() )
				.operationType( getOperationType( dto ) )
				.account( Account.builder().id( dto.getAccountId() ).build() )
				.build();
	}

	private OperationType getOperationType(TransactionDTO dto) {
		return Arrays.stream( OperationType.values() )
				.filter( operationType -> operationType.getCode()
						.equals( dto.getOperationType() ) )
				.findFirst()
				.orElseThrow( () -> new OperationTypeException( "Tipo de operação inexistente" ) );
	}
}
