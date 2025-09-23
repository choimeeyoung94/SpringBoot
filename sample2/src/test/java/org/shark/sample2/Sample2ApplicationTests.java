package org.shark.sample2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class Sample2ApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(stringRedisTemplate);
	}

}
