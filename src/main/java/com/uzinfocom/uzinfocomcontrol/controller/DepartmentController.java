package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.Company;
import com.uzinfocom.uzinfocomcontrol.model.DTO.DepartmentDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.service.CompanyService;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/getAll")
    public List<DepartmentDTO> getAll() {
        List<Department> departmentList = departmentService.getAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentDTOList.add(departmentService.toDepartmentDto(department));
        }
        return departmentDTOList;
    }

    @GetMapping("/get/{id}")
    public DepartmentDTO getAll(@PathVariable(name = "id") Long departmentId) {
        Department department = departmentService.getById(departmentId);
        if (department == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Отдел не найден");
        }
        return departmentService.toDepartmentDto(department);
    }

    @PostMapping("/add")
    public Boolean addDepartment(
            @RequestBody DepartmentDTO departmentDTO
    ) throws ServiceException {
        Company company = companyService.getById(departmentDTO.getCompanyId());
        boolean isAdded = departmentService.addDepartment(departmentDTO.getName(), company);

        if (!isAdded){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Department wasn't added");
        }
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable(name = "id") Long departmentId) {
        departmentService.delete(departmentId);
    }
}
