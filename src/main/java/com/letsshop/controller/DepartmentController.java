package com.letsshop.controller;

import com.letsshop.dto.DepartmentDTO;
import com.letsshop.entity.Product;
import com.letsshop.service.DeparmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger logger = LogManager.getLogger(DepartmentController.class);

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
        logger.info("Inside getDepartments method ");
        logger.info(" Departments list before returning to client " +  departmentService.getDepartments().toString());

        return departmentService.getDepartments();
    }


}
