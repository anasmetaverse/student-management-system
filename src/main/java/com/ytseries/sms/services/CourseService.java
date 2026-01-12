package com.ytseries.sms.services;

import com.ytseries.sms.dao.CourseDao;
import com.ytseries.sms.dao.InstructorDao;
import com.ytseries.sms.dto.CourseDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Instructor;
import com.ytseries.sms.entity.enums.Category;
import com.ytseries.sms.entity.enums.Status;
import com.ytseries.sms.exception.NotFoundExceptionResource;
import com.ytseries.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private InstructorDao instructorDao;

//   AOP
    public ResponseModel insertCourse(CourseDTO courseDTO) {
//        log.info("insertCourse Method Executed {}", LocalDateTime.now());
        Instructor instructor = instructorDao.findById(courseDTO.getInstructorId());
        if(instructor == null){
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null
            );
        }

        boolean courseNameExist = courseDao.existByName(courseDTO.getCourseName());
        if(courseNameExist){
            return ResponseModel.conflict(
                    APIMessage.INSTRUCTOR_NAME_EXISTS,
                    null
            );
        }

        log.debug("All Validation Done Ready to Insert new Course");
        Course course = courseDTO.toEntity(instructor);
        course = courseDao.save(course);

//        log.info("insertCourse Method Execution END Preparing Response {}", LocalDateTime.now());
        return ResponseModel.created(
                APIMessage.COURSE_CREATED,
             CourseDTO.toDTO(course)
        );
    }

    public ResponseModel getAllCourses(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Course> courses = courseDao.findAllByStatus(Status.ACTIVE, pageable);

        List<CourseDTO> dtos = courses.getContent()
                .stream()
                .map(CourseDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", courses.getTotalElements());
        pageResult.put("pageCount", courses.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.COURSE_FOUND,
                result
        );
    }

    public ResponseModel getCourseById(String courseId) {
        Course course = courseDao.findById(courseId);
        if(course == null) {
            return ResponseModel.not_found(APIMessage.COURSE_NOT_FOUND, null);
        }

        return ResponseModel.success(
                APIMessage.COURSE_FOUND,
                CourseDTO.toDTO(course)
        );
    }

    public ResponseModel updateCourse(String id, CourseDTO dto) {
        Course course = courseDao.findById(id);
        if(course == null) {
            return ResponseModel.not_found(
                    APIMessage.COURSE_NOT_FOUND,
                    null);
        }

        Instructor instructor = null;
        if(dto.getInstructorId() != null) {
            instructor = instructorDao.findById(dto.getInstructorId());
            if(instructor == null) {
                return ResponseModel.not_found(
                        APIMessage.INSTRUCTOR_NOT_FOUND,
                        null
                );
            }
        }

        if(dto.getCourseName() != null && !dto.getCourseName().equals(course.getCourseName())) {
            boolean courseNameExist = courseDao.existByName(dto.getCourseName());
            if(courseNameExist) {
                return ResponseModel.conflict(
                        APIMessage.COURSE_NAME_EXISTS,
                        null
                );
            }
        }

        course = dto.toUpdateEntity(course, instructor);
        course = courseDao.save(course);

        return ResponseModel.success(
                APIMessage.COURSE_UPDATED,
                CourseDTO.toDTO(course)
        );
    }

    public ResponseModel deleteCourse(String id) {
        Course course = courseDao.findById(id);
        if(course == null) {
            return ResponseModel.not_found(
                    APIMessage.COURSE_NOT_FOUND,
                    null
            );
        }

        course.setStatus(Status.DELETED);
        courseDao.save(course);
        return ResponseModel.success(
                APIMessage.COURSE_DELETED,
                CourseDTO.toDTO(course)
        );
    }

    public ResponseModel filterByCategory(String name, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);

        try {

            Page<Course> courses = courseDao.findAllByCategory(Category.valueOf(name.toUpperCase()), pageable);

            List<CourseDTO> dtos = courses.getContent()
                    .stream()
                    .map(CourseDTO::toDTO)
                    .toList();

            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("pageSize", pageSize);
            pageResult.put("pageNo", pageNo);
            pageResult.put("totalRecords", courses.getTotalElements());
            pageResult.put("pageCount", courses.getTotalPages());

            Map<String, Object> result = new HashMap<>();
            result.put("data", dtos);
            result.put("pageResult", pageResult);

            return ResponseModel.success(
                    APIMessage.COURSE_FOUND,
                    result
            );

        } catch (IllegalArgumentException e) {
            throw new NotFoundExceptionResource(APIMessage.CATEGORY_NAME_NOTFOUND);
        } catch (Exception e) {
            throw e;
        }
    }
}
