package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.Company;
import com.uzinfocom.uzinfocomcontrol.model.DTO.CompanyDTO;
import com.uzinfocom.uzinfocomcontrol.service.CompanyService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/getAll")
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/get/{id}")
    public Company getAll(
            @PathVariable(name = "id") Long companyId
    ) {
        Company company = companyService.getById(companyId);
        if (company == null){
            throw new ServiceException("Company is not found");
        }
        return company;
    }

    @PostMapping("/add")
    public Boolean addCompany(
            @RequestBody CompanyDTO companyDTO
    ) throws ServiceException {
        boolean isAdded = companyService.addCompany(companyDTO);
        System.out.println(companyDTO.getName());
        if (!isAdded){
            throw new ServiceException("Company wasn't added");
        }
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable(name = "id") Long companyId) {
        companyService.delete(companyId);
    }
}
