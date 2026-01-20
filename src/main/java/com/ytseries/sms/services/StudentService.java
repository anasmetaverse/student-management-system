package com.ytseries.sms.services;

import com.ytseries.sms.dao.StudentDao;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.dto.StudentDTO;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.entity.enums.Status;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResource;
import com.ytseries.sms.util.APIMessage;
import com.ytseries.sms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public ResponseModel saveTheStudent(StudentDTO dto) {
            boolean studentExist = studentDao.existStudentByEmail(dto.getEmail());
            if (studentExist) {
                throw new DuplicateExceptionResource(APIMessage.STUDENT_ALREADY_PRESENT.formatted("Email"));
            }

            studentExist = studentDao.existStudentByPhoneNo(dto.getPhoneNo());
            if (studentExist) {
                throw new DuplicateExceptionResource(APIMessage.STUDENT_ALREADY_PRESENT.formatted("PhoneNo"));
            }

            Student student = dto.toEntity();
            student = studentDao.save(student);
            return ResponseModel.created(
                    APIMessage.STUDENT_CREATED,
                    StudentDTO.toDto(student)
            );
    }

//  Admin Use
    public ResponseModel getAllStudents(int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Student> students = studentDao.findAllByStatus(Status.ACTIVE, pageable);

        List<StudentDTO> dtos = students.getContent()
                .stream()
                .map(StudentDTO::toDto)
                .toList();


        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", Utils.preparePageResult(students));

        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                result
        );
    }

    public ResponseModel getStudentById(String studentId) {
//        findById -> Optinal<?>
        Student student = studentDao.findById(studentId);
//       if record found and if Not
        if(student == null) {
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND.formatted(studentId));
        }

        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                StudentDTO.toDto(student)
        );
    }

    public ResponseModel updateTheStudent(String id, StudentDTO dto) {
        Student student = studentDao.findById(id);
        if(student == null) {
            return ResponseModel.not_found(
                    APIMessage.STUDENT_NOT_FOUND,
                    null);
        }

        boolean studentExist = studentDao.existStudentByEmail(dto.getEmail());
        if (studentExist){
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted( "Email"),
                    null
            );
        }

        studentExist = studentDao.existStudentByPhoneNo(dto.getPhoneNo());
        if (studentExist){
            return ResponseModel.conflict(
                    APIMessage.STUDENT_ALREADY_PRESENT.formatted("PhoneNo"),
                    null
            );
        }

        student = dto.toUpdateEntity(student);
        student = studentDao.save(student);

        return ResponseModel.success(
                APIMessage.STUDENT_UPDATED,
                StudentDTO.toDto(student)
        );
    }

//  Soft Delete
    public ResponseModel deleteStudent(String id) {
        Student student = studentDao.findById(id);
        if(student == null) {
            return ResponseModel.not_found(
                    APIMessage.STUDENT_NOT_FOUND,
                    null
            );
        }

        student.setStatus(Status.DELETED);
        studentDao.save(student);
        return ResponseModel.success(
                APIMessage.STUDENT_DELETED,
                StudentDTO.toDto(student)
        );
    }

//  Searching
    public ResponseModel searchStudent(String searchKeyWord, int pageSize, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Student> students = studentDao.searchStudentByStatus(searchKeyWord, Status.ACTIVE, pageable);

        List<StudentDTO> dtos = students.getContent()
                .stream()
                .map(StudentDTO::toDto)
                .toList();


        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", students.getTotalElements());
        pageResult.put("pageCount", students.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.STUDENT_FOUND,
                result
        );
    }
}
