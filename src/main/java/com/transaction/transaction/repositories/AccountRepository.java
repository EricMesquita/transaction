package com.transaction.transaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transaction.transaction.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

	@Query("select a from Account a where a.document = :documentNumber")
	Account findByDocument(@Param("documentNumber") String documentNumber);
}
