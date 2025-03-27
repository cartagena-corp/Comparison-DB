package com.cartagenacorp.Comparison_DB;

import com.cartagenacorp.Comparison_DB.service.PerformanceTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComparisonDbApplicationTests {

	@Autowired
	private PerformanceTestService performanceTestService;

	@Test
	public void dbPerformanceTest() {
		// Insert n records
		performanceTestService.insertMassData(100000);

		// Execute complex query
		performanceTestService.executeComplexQuery();
	}

}
