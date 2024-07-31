package io.github.Hazarinn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {
    private String name;
    private String surname;
    private String cpf;
    private String cnpj;
    private boolean physical;
}
