package com.ytseries.sms.dao.repo;

import com.ytseries.sms.entity.Department;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, String> {
    boolean existsByDepartmentName(String departmentName);
    Page<Department> findAllByStatus(Status status, Pageable pageable);

    @Query("SELECT d FROM Department d WHERE " +
            "d.departmentName LIKE :search " +
            "AND d.status = :status")
    Page<Department> searchDepartmentByStatus(@Param("search") String searchKeyword,
                                               @Param("status") Status status,
                                               Pageable pageable);
}

