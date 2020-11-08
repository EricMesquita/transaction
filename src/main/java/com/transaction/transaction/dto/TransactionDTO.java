package com.transaction.transaction.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	@JsonProperty("account_id")
	@NotNull(message = "O identificador não pode ser nulo")
	private Long accountId;

	@JsonProperty("operation_type_id")
	@NotNull(message = "O tipo da operação não pode ser nulo")
	private Long operationType;

	@NotNull(message = "O valor da transação não pode ser nulo")
	private BigDecimal amount;
}
