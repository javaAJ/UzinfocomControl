package com.uzinfocom.uzinfocomcontrol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBirthday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userBirthday;
    private Boolean isPaid;
    @ManyToOne
    private User userPayment;
    private LocalDate paidDate;
    @ManyToOne
    private Department department;

}
