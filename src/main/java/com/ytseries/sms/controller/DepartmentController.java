package com.ytseries.sms.controller;

import com.ytseries.sms.dto.DepartmentDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/insert")
    public ResponseModel insertDepartment(@RequestBody DepartmentDTO dto) {
        log.info("insertDepartment API Triggered");
        return departmentService.insertDepartment(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllDepartments(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return departmentService.getAllDepartments(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    public ResponseModel getDepartmentById(@PathVariable String id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateDepartment(@PathVariable String id, @RequestBody DepartmentDTO dto) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteDepartment(@PathVariable String id) {
        return departmentService.deleteDepartment(id);
    }

    @GetMapping("/search")
    public ResponseModel searchDepartment(@RequestParam String searchKeyword,
                                         @RequestParam(required = false, defaultValue = "1") int pageNo,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return departmentService.searchDepartment(searchKeyword, pageSize, pageNo);
    }
}

