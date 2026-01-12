package com.ytseries.sms.dto;

import com.ytseries.sms.controller.validator.MaxYear;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.entity.enums.Gender;
import com.ytseries.sms.entity.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {
    private String studentId;

//  For String  = NOtBlack
    @NotBlank(message = "First Name is Required")
    @Size(min = 3, message = "Atleast 3 char name is required")
    private String fName;

    @NotBlank(message = "Last Name is Required")
    @Size(min = 3, message = "Atleast 3 char name is required")
    private String lName;

//  @
    @NotBlank(message = "Email Name is Required")
    @Email(message = "Invalid Email")
    private String email;

//   0-9
    @NotBlank(message = "Phone Name is Required")
    @Pattern(regexp = "[0-9]{10}$", message = "Invalid Phone No")
    private String phoneNo;

    @NotNull(message = "DOB is Required")
//  past -> future dates
    @Past(message = "DOB can't be from future")
//  if student hase DOB- year > 2023 = invalid
    @MaxYear(value = 2023)
    private LocalDate DOB;

    @NotNull(message = "Gender is Required")
    private Gender gender;

//    @NotBlank(message = "Status is Required")
    private Status status;

    public StudentDTO(String studentId, String fName, String lName, String email, String phoneNo, LocalDate DOB, Gender gender, Status status) {
        this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.DOB = DOB;
        this.gender = gender;
        this.status = status;
    }

    public Student toEntity () {
        return Student.builder()
                .lastName(this.lName)
                .firstName(this.fName)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .DOB(this.DOB)
                .gender(this.gender)
                .status(this.status)
                .build();
    }

    public static StudentDTO toDto (Student entity) {
        return new StudentDTO(
            entity.getStudentId().toString(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getPhoneNo(),
            entity.getDOB(),
            entity.getGender(),
            entity.getStatus()
        );
    }

    public Student toUpdateEntity (Student entity) {
//        user sent the dto, in that fname is not null means update
        if(this.fName != null) {
            entity.setFirstName(this.fName);
        }
        if(this.lName != null) {
            entity.setLastName(this.lName);
        }
        if(this.email != null) {
            entity.setEmail(this.email);
        }
        if(this.phoneNo != null) {
            entity.setPhoneNo(this.phoneNo);
        }
        if(this.DOB != null) {
            entity.setDOB(this.DOB);
        }
        if(this.gender != null) {
            entity.setGender(this.gender);
        }
        return entity;
    }
}
