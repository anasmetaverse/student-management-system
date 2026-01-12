package com.ytseries.sms.services;

import com.ytseries.sms.dao.InstructorDao;
import com.ytseries.sms.dto.InstructorDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.entity.Instructor;
import com.ytseries.sms.entity.enums.Status;
import com.ytseries.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InstructorService {

    @Autowired
    private InstructorDao instructorDao;

    public ResponseModel insertInstructor(InstructorDTO dto) {
        log.info("insertInstructor Method Executed");
        
        boolean instructorExist = instructorDao.existInstructorByEmail(dto.getEmail());
        if (instructorExist) {
            return ResponseModel.conflict(
                    APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("Email"),
                    null
            );
        }

        instructorExist = instructorDao.existInstructorByPhoneNo(dto.getPhoneNo());
        if (instructorExist) {
            return ResponseModel.conflict(
                    APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("PhoneNo"),
                    null
            );
        }

        log.debug("All Validation Done Ready to Insert new Instructor");
        Instructor instructor = dto.toEntity();
        instructor = instructorDao.save(instructor);

        log.info("insertInstructor Method Execution END Preparing Response");
        return ResponseModel.created(
                APIMessage.INSTRUCTOR_CREATED,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel getAllInstructors(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Instructor> instructors = instructorDao.findAllByStatus(Status.ACTIVE, pageable);

        List<InstructorDTO> dtos = instructors.getContent()
                .stream()
                .map(InstructorDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", instructors.getTotalElements());
        pageResult.put("pageCount", instructors.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                result
        );
    }

    public ResponseModel getInstructorById(String instructorId) {
        Instructor instructor = instructorDao.findById(instructorId);
        if (instructor == null) {
            return ResponseModel.not_found(APIMessage.INSTRUCTOR_NOT_FOUND, null);
        }

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel updateInstructor(String id, InstructorDTO dto) {
        Instructor instructor = instructorDao.findById(id);
        if (instructor == null) {
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null);
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(instructor.getEmail())) {
            boolean instructorExist = instructorDao.existInstructorByEmailExcludingId(dto.getEmail(), id);
            if (instructorExist) {
                return ResponseModel.conflict(
                        APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("Email"),
                        null
                );
            }
        }

        if (dto.getPhoneNo() != null && !dto.getPhoneNo().equals(instructor.getPhoneNo())) {
            boolean instructorExist = instructorDao.existInstructorByPhoneNoExcludingId(dto.getPhoneNo(), id);
            if (instructorExist) {
                return ResponseModel.conflict(
                        APIMessage.INSTRUCTOR_ALREADY_PRESENT.formatted("PhoneNo"),
                        null
                );
            }
        }

        instructor = dto.toUpdateEntity(instructor);
        instructor = instructorDao.save(instructor);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_UPDATED,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel deleteInstructor(String id) {
        Instructor instructor = instructorDao.findById(id);
        if (instructor == null) {
            return ResponseModel.not_found(
                    APIMessage.INSTRUCTOR_NOT_FOUND,
                    null
            );
        }

        instructor.setStatus(Status.DELETED);
        instructorDao.save(instructor);
        return ResponseModel.success(
                APIMessage.INSTRUCTOR_DELETED,
                InstructorDTO.toDTO(instructor)
        );
    }

    public ResponseModel searchInstructor(String searchKeyword, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Instructor> instructors = instructorDao.searchInstructorByStatus(searchKeyword, Status.ACTIVE, pageable);

        List<InstructorDTO> dtos = instructors.getContent()
                .stream()
                .map(InstructorDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", instructors.getTotalElements());
        pageResult.put("pageCount", instructors.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.INSTRUCTOR_FOUND,
                result
        );
    }
}

