package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Instructor;
import com.ytseries.sms.entity.enums.Category;
import com.ytseries.sms.entity.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO {
    private String courseId;

    @NotBlank(message = "Course Name is Required")
    @Size(min = 3, message = "At least 3 char course name is required")
    private String courseName;

    @NotBlank(message = "Course Description is Required")
    @Size(min = 10, message = "Course description must be at least 10 characters")
    private String courseDescription;

    @Min(value = 1, message = "Duration must be at least 1")
    private long duration;

    private Status status;

    @NotNull(message = "Category is Required")
    private Category category;

    private LocalDate createdOn;

    @NotBlank(message = "Instructor ID is Required")
    private String instructorId;

    public Course toEntity(Instructor instructor) {
        return Course.builder()
                .courseName(this.courseName)
                .courseDescription(this.courseDescription)
                .duration(this.duration)
                .status(this.status)
                .category(this.category)
                .instructorId(instructor)
                .build();
    }

    public static CourseDTO toDTO(Course course) {
        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .category(course.getCategory())
                .status(course.getStatus())
                .duration(course.getDuration())
                .duration(course.getDuration())
                .instructorId(course.getInstructorId().getInstructorId())
                .createdOn(course.getCreatedOn())
                .build();
    }

    public Course toUpdateEntity(Course entity, Instructor instructor) {
        if(this.courseName != null) {
            entity.setCourseName(this.courseName);
        }
        if(this.courseDescription != null) {
            entity.setCourseDescription(this.courseDescription);
        }
        if(this.duration > 0) {
            entity.setDuration(this.duration);
        }
        if(this.category != null) {
            entity.setCategory(this.category);
        }
        if(this.status != null) {
            entity.setStatus(this.status);
        }
        if(instructor != null) {
            entity.setInstructorId(instructor);
        }
        return entity;
    }
}
