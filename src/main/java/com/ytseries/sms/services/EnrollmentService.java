package com.ytseries.sms.services;

import com.ytseries.sms.dao.CourseDao;
import com.ytseries.sms.dao.CourseEnrollmentDao;
import com.ytseries.sms.dao.StudentDao;
import com.ytseries.sms.dto.EnrollmentDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Enrollment;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.MaxLimitExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResource;
import com.ytseries.sms.util.APIMessage;
import com.ytseries.sms.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnrollmentService {

    @Autowired
    private CourseEnrollmentDao courseEnrollmentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudentDao studentDao;

    // in momery -> no exc -> save
    @Transactional(rollbackOn = Exception.class)
    public ResponseModel insertEnrollment(EnrollmentDTO dto) {
        // 1. is the course ID is valid
        // 2. is is this course reach max limit
        // 3. is the Student id is valid
        // 4. exist by courseId and StudentId where courseId = x and student = x!

        Course course = courseDao.findById(dto.getCourseId());
        if (course == null) {
            throw new NotFoundExceptionResource(APIMessage.COURSE_NOT_FOUND);
        } else if (course.getCurrentEnrollment() == course.getMaxEnrollment()) {
            throw new MaxLimitExceptionResource(APIMessage.MAX_LIMIT_REACHED);
        }

        Student student = studentDao.findById(dto.getStudentId());
        if (student == null) {
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
        }

        boolean studentAlreadyEnrolled = courseEnrollmentDao.existsByCourseIdAndStudentId(course.getCourseId(),
                student.getStudentId());
        if (studentAlreadyEnrolled) {
            throw new DuplicateExceptionResource(APIMessage.STUDENT_ALREADY_ENROLLED);
        }

        // DB
        // 1. course -> currentEnrollmentCout++ -> record will update
        // 2. Entrollment table -> insert new record

        course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
        // update the course
        courseDao.save(course); // sucess

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        // Insert new Enrollment Record
        enrollment = courseEnrollmentDao.save(enrollment);

        return ResponseModel.created(
                APIMessage.ENROLLMENT_SUCCESS,
                EnrollmentDTO.fromEnrollment(enrollment));
    }

    public ResponseModel getAllEnrollment(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Enrollment> enrollments = courseEnrollmentDao.findAll(pageable);

        List<EnrollmentDTO> enrollmentDTOs = enrollments.getContent().stream()
                .map(EnrollmentDTO::fromEnrollment)
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("data", enrollmentDTOs);
        result.put("pageResult", Utils.preparePageResult(enrollments));

        return ResponseModel.success(APIMessage.ENROLLMENT_FATCH_SUCCESS, result);
    }

    public ResponseModel getEnrollmentById(String enrollmentId) {
        Enrollment enrollment = courseEnrollmentDao.findById(enrollmentId);

        if (enrollment == null) {
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }

        return ResponseModel.success(
                APIMessage.ENROLLMENT_FATCH_SUCCESS,
                EnrollmentDTO.fromEnrollment(enrollment));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseModel deleteEnrollmentById(String enrollmentId) {
        Enrollment enrollment = courseEnrollmentDao.findById(enrollmentId);

        if (enrollment == null) {
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }

        // Decrease the current enrollment count in the course
        Course course = enrollment.getCourse();
        course.setCurrentEnrollment(course.getCurrentEnrollment() - 1);
        courseDao.save(course);

        // Delete the enrollment
        courseEnrollmentDao.deletedById(enrollmentId);

        return ResponseModel.success(
                APIMessage.ENROLLMENT_DELETE_SUCCESS,
                null);
    }

}
