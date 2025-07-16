package org.hibernatedemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
