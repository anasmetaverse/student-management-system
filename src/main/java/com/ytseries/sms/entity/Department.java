package com.ytseries.sms.entity;

import com.ytseries.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Department {
    @Id
    private String departmentId;
    private String departmentName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "hod")
    private Instructor HOD;

    @OneToMany
    private List<Instructor> instructors;

    @PrePersist
    public void prePersist() {
        this.departmentId = UUID.randomUUID().toString();
        this.setStatus(Status.ACTIVE);
    }
}
