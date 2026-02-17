package com.example.demo;

import com.example.demo.dao.TransactionRepository;
import com.example.demo.dao.WalletRepository;
import com.example.demo.model.Wallet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests {


	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WalletRepository walletRepo;
	@Autowired
	private TransactionRepository txrepo;

	@BeforeAll
	void setUp() {
		walletRepo.deleteAll();
		txrepo.deleteAll();

		Wallet w1 = new Wallet();
		w1.setBalance(1000);

		Wallet w2 = new Wallet();
		w2.setBalance(500);

		Wallet w3 = new Wallet();

		walletRepo.saveAll(List.of(w1, w2, w3));
	}

	@Test
	void createWallet() throws Exception {
		mockMvc.perform(post("/wallets")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")).andExpect(status().isCreated());

	}

	@Test
	void getWalletById() throws Exception {
		mockMvc.perform(get("/wallets/1")
						.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void testTransferWithIdempotency() throws Exception {
		String jsonPayload = """
            {
                "senderId": 1,
                "receiverId": 2,
                "amount": 200,
                "idempotencyKey": "unique-key-123",
                "type": "debit"
            }
            """;

		// First attempt: Should succeed (Status 200 or 201)
		mockMvc.perform(post("/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonPayload))
				.andExpect(status().isOk());


		mockMvc.perform(get("/wallets/1"))
				.andExpect(jsonPath("$.balance").value(800));


		mockMvc.perform(get("/wallets/2"))
				.andExpect(jsonPath("$.balance").value(700));


		mockMvc.perform(post("/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonPayload))
				.andExpect(status().isConflict());
	}

	@Test
	void testInsufficientBalance() throws Exception {
		String jsonPayload = """
            {
                "senderId": 3,
                "receiverId": 2,
                "amount": 200,
                "idempotencyKey": "unique-key-456",
                "type": "debit"
            }
            """;

		mockMvc.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPayload))
				.andExpect(status().isBadRequest());
	}

}
