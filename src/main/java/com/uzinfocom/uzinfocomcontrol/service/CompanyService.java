package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.Company;
import com.uzinfocom.uzinfocomcontrol.model.DTO.CompanyDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll(){
        return companyRepository.findAll();
    }
    public Company getById(Long id){
        return companyRepository.findById(id).get();
    }

    public Company addCompany(CompanyDTO companyDTO) {
        if (companyDTO.getName() == null){
            return null;
        }
        Company company = new Company();
        company.setName(companyDTO.getName());

        if (companyDTO.getAdminId() != null){
            User user = userService.getById(companyDTO.getAdminId());
            company.setAdmin(user);
        }
        if (companyDTO.getDepartmentsId() != null){
            List<Department> departmentList = addDepartmentsToCompany(companyDTO.getDepartmentsId());
            company.setDepartmentList(departmentList);
        }


        return companyRepository.save(company);
    }

    private List<Department> addDepartmentsToCompany(List<Long> departmentsId) {
        List<Department> departmentList = new ArrayList<>();
        for (Long id : departmentsId) {
            departmentList.add(departmentService.getById(id));
        }
        return departmentList;
    }

    public void delete(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
