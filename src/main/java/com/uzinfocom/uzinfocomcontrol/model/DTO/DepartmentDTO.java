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
    private String name;
    private Long companyId;
    private Long adminId;
    private List<Long> usersId;
}
