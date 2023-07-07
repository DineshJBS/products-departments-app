package com.letsshop.controller;

import com.letsshop.dto.DepartmentDTO;
import com.letsshop.entity.Product;
import com.letsshop.service.DeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DeparmentService departmentService;

    @GetMapping("/department/{id}")
    public DepartmentDTO getDepartment(@PathVariable Integer id){
        System.out.println(departmentService.getDepartment(2));
        return departmentService.getDepartment(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public List<DepartmentDTO> getDepartments(){
        System.out.println(departmentService.getDepartments().toString());

        return departmentService.getDepartments();
    }


}
