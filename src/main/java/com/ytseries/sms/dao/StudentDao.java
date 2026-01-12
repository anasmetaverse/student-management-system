package com.ytseries.sms.dao;

import com.ytseries.sms.dao.repo.StudentRepo;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StudentDao extends BasicCURD<Student, String> {

    private StudentRepo studentRepo;

    @Autowired
    public StudentDao(StudentRepo studentRepo) {
        super(studentRepo);
        this.studentRepo = studentRepo;
    }

    public List<Student> findAllinPagination (int pageSize, int offset) {
        return studentRepo.findAllInPagination(pageSize, offset);
    }

    public Page<Student> findAllByStatus (Status status, Pageable pageable) {
        return studentRepo.findAllByStatus(status, pageable);
    }

    public Long countAllStudents() {
        return studentRepo.count();
    }

    public boolean existStudentByEmail (String email) {
        return studentRepo.existsByEmail(email);
    }

    public boolean existStudentByPhoneNo (String phoneNo) {
        return studentRepo.existsByPhoneNo(phoneNo);
    }

    public Page<Student> searchStudentByStatus (String searchKeyword, Status status, Pageable pageable) {
        return studentRepo.findAllByFirstNameContainsOrLastNameContainsOrEmailContainsAndStatus(
                "%"+searchKeyword+"%",
                status,
                pageable);
    }
}
