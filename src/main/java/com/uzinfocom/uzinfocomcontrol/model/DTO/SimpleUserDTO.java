package com.uzinfocom.uzinfocomcontrol.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
}
