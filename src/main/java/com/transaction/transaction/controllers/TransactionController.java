package com.transaction.transaction.controllers;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.transaction.dto.TransactionDTO;
import com.transaction.transaction.services.TransactionService;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

	private TransactionService service;

	@PostMapping
	public void create(@Valid @RequestBody TransactionDTO dto) {
		service.create( dto );
	}
}
