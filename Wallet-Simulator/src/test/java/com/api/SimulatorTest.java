package com.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.model.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SimulatorTest {
	
	@Autowired                   
    private WalletRepository repository;
	
	private MockMvc mockMvc;
	
	 @Before
	   public void setUp() throws Exception{
		 
		 mockMvc = MockMvcBuilders.standaloneSetup(new Simulator(repository))
					.build();
	 }
	 
	@Test
	public void testGetWallets() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/wallets")
				.accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.*").isNotEmpty());
	}

	@Test
	public void testGetWalletById() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
			      .get("/wallets/{id}",2)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));
	}
	
	@Test
	public void testCreateWallet() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .post("/wallets")
	      .content(asJsonString(new Wallet("Wallet4", "US",2,2345.34, "BTC")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	@Test
	public void testUpdateWallet() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/wallets/{id}",2)
	      .content(asJsonString(new Wallet("Wallet2", "Canada",8,345.45,"ETH")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.noOfCoins").value(8));
	}
	
	@Test
	public void testDeleteWallet() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders.delete("/wallets/{id}", 1) )
	        .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testTransfer() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/transfer/{frmId}/{toId}/{amount}",2,3,10)
	      .content(asJsonString(new Wallet("Wallet3", "UK",1,10.3,"DASH")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(20.3));
	}
	
	@Test
	public void testBuyCoins() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .put("/buyCoins/{id}/{noOfCoins}",2,2)
	      .content(asJsonString(new Wallet("Wallet2", "Canada",1,20000.5,"ETH")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.noOfCoins").value(3));
	}
	
	@Test
	public void testGetAllCoins() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/allCoins")
				.accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPrice() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/price/{fsym}/{tsym}","ETH","BTC,USD,EUR")
				.accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPriceMulti() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/priceMulti/{fsym}/{tsym}","ETH","BTC,USD,EUR")
				.accept(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
