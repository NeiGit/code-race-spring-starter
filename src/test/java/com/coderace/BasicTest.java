package com.coderace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Basic test")
public class BasicTest {

	@Test
	@DisplayName("Basic test | 2 + 2 should be 4")
	void basicTest() {
		assertEquals(4, 2 + 2);
	}

}
