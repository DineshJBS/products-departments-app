package com.letsshop.service;

import com.letsshop.dto.DepartmentDTO;
import com.letsshop.entity.Department;

import java.util.List;

public interface DeparmentService {

    public DepartmentDTO getDepartment(Integer deptId);

    void addDepartment(Department tempDept);

    List<DepartmentDTO> getDepartments();

    Department getDepartmentByName(String deptName);
}
