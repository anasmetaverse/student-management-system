package com.ytseries.sms.services;

import com.ytseries.sms.dao.DepartmentDao;
import com.ytseries.sms.dao.InstructorDao;
import com.ytseries.sms.dto.DepartmentDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.entity.Department;
import com.ytseries.sms.entity.Instructor;
import com.ytseries.sms.entity.enums.Status;
import com.ytseries.sms.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private InstructorDao instructorDao;

    public ResponseModel insertDepartment(DepartmentDTO dto) {
        log.info("insertDepartment Method Executed");

        boolean departmentExist = departmentDao.existByDepartmentName(dto.getDepartmentName());
        if (departmentExist) {
            return ResponseModel.conflict(
                    APIMessage.DEPARTMENT_NAME_EXISTS,
                    null
            );
        }

        Instructor hod = null;
        if (dto.getHodId() != null) {
            hod = instructorDao.findById(dto.getHodId());
            if (hod == null) {
                return ResponseModel.not_found(
                        APIMessage.INSTRUCTOR_NOT_FOUND,
                        null
                );
            }
        }

        List<Instructor> instructors = new ArrayList<>();
        if (dto.getInstructorIds() != null && !dto.getInstructorIds().isEmpty()) {
            for (String instructorId : dto.getInstructorIds()) {
                Instructor instructor = instructorDao.findById(instructorId);
                if (instructor == null) {
                    return ResponseModel.not_found(
                            APIMessage.INSTRUCTOR_NOT_FOUND,
                            null
                    );
                }
                instructors.add(instructor);
            }
        }

        log.debug("All Validation Done Ready to Insert new Department");
        Department department = dto.toEntity(hod, instructors);
        department = departmentDao.save(department);

        log.info("insertDepartment Method Execution END Preparing Response");
        return ResponseModel.created(
                APIMessage.DEPARTMENT_CREATED,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel getAllDepartments(int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Department> departments = departmentDao.findAllByStatus(Status.ACTIVE, pageable);

        List<DepartmentDTO> dtos = departments.getContent()
                .stream()
                .map(DepartmentDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", departments.getTotalElements());
        pageResult.put("pageCount", departments.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                result
        );
    }

    public ResponseModel getDepartmentById(String departmentId) {
        Department department = departmentDao.findById(departmentId);
        if (department == null) {
            return ResponseModel.not_found(APIMessage.DEPARTMENT_NOT_FOUND, null);
        }

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel updateDepartment(String id, DepartmentDTO dto) {
        Department department = departmentDao.findById(id);
        if (department == null) {
            return ResponseModel.not_found(
                    APIMessage.DEPARTMENT_NOT_FOUND,
                    null);
        }

        if (dto.getDepartmentName() != null && !dto.getDepartmentName().equals(department.getDepartmentName())) {
            boolean departmentExist = departmentDao.existByDepartmentName(dto.getDepartmentName());
            if (departmentExist) {
                return ResponseModel.conflict(
                        APIMessage.DEPARTMENT_NAME_EXISTS,
                        null
                );
            }
        }

        Instructor hod = null;
        if (dto.getHodId() != null) {
            hod = instructorDao.findById(dto.getHodId());
            if (hod == null) {
                return ResponseModel.not_found(
                        APIMessage.INSTRUCTOR_NOT_FOUND,
                        null
                );
            }
        }

        List<Instructor> instructors = null;
        if (dto.getInstructorIds() != null && !dto.getInstructorIds().isEmpty()) {
            instructors = new ArrayList<>();
            for (String instructorId : dto.getInstructorIds()) {
                Instructor instructor = instructorDao.findById(instructorId);
                if (instructor == null) {
                    return ResponseModel.not_found(
                            APIMessage.INSTRUCTOR_NOT_FOUND,
                            null
                    );
                }
                instructors.add(instructor);
            }
        }

        department = dto.toUpdateEntity(department, hod, instructors);
        department = departmentDao.save(department);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_UPDATED,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel deleteDepartment(String id) {
        Department department = departmentDao.findById(id);
        if (department == null) {
            return ResponseModel.not_found(
                    APIMessage.DEPARTMENT_NOT_FOUND,
                    null
            );
        }

        department.setStatus(Status.DELETED);
        departmentDao.save(department);
        return ResponseModel.success(
                APIMessage.DEPARTMENT_DELETED,
                DepartmentDTO.toDTO(department)
        );
    }

    public ResponseModel searchDepartment(String searchKeyword, int pageSize, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Department> departments = departmentDao.searchDepartmentByStatus(searchKeyword, Status.ACTIVE, pageable);

        List<DepartmentDTO> dtos = departments.getContent()
                .stream()
                .map(DepartmentDTO::toDTO)
                .toList();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords", departments.getTotalElements());
        pageResult.put("pageCount", departments.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtos);
        result.put("pageResult", pageResult);

        return ResponseModel.success(
                APIMessage.DEPARTMENT_FOUND,
                result
        );
    }
}

