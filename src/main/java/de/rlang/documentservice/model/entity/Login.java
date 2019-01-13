package de.rlang.documentservice.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@Table(name = "login")
@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an Email")
    @Column(unique = true)
    private String email;

    @NotEmpty(message =  "*Please provide a valid Email")
    private String password;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
