package com.ytseries.sms.dao.repo;

import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Enrollment;
import com.ytseries.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, String> {

    boolean existsByCourse_CourseIdAndStudent_StudentId(String courseId, String studentId);
}
