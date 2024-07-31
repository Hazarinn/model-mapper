package io.github.Hazarinn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;
    private String nickname;
    private String register;
    private PersonType type;

    public boolean isPhysical(){
        return this.type.equals(PersonType.PF);
    }

    public String getSurname(){
        return this.getNickname();
    }
}
