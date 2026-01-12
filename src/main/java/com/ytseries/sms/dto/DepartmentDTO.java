package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Department;
import com.ytseries.sms.entity.Instructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentDTO {
    private String departmentId;
    private String departmentName;
    private String hodId;
    private List<String> instructorIds;

    public Department toEntity(Instructor hod, List<Instructor> instructors) {
        Department department = new Department();
        department.setDepartmentName(this.departmentName);
        department.setHOD(hod);
        department.setInstructors(instructors);
        return department;
    }

    public static DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        if(department.getHOD() != null) {
            dto.setHodId(department.getHOD().getInstructorId());
        }
        if(department.getInstructors() != null) {
            dto.setInstructorIds(department.getInstructors().stream()
                    .map(Instructor::getInstructorId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Department toUpdateEntity(Department entity, Instructor hod, List<Instructor> instructors) {
        if(this.departmentName != null) {
            entity.setDepartmentName(this.departmentName);
        }
        if(hod != null) {
            entity.setHOD(hod);
        }
        if(instructors != null) {
            entity.setInstructors(instructors);
        }
        return entity;
    }
}

