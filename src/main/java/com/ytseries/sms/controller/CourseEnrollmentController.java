package com.ytseries.sms.controller;

import com.ytseries.sms.dto.EnrollmentDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.services.EnrollmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/course/enrollment")
public class CourseEnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/insert")
    public ResponseModel insertEnrollment(@RequestBody @Valid EnrollmentDTO dto) {
        return enrollmentService.insertEnrollment(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllEnrollment(@RequestParam int pageNo, @RequestParam int pageSize) {
        System.out.println(pageNo + "-" + pageSize);
        return enrollmentService.getAllEnrollment(pageNo, pageSize);
    }

    @GetMapping("/get/{enrollmentId}")
    public ResponseModel getEnrollmentById(@PathVariable String enrollmentId) {
        return enrollmentService.getEnrollmentById(enrollmentId);
    }

    @DeleteMapping("/delete/{enrollmentId}")
    public ResponseModel deleteEnrollmentById(@PathVariable String enrollmentId) {
        return enrollmentService.deleteEnrollmentById(enrollmentId);
    }
}
