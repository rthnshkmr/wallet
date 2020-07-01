package com.api;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.model.Wallet;

@Configuration
public class LocalDataBase {
	
	private static final Logger log = LoggerFactory.getLogger(LocalDataBase.class);

	// preload wallets
	  @Bean
	  CommandLineRunner initDatabase(WalletRepository repository) {
	    return args -> {
	      log.info("Preloading " + repository.save(new Wallet("Wallet1", "US",2,2345.34, "BTC")));
	      log.info("Preloading " + repository.save(new Wallet("Wallet2", "Canada",4,345.45,"ETH")));
	      log.info("Preloading " + repository.save(new Wallet("Wallet3", "UK",1,646.23,"DASH")));
	    };
	  }
	}
