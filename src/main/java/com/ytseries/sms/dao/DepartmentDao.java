package com.ytseries.sms.dao;

import com.ytseries.sms.dao.repo.DepartmentRepo;
import com.ytseries.sms.entity.Department;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDao extends BasicCURD<Department, String> {

    private DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentDao(DepartmentRepo departmentRepo) {
        super(departmentRepo);
        this.departmentRepo = departmentRepo;
    }

    public boolean existByDepartmentName(String departmentName) {
        return departmentRepo.existsByDepartmentName(departmentName);
    }

    public Page<Department> findAllByStatus(Status status, Pageable pageable) {
        return departmentRepo.findAllByStatus(status, pageable);
    }

    public Page<Department> searchDepartmentByStatus(String searchKeyword, Status status, Pageable pageable) {
        return departmentRepo.searchDepartmentByStatus("%" + searchKeyword + "%", status, pageable);
    }
}

