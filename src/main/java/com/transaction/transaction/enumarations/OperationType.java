package com.transaction.transaction.enumarations;

import static com.transaction.transaction.enumarations.TypeValue.NEGATIVE;
import static com.transaction.transaction.enumarations.TypeValue.POSITIVE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationType {

	PAID_CASH( 1L, "COMPRA A VISTA", NEGATIVE ),
	PAID_INSTALLMENT( 2L, "COMPRA PARCELADA", NEGATIVE ),
	WITHDRAW( 3L, "SAQUE", NEGATIVE ),
	PAYMENT( 4L, "PAGAMENTO", POSITIVE );

	private Long code;
	private String description;
	private TypeValue typeValue;

}
