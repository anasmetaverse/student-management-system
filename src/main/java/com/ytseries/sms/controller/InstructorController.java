package com.ytseries.sms.controller;

import com.ytseries.sms.dto.InstructorDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.services.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/insert")
    public ResponseModel insertInstructor(@RequestBody InstructorDTO dto) {
        log.info("insertInstructor API Triggered");
        return instructorService.insertInstructor(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllInstructors(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return instructorService.getAllInstructors(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    public ResponseModel getInstructorById(@PathVariable String id) {
        return instructorService.getInstructorById(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateInstructor(@PathVariable String id, @RequestBody InstructorDTO dto) {
        return instructorService.updateInstructor(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteInstructor(@PathVariable String id) {
        return instructorService.deleteInstructor(id);
    }

    @GetMapping("/search")
    public ResponseModel searchInstructor(@RequestParam String searchKeyword,
                                          @RequestParam(required = false, defaultValue = "1") int pageNo,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return instructorService.searchInstructor(searchKeyword, pageSize, pageNo);
    }
}

