package com.uzinfocom.uzinfocomcontrol.model.DTO;

import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String userName;
    private String phoneNumber;
    private Date dateOfBirthday;
    private Position telegramPosition;
    private Long departmentId;
}
