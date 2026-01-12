package com.ytseries.sms.dao;

import com.ytseries.sms.dao.repo.InsturctorRepo;
import com.ytseries.sms.entity.Instructor;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class InstructorDao extends BasicCURD<Instructor, String> {

    private InsturctorRepo insturctorRepo;

    @Autowired
    public InstructorDao(InsturctorRepo insturctorRepo) {
        super(insturctorRepo);
        this.insturctorRepo = insturctorRepo;
    }

    public boolean existInstructorByEmail(String email) {
        return insturctorRepo.existsByEmail(email);
    }

    public boolean existInstructorByPhoneNo(String phoneNo) {
        return insturctorRepo.existsByPhoneNo(phoneNo);
    }

    public boolean existInstructorByEmailExcludingId(String email, String instructorId) {
        return insturctorRepo.existsByEmailAndInstructorIdNot(email, instructorId);
    }

    public boolean existInstructorByPhoneNoExcludingId(String phoneNo, String instructorId) {
        return insturctorRepo.existsByPhoneNoAndInstructorIdNot(phoneNo, instructorId);
    }

    public Page<Instructor> findAllByStatus(Status status, Pageable pageable) {
        return insturctorRepo.findAllByStatus(status, pageable);
    }

    public Page<Instructor> searchInstructorByStatus(String searchKeyword, Status status, Pageable pageable) {
        return insturctorRepo.searchInstructorByStatus("%" + searchKeyword + "%", status, pageable);
    }
}
