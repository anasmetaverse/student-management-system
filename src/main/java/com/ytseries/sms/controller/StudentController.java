package com.ytseries.sms.controller;

import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.dto.StudentDTO;
import com.ytseries.sms.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sms/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/insert")
    public ResponseModel insertStudent(@RequestBody @Valid StudentDTO dto) {
        return studentService.saveTheStudent(dto);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllStudents(@RequestParam(required = false ,defaultValue = "1") int pageNo,
                                              @RequestParam(required = false, defaultValue =  "10") int pageSize) {
        return studentService.getAllStudents(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    public ResponseModel getStudentById (@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateStudent(@PathVariable String id, @RequestBody StudentDTO dto) {
        return studentService.updateTheStudent(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/search")
    public ResponseModel searchStudent(@RequestParam String searchKeyword,
                                       @RequestParam(required = false ,defaultValue = "1") int pageNo,
                                       @RequestParam(required = false, defaultValue =  "10") int pageSize) {
        return studentService.searchStudent(searchKeyword, pageSize, pageNo);
    }
}
