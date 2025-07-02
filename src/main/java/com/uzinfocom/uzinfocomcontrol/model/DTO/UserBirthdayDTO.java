package com.uzinfocom.uzinfocomcontrol.model.DTO;

import com.uzinfocom.uzinfocomcontrol.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBirthdayDTO {
    private Long id;
    private Long userBirthdayId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private List<UserBirthdayPaymentDTO> usersBirthdayPayment;
}
