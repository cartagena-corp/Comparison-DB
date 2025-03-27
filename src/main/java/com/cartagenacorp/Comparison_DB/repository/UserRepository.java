package com.cartagenacorp.Comparison_DB.repository;

import com.cartagenacorp.Comparison_DB.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        @Query("SELECT " +
                "COUNT(u) as totalUsers, " +
                "COALESCE(AVG(u.age), 0) as averageAge, " +
                "COALESCE(MIN(u.age), 0) as minimumAge, " +
                "COALESCE(MAX(u.age), 0) as maximumAge " +
                "FROM User u " +
                "WHERE u.age BETWEEN 20 AND 60")
        List<Object[]> getStatsAge();
}
