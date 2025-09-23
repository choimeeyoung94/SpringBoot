package org.shark.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SampleApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(stringRedisTemplate);
	}

}
