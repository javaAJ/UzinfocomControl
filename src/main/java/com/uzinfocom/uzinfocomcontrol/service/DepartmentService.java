package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.Company;
import com.uzinfocom.uzinfocomcontrol.model.DTO.DepartmentDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.UserDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserService userService;

    public List<Department> getAll(){
        return departmentRepository.findAll();
    }
    public Department getById(Long id){
        return departmentRepository.findById(id).orElse(null);
    }
    public Department getUsersByDepartmentId(Long id){
        return departmentRepository.findByIdWithUsers(id).orElse(null);
    }

    public boolean setAdmin(Long departmentId, User user) {
        Department department = getById(departmentId);
        if (department == null){
            return true;
        }
        department.setAdmin(user);
        departmentRepository.save(department);
        return true;
    }
    public Department addDepartment(String name, Company company) {
        if (name == null || company == null){
            return null;
        }

        Department department = new Department();
        department.setName(name);
        department.setCompany(company);


        return departmentRepository.save(department);
    }
    @Transactional
    public void addUser(Long id, User user) {
        Department department = getById(id);
        System.out.println(department.getUsers());
        if (department.getUsers() == null){
            department.setUsers(new ArrayList<>());
        }
        System.out.println(department.getName());
        List<User> users1 = department.getUsers();
        System.out.println(users1.size());


        List<User> users = department.getUsers();
        users.add(user);
        department.setUsers(users);
        departmentRepository.save(department);
    }

    public void delete(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    public DepartmentDTO toDepartmentDto(Department department) {
        if (department == null) return null;

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());

        if (department.getAdmin() != null) {
            dto.setAdminId(department.getAdmin().getId());
            dto.setAdminFullName(
                    department.getAdmin().getFirstName() + " " + department.getAdmin().getLastName()
            );
        }

        if (department.getCompany() != null) {
            dto.setCompanyId(department.getCompany().getId());
            dto.setCompanyName(department.getCompany().getName());
        }

        if (department.getUsers() != null) {
            List<UserDTO> usersDTO = department.getUsers().stream()
                    .map(userService::toUserDto)
                    .collect(Collectors.toList());
            dto.setUsers(usersDTO);
        }
        return dto;
    }

}
