package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Instructor;
import lombok.Data;

@Data
public class InstructorDTO {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
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

