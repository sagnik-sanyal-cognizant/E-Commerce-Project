package com.cts.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
    public void main() {
        // Test the main method to ensure it runs without exceptions
        String[] args = {};
        BackendApplication.main(args);
    }

}
