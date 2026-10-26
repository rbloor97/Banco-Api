package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository,AccountRepository accountRepository) {
		this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
	}

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDto(
            transaction.getId(),
            transaction.getDate(), 
            transaction.getType(),
            transaction.getAmount(),
            transaction.getBalance(),
            transaction.getAccountId())).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        @SuppressWarnings("null")
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
		return new TransactionDto(
            transaction.getId(),
            transaction.getDate(), 
            transaction.getType(),
            transaction.getAmount(),
            transaction.getBalance(),
            transaction.getAccountId()
        );
    }

    @SuppressWarnings("null")
    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        TransactionDto lastTransaction = getLastByAccountId(transactionDto.getAccountId());
        double currentBalance;

        if(lastTransaction != null){
            currentBalance = lastTransaction.getBalance();
        }else {
            Account account = accountRepository.findById(transactionDto.getAccountId())
                            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
            currentBalance = account.getInitialAmount();
        }

        double newBalance = currentBalance +  transactionDto.getAmount();

        if(newBalance <0){
            throw new IllegalArgumentException("Saldo no disponible");
        }

        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate() != null ? transactionDto.getDate(): new Date());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(newBalance);
        transaction.setAccountId(transactionDto.getAccountId());

        Transaction saved = transactionRepository.save(transaction);
		return new TransactionDto(
            saved.getId(),
            saved.getDate(), 
            saved.getType(),
            saved.getAmount(),
            saved.getBalance(),
            saved.getAccountId()
        );
    }

    @SuppressWarnings("null")
    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(
        Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<Account> accounts = accountRepository.findAll().stream()
                                    .filter(acc -> acc.getClientId() != null && acc.getId().equals(clientId))
                                    .collect(Collectors.toList());
        List<Long> accountIds = accounts.stream().map(Account::getId).collect(Collectors.toList());
		
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountId() != null && accountIds.contains(transaction.getAccountId()))
                .filter(transaction -> transaction.getDate() != null && 
                !transaction.getDate().before(dateTransactionStart) &&
                !transaction.getDate().after(dateTransactionEnd))
                .map(transaction -> {
                    Account acc = accounts.stream().filter(a -> a.getId().equals(transaction.getAccountId())).findFirst().orElse(null);
                    return new BankStatementDto(
                            transaction.getDate(),
                            "",
                            acc !=null ? acc.getNumber(): "",
                            acc !=null ? acc.getType(): "",
                            acc !=null ? acc.getInitialAmount(): 0.0,
                            acc !=null ? acc.isActive(): true,
                            transaction.getType(),
                            transaction.getAmount(),
                            transaction.getBalance()
                );
                }).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        Transaction transaction = transactionRepository.findAll().stream()
            .filter(t -> t.getAccountId() != null && t.getAccountId().equals(accountId))
            .reduce((fisrt,second)->second)
            .orElse(null);
        if(transaction == null){
            return null;
        }
		return new TransactionDto(transaction.getId(),
        transaction.getDate(), 
        transaction.getType(),
        transaction.getAmount(),
        transaction.getBalance(),
        transaction.getAccountId()
        );
    }
    
}
