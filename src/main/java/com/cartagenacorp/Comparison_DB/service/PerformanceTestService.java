package com.cartagenacorp.Comparison_DB.service;

import com.cartagenacorp.Comparison_DB.model.User;
import com.cartagenacorp.Comparison_DB.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PerformanceTestService {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public long insertMassData(int quantityRecords) {
        List<User> users = new ArrayList<>(quantityRecords);
        Random random = new Random();

        for (int i = 0; i < quantityRecords; i++) {
            String name = generateRandomName();
            String email = name + "@example.com";
            int age = random.nextInt(63) + 18; // Entre 18 y 80

            User user = new User(name, email, age);
            users.add(user);
        }

        Instant start = Instant.now();

        userRepository.saveAll(users);

        Instant end = Instant.now();
        long insertExecutionTime = Duration.between(start, end).toMillis();

        logger.info("Insertion time of {} records: {} ms", quantityRecords, insertExecutionTime);
        return insertExecutionTime;
    }

    @Transactional(readOnly = true)
    public void executeComplexQuery() {

        Instant start = Instant.now();

        List<Object[]> results = userRepository.getStatsAge();

        Instant end = Instant.now();
        Duration queryExecutionTime = Duration.between(start, end);

        if (results == null || results.isEmpty()) {
            logger.error("No results were found in the statistics query");
            return;
        }

        Object[] result = results.get(0);

        long totalUsers = result[0] != null ?
                ((Number) result[0]).longValue() : 0L;

        double averageAge = result[1] != null ?
                ((Number) result[1]).doubleValue() : 0.0;

        int minimumAge = result[2] != null ?
                ((Number) result[2]).intValue() : 0;

        int maximumAge = result[3] != null ?
                ((Number) result[3]).intValue() : 0;

        logger.info("Query Execution Time: {} ms", queryExecutionTime.toMillis());
        logger.info("=== Complex Query Results ===");
        logger.info("Total users: {}", totalUsers);
        DecimalFormat df = new DecimalFormat("#.##");
        logger.info("Average age: {}", df.format(averageAge));
        logger.info("Minimum age: {}", minimumAge);
        logger.info("Maximum age: {}", maximumAge);
    }

    private String generateRandomName() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
