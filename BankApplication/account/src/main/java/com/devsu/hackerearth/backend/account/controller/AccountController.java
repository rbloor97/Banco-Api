package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@SuppressWarnings("null")
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@SuppressWarnings("null")
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		try{
			AccountDto account = accountService.getById(id);
			if(account != null){
				return ResponseEntity.ok(account);
			}else{
				return ResponseEntity.notFound().build();
			}
			
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@SuppressWarnings("null")
	@PostMapping
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(accountDto));
	}

	@SuppressWarnings("null")
	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		try{
			accountDto.setId(id);
			AccountDto account = accountService.update(accountDto);
			if(account != null){
				return ResponseEntity.ok(account);
			}else{
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@SuppressWarnings("null")
	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		try{

			AccountDto account = accountService.partialUpdate(id, partialAccountDto);
			if(account != null){
				return ResponseEntity.ok(account);
			}else{
				return ResponseEntity.notFound().build();
			}
			
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try{
			if(id != null){
				accountService.deleteById(id);
				return ResponseEntity.noContent().build();
			}else{
				return ResponseEntity.notFound().build(); 
			}
			
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
		
	}
}
