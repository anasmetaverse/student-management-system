package com.ytseries.sms.entity;

import com.ytseries.sms.entity.enums.Category;
import com.ytseries.sms.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
    @Id
    private String courseId;
    private String courseName;
    private String courseDescription;
    private long duration;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdOn;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "instructor_Id")
    private Instructor instructorId;

    @PrePersist
    public void prePersist() {
        this.courseId = UUID.randomUUID().toString();
        this.createdOn = LocalDate.now();
    }
}
