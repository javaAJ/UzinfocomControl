package com.uzinfocom.uzinfocomcontrol.model.DTO;

import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String userName;
    private String phoneNumber;
    private LocalDate dateOfBirthday;
    private Position telegramPosition;
    private Long departmentId;
    private String departmentName;
}