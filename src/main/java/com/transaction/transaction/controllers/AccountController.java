package com.transaction.transaction.controllers;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.transaction.dto.AccountDTO;
import com.transaction.transaction.services.AccountService;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

	private AccountService accountService;

	@PostMapping
	private AccountDTO create(@Valid @RequestBody AccountDTO accountDTO) {
		return accountService.create( accountDTO );
	}

	@GetMapping("/{accountId}")
	private AccountDTO find(@PathVariable( "accountId" ) Long accountId) {
		return accountService.find( accountId );
	}
}
