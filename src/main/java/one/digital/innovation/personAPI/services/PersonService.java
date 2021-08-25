package one.digital.innovation.personAPI.services;

import lombok.AllArgsConstructor;
import one.digital.innovation.personAPI.dto.mapper.PersonMapper;
import one.digital.innovation.personAPI.dto.request.PersonDTO;
import one.digital.innovation.personAPI.dto.response.MessageResponseDTO;
import one.digital.innovation.personAPI.entities.Person;
import one.digital.innovation.personAPI.exception.PersonNotFoundException;
import one.digital.innovation.personAPI.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public MessageResponseDTO create(PersonDTO personDTO) {
        Person person = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(person);

        MessageResponseDTO messageResponse = createMessageResponse("Person successfully created with ID ", savedPerson.getId());

        return messageResponse;
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return personMapper.toDTO(person);
    }

    public List<PersonDTO> listAll() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);

        MessageResponseDTO messageResponse = createMessageResponse("Person successfully updated with ID ", updatedPerson.getId());

        return messageResponse;
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public void delete(Long id) throws PersonNotFoundException {
        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        personRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(String s, Long id2) {
        return MessageResponseDTO.builder()
                .message(s + id2)
                .build();
    }
}
