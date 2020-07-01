package com.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	List<Wallet> findByName(String name);

	Wallet findById(long id);
}