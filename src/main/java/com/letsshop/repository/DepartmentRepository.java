package com.letsshop.repository;

import com.letsshop.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDeptId(Integer deptId);
    Department findByDeptName(String deptName);
}
