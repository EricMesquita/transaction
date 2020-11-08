package com.transaction.transaction.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.transaction.transaction.enumarations.OperationType;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_transaction", sequenceName = "seq_transaction", allocationSize = 1)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private OperationType operationType;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	private BigDecimal amount;
}
