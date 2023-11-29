package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.User;
import com.makogon.foodtracker.repository.PersonRepository;
import org.springframework.stereotype.Service;
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Персона с идентификатором " + personId + " не найдена"));
    }
public Person getPersonByUser(User user){
    return (Person) personRepository.findByUser(user)
            .orElseThrow(() -> new IllegalArgumentException("Персона с идентификатором " + user + " не найдена"));

}
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
    public Person updatePerson(Person updatedPerson) {
        Person existingPerson = getPersonById(updatedPerson.getPersonID());

        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());

        return personRepository.save(existingPerson);
    }

    public void deletePersonById(long personId) {
        Person person = getPersonById(personId);
        personRepository.delete(person);
    }
}