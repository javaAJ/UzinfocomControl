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
public class CompanyDTO {
    private String name;
    private Long adminId;
    private List<Long> departmentsId;
}
