package com.uzinfocom.uzinfocomcontrol.model;

import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String telegramUserName;
    private String userName;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirthday;
    @Enumerated(EnumType.STRING)
    private Position telegramPosition;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
}
