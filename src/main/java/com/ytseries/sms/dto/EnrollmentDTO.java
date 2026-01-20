package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Enrollment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentDTO {
    private String enrollmentId;

    private LocalDate enrollmentDate;

    @NotBlank(message = "studentId cannot be blank")
    private String studentId;

    @NotBlank(message = "courseId cannot be blank")
    private String courseId;

    // Additional fields for response
    private String courseName;
    private String studentName;

    public static EnrollmentDTO fromEnrollment(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setStudentId(enrollment.getStudent().getStudentId());
        enrollmentDTO.setCourseId(enrollment.getCourse().getCourseId());
        enrollmentDTO.setEnrollmentId(enrollment.getEnrollmentId());
        enrollmentDTO.setEnrollmentDate(enrollment.getEnrollmentDate());

        // Set course name and student name
        enrollmentDTO.setCourseName(enrollment.getCourse().getCourseName());
        enrollmentDTO
                .setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());

        return enrollmentDTO;
    }
}
