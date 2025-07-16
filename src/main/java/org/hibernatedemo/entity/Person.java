package org.hibernatedemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @EmbeddedId
    @Valid
    private PersonCompositeKey id;

    @NotBlank(message = "Phone number cannot be blank")
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "city_of_living")
    @NotBlank(message = "City cannot be blank")
    private String city;

}
