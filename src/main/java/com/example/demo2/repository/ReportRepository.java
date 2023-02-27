package com.example.demo2.repository;

import com.example.demo2.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "select * from reports r where r.user_id= :uid",nativeQuery = true)
    List<Report> findAllByUserId(@Param("uid") Integer uid);
}
