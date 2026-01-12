package com.ytseries.sms.dao.repo;

import com.ytseries.sms.entity.Student;
import com.ytseries.sms.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

//  JPQl (Default)
//  Native (Mysql)
    @Query(value = "SELECT * FROM Student limit ?1 offset ?2", nativeQuery = true)
    List<Student> findAllInPagination (int pageSize, int offset);

    Page<Student> findAllByStatus(Status status, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhoneNo(String phoneNO);

    @Query("SELECT s FROM Student s WHERE " +
            "( s.firstName LIKE :search OR s.lastName LIKE :search OR s.email LIKE :search ) " +
            "AND s.status = :status")
    Page<Student> findAllByFirstNameContainsOrLastNameContainsOrEmailContainsAndStatus (@Param("search") String SearchKeyword,
                                                                                        @Param("status") Status status,
                                                                                        Pageable pageable);
}
