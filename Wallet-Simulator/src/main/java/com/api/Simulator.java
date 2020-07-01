package com.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.api.model.Wallet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Simulator {
	
	 private final WalletRepository repository;

	private final String BASE_URL = "https://min-api.cryptocompare.com/data/";
	
	private JsonParser jsonParser = new JsonParser();
	
	private static final Logger log = LoggerFactory.getLogger(Simulator.class);
	
	
	Simulator(WalletRepository repository) {
	    this.repository = repository;
	  }
	
	
/**
 * Get all the Wallets
 * @return the wallets available
 */
  @GetMapping("/wallets")
  List<Wallet> all() {
    return repository.findAll();
  }
  

  /**
   * Create a new wallet
   * @param newWallet
   * @return new wallet
   */
  @PostMapping("/wallets")
  Wallet newWallet(@RequestBody Wallet newWallet) {
    return repository.save(newWallet);
  }


  /**
   * Get the wallet by ID
   * @param id
   * @return the wallet for the ID
   * @throws Exception
   */
  @GetMapping("/wallets/{id}")
  Wallet one(@PathVariable Long id) throws Exception {

    return repository.findById(id)
      .orElseThrow(() -> new Exception());
  }

  
  /**
   * Update the wallet
   * @param newWallet
   * @param id
   * @return new wallet
   */
  @PutMapping("/wallets/{id}")
  Wallet replaceWallet(@RequestBody Wallet newWallet, @PathVariable Long id) {

    return repository.findById(id)
      .map(wallet -> {
    	  wallet.setName(newWallet.getName());
    	  wallet.setAddress(newWallet.getAddress());
    	  wallet.setCoinType(newWallet.getCoinType());
        return repository.save(wallet);
      })
      .orElseGet(() -> {
    	  newWallet.setId(id);
        return repository.save(newWallet);
      });
  }
  

  /**
   * Delete the wallet
   * @param id
   */
  @DeleteMapping("/wallets/{id}")
  void deleteWallet(@PathVariable Long id) {
    repository.deleteById(id);
  }
  
  
  /**
   * Transfer the amount between two wallets
   * @param frmId
   * @param toId
   * @param amount
   * @return wallet with new amount
   * @throws Exception
   */
  @PutMapping("/transfer/{frmId}/{toId}/{amount}")
  Wallet transfer(@PathVariable Long frmId, @PathVariable Long toId, @PathVariable double amount) throws Exception {
	  Optional<Wallet> wallet1 = repository.findById(frmId);
	  Optional<Wallet> wallet2 = repository.findById(toId);
	    
	  if(wallet1.get().getBalance()> amount) {
		  wallet1.get().setBalance(wallet1.get().getBalance()-amount);
		  wallet2.get().setBalance(wallet2.get().getBalance()+amount);
		  repository.save(wallet1.get());
		  repository.save(wallet2.get());
	  }else {
		  throw new Exception();
	  }
	return (wallet2.get());
  }
  
  
  /**
   * Buy coins for the wallet
   * @param restTemplate
   * @param id
   * @param noOfCoins
   * @return wallet with new number of coins
   * @throws Exception
   */
  @PutMapping("/buyCoins/{id}//{noOfCoins}")
  Wallet buyCoins(RestTemplate restTemplate, @PathVariable Long id, @PathVariable Integer noOfCoins) throws Exception {
	  Optional<Wallet> wallet = repository.findById(id);
	  String coinType = wallet.get().getCoinType();
	  StringBuilder sb = new StringBuilder();
      sb.append(this.BASE_URL)
              .append("price?fsym=").append(coinType).append("&tsyms=USD");
	  String result = restTemplate.getForObject(sb.toString(), String.class);
	  JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
      log.info(jsonObject.toString());
      double coinPrice = jsonObject.get("USD").getAsDouble();
	  if(wallet.get().getBalance()> coinPrice) {
		  wallet.get().setBalance(wallet.get().getBalance()-(noOfCoins*coinPrice));
		  wallet.get().setNoOfCoins(wallet.get().getNoOfCoins()+noOfCoins);
		  repository.save(wallet.get());
	  }else {
		  throw new Exception();
	  }
      return wallet.get(); 
      	}
  
  
  /**
   * List all the coins available
   * @param restTemplate
   */
  @GetMapping("/allCoins")
  void allCoins(RestTemplate restTemplate)  {

	  String requestUrl = BASE_URL + "all/coinlist";
	  String result = restTemplate.getForObject(requestUrl, String.class);
	  JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
      log.info(jsonObject.toString()); 
      	}
  
  
  /**
   * List the price for the symbol
   * @param restTemplate
   * @param fsym
   * @param tsym
   * @return
   */
  @GetMapping("/price/{fsym}/{tsym}")
  String price(RestTemplate restTemplate, @PathVariable String fsym, @PathVariable String tsym) {
	  StringBuilder sb = new StringBuilder();
      sb.append(this.BASE_URL)
              .append("price?fsym=").append(fsym).append("&tsyms=").append(tsym);
	  String result = restTemplate.getForObject(sb.toString(), String.class);
	  JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
      log.info(jsonObject.toString());
      return jsonObject.toString();
      	}
  
  
  /**
   * List the price for the multiple symbol
   * @param restTemplate
   * @param fsym
   * @param tsym
   * @return
   */
  @GetMapping("/priceMulti/{fsym}/{tsym}")
  String priceMulti(RestTemplate restTemplate, @PathVariable String fsym, @PathVariable String tsym) {
	  StringBuilder sb = new StringBuilder();
      sb.append(this.BASE_URL)
              .append("pricemulti?fsyms=").append(fsym).append("&tsyms=").append(tsym);
	  String result = restTemplate.getForObject(sb.toString(), String.class);
	  JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
      log.info(jsonObject.toString());
      return jsonObject.toString();
      	}
}
