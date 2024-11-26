package net.mephys.giftmas.controller;

import net.mephys.giftmas.execption.ExceededEntryException;
import net.mephys.giftmas.model.Email;
import net.mephys.giftmas.model.Person;
import net.mephys.giftmas.service.ControlCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("control")
public class ControlCenterController {

    @Autowired
    ControlCenterService controlCenterService;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return controlCenterService.getPersons();
    }

    @GetMapping("person/{id}")
    public Person getPerson(@PathVariable("id") Integer id) {
        return controlCenterService.getPersons().get(id);
    }

    @PostMapping(value = "persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> postPersons(@RequestBody List<Person> persons) throws ExceededEntryException {
        controlCenterService.setPersons(persons);
        return controlCenterService.getPersons();
    }

    @GetMapping("show")
    public List<Email> showEmails() {
        return controlCenterService.getAllMailMessages();
    }

    @PostMapping("send")
    public Integer sendEmails() {
        return controlCenterService.sendMessages();
    }

    @DeleteMapping("person/{id}")
    public void deletePerson(@PathVariable("id") Integer id) {
        controlCenterService.deletePersons(id);
    }

    @DeleteMapping(value = "persons")
    public void deletePersons() {
        controlCenterService.clear();
    }

}
