package com.uzinfocom.uzinfocomcontrol.model.DTO;

import com.uzinfocom.uzinfocomcontrol.model.User;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBirthdayPaymentDTO {
    private Long id;

    private Long userId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Double paymentAmount;
    private Double amountPaid;
}
