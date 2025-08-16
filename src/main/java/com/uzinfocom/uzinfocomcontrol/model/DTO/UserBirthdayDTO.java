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
    private UserDTO birthdayUser;
    private Double totalAmountPaid;
    private List<UserBirthdayPaymentDTO> usersBirthdayPayment;
}
