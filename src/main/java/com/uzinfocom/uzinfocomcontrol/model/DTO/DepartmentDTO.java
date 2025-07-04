package com.uzinfocom.uzinfocomcontrol.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDTO {
    private Long id;
    private String name;
    private Long adminId;
    private String adminFullName;
    private Long companyId;
    private String companyName;
    private List<UserDTO> users;
}
