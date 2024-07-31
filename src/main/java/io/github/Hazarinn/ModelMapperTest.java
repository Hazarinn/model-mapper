package io.github.Hazarinn;

import io.github.Hazarinn.domain.Person;
import io.github.Hazarinn.domain.PersonType;
import io.github.Hazarinn.dto.PersonDTO;
import io.github.Hazarinn.dto.PersonTypeDTO;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;


import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ModelMapperTest {

    // Transferir estado: mesmo tipo e mesmo nome do atributo -OK
    // Transferir estado: mesmo tipo e mesmo nome do comportamento OK
    // Transferir estado: diferente tipo e mesmo nome do atributo -
    // Transferir estado: diferente nome de atributo
    // Transferir estado: com condicional



    private static final ModelMapper modelMapper = new ModelMapper();


    static {
        modelMapper.addConverter(getConverterPersonType(), PersonType.class, PersonTypeDTO.class);

        final var typeMap = modelMapper.typeMap(Person.class, PersonDTO.class);
        typeMap.addMappings(map -> map.map(Person::getNickname, PersonDTO::setSurname))
                .addMappings(map -> map.when(getPFCondition()).map(Person::getRegister, PersonDTO::setCpf))
        .addMappings(map -> map.when(getPJCondition()).map(Person::getRegister, PersonDTO::setCnpj));
    }


    private static Condition<String, String> getPFCondition() {
        return cxt -> Optional.ofNullable(cxt.getSource())
                .map(register -> register.replace(".", "").replace("-", ""))
                .filter(register -> register.length() == 11).isPresent();

    }

    private static Condition<String, String> getPJCondition() {
        return cxt -> Optional.ofNullable(cxt.getSource())
                .map(register -> register.replace(".", "").replace("-", "")
                        .replace("/", ""))

                .filter(register -> register.length() == 14).isPresent();

    }

    private static Converter<PersonType, PersonTypeDTO> getConverterPersonType() {
        return context -> Optional.ofNullable(context.getSource())
                .map(personType -> personType == PersonType.PF ? PersonTypeDTO.PHYSICAL_PERSON : PersonTypeDTO.PHYSICAL_PERSON)
                .orElse(null);
    }

    public static void main(String[] args) {


        final var joao = new Person("joao", "Jo", "000.000.000.00", PersonType.PF);
        final var empresa = new Person("Empresa", "Ma", "XX.XXX.XXX/0001-XX", PersonType.PJ);


        final var joaoDTO = modelMapper.map(joao, PersonDTO.class);
        final var empresaDTO = modelMapper.map(empresa, PersonDTO.class);

        System.out.println(joaoDTO);
        System.out.println(empresaDTO);



    }
}