package org.hibernatedemo.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PersonCompositeKey implements Serializable {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 109, message = "Age must be less than 110")
    private int age;

}
