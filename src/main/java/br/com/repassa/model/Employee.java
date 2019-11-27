package br.com.repassa.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message = "Name may not be blank")
    private String name;

    @Column
    @NotBlank(message = "Department may not be blank")
    private String department;

    @Column
    @NotBlank(message = "Username may not be blank")
    private String username;

    @Column
    @NotBlank(message = "Password may not be blank")
    @Size(min = 6)
    private String password;

    @Column
    private String userRole;

    @Column
    private boolean enabled;
}
