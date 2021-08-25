package one.digital.innovation.personAPI.controller;

import lombok.AllArgsConstructor;
import one.digital.innovation.personAPI.dto.request.PersonDTO;
import one.digital.innovation.personAPI.dto.response.MessageResponseDTO;
import one.digital.innovation.personAPI.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid PersonDTO personDTO) {
        return personService.create(personDTO);
    }
}
