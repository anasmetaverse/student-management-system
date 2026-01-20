package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Instructor;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InstructorDTO {
    private String instructorId;

    @NotBlank(message = "First Name is Required")
    @Size(min = 3, message = "At least 3 char name is required")
    private String firstName;

    @NotBlank(message = "Last Name is Required")
    @Size(min = 3, message = "At least 3 char name is required")
    private String lastName;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Phone No is Required")
    @Pattern(regexp = "[0-9]{10}$", message = "Invalid Phone No")
    private String phoneNo;

    public Instructor toEntity() {
        return Instructor.builder()
                .fistName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .build();
    }

    public static InstructorDTO toDTO(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        dto.setInstructorId(instructor.getInstructorId());
        dto.setFirstName(instructor.getFistName());
        dto.setLastName(instructor.getLastName());
        dto.setEmail(instructor.getEmail());
        dto.setPhoneNo(instructor.getPhoneNo());
        return dto;
    }

    public Instructor toUpdateEntity(Instructor entity) {
        if(this.firstName != null) {
            entity.setFistName(this.firstName);
        }
        if(this.lastName != null) {
            entity.setLastName(this.lastName);
        }
        if(this.email != null) {
            entity.setEmail(this.email);
        }
        if(this.phoneNo != null) {
            entity.setPhoneNo(this.phoneNo);
        }
        return entity;
    }
}

