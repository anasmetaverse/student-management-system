package com.ytseries.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"courseId", "studentId"})
        }
)
public class Enrollment {
    @Id
    private String enrollmentId;
    private LocalDate enrollmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private Course course;

    @PrePersist
    public void prePersistCourse() {
        this.enrollmentId = UUID.randomUUID().toString();
        this.enrollmentDate = LocalDate.now();
    }
}
