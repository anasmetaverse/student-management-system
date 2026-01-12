package com.ytseries.sms.dao.repo;

import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.enums.Category;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepo extends JpaRepository<Course, String> {
    boolean existsByCourseName(String courseName);
    Page<Course> findAllByStatus(Status status, Pageable pageable);
    Page<Course> findAllByCategory(Category category, Pageable pageable);
}
