package org.hibernatedemo.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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
