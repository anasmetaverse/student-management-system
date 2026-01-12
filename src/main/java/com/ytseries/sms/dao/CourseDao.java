package com.ytseries.sms.dao;

import com.ytseries.sms.dao.repo.CourseRepo;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.enums.Category;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CourseDao extends BasicCURD<Course, String> {

    private CourseRepo courseRepo;

    @Autowired
    public CourseDao(CourseRepo courseRepo) {
        super(courseRepo);
        this.courseRepo = courseRepo;
    }

    public boolean existByName (String name) {
        return courseRepo.existsByCourseName(name);
    }

    public Page<Course> findAllByStatus(Status status, Pageable pageable) {
        return courseRepo.findAllByStatus(status, pageable);
    }

    public Page<Course> findAllByCategory(Category category, Pageable pageable) {
        return courseRepo.findAllByCategory(category, pageable);
    }
}
