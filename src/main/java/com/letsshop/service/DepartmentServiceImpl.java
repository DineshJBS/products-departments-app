package com.letsshop.service;

import com.letsshop.entity.Product;
import com.letsshop.repository.DepartmentRepository;
import com.letsshop.dto.DepartmentDTO;
import com.letsshop.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DeparmentService{

    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public DepartmentDTO getDepartment(Integer deptId) {
        return this.convertEntityToDTO(departmentRepository.findByDeptId(deptId));



    }

    @Override
    public void addDepartment(Department tempDept) {
        departmentRepository.save(tempDept);
    }

    @Override
    public List<DepartmentDTO> getDepartments() {
        List<DepartmentDTO> temp = departmentRepository.findAll()
                .stream()
                .map(d -> {
                    List<Product> tempProducts = d.getProducts()
                            .stream()
                            .filter(p -> p.getDeleteOrNo() == 0)
                            .collect(Collectors.toList());

                    d.setProducts(tempProducts);
                    return d;
                })
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());

        return temp;
    }



    @Override
    public Department getDepartmentByName(String deptName) {
        Department temp =  departmentRepository.findByDeptName(deptName);
        return temp;
    }


    private DepartmentDTO convertEntityToDTO(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();

        if(department != null){

            departmentDTO.setDeptName(department.getDeptName());
            departmentDTO.setProducts(department.getProducts());
        }
        return departmentDTO;
    }
}
