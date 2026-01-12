package com.ytseries.sms.entity;

import com.ytseries.sms.entity.enums.Gender;
import com.ytseries.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate DOB;
    private String email;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;

//  this execute before the data save in the DB
    @PrePersist
    public void prePersist() {
        this.studentId = UUID.randomUUID().toString();
        this.setStatus(Status.ACTIVE);
    }

}
