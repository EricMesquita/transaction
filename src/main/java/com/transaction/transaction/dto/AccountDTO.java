package com.transaction.transaction.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
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
public class AccountDTO {

	@JsonProperty("account_id")
	private Long id;

	@JsonProperty("document_number")
	@NotNull(message = "O número do documento não pode ser nulo")
	@NotEmpty(message = "O número do documento não pode ser não vazio")
	private String documentNumber;

	@JsonProperty("available_credit_limit")
	@NotNull(message = "O campo referente ao credito disponivel na conta não pode ser nulo")
	private BigDecimal availableCreditLimit;
}
