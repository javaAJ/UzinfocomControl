package com.uzinfocom.uzinfocomcontrol.model.DTO;

import com.uzinfocom.uzinfocomcontrol.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private Long id;
    private String message;
    private String title;
}
