package com.ytseries.sms.controller;

import com.ytseries.sms.dto.CourseDTO;
import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

//  FATAL
//  ERROR
//  WARN
//  INFO
//  DEBUG
//  TRACE

    @PostMapping("/insert")
    public ResponseModel insertCourse(@RequestBody CourseDTO courseDTO) {
        log.info("insertCourse API Triggered");
        return courseService.insertCourse(courseDTO);
    }

    @GetMapping("/get-all")
    public ResponseModel getAllCourses(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return courseService.getAllCourses(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    public ResponseModel getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateCourse(@PathVariable String id, @RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(id, courseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteCourse(@PathVariable String id) {
        return courseService.deleteCourse(id);
    }

    @GetMapping("/by/category/{name}")
    public ResponseModel getCourseByCategory(@PathVariable String name,
                                             @RequestParam(required = false, defaultValue = "1") int pageNo,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return courseService.filterByCategory(name, pageSize, pageNo);
    }
}
