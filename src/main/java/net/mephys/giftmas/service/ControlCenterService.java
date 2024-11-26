package net.mephys.giftmas.service;

import jakarta.annotation.PostConstruct;
import net.mephys.giftmas.common.MultipleChoice;
import net.mephys.giftmas.common.StringUtils;
import net.mephys.giftmas.execption.ExceededEntryException;
import net.mephys.giftmas.model.Email;
import net.mephys.giftmas.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ControlCenterService {

    @Autowired
    EmailService emailService;

    private MultipleChoice choice;

    private static final int MAX_ENTRIES = 10;
    private static final Logger LOG = LoggerFactory.getLogger(ControlCenterService.class);
    private final String FILENAME = "/tmp/persons.txt";
    private final List<Person> persons = new ArrayList<>();
    private final String TEMPLATE_CONTENT = """
            ¡Hola [PARTICIPANT]!
            
            ¡Espero que estés teniendo un excelente día! Soy tu organizador del Amigo Secreto y quería aprovechar esta ocasión para enviarte un mensaje especial.
            
            Tu Amigo Secreto es: **[SECRET_FRIEND]**.
            
            Estoy emocionado de que formes parte de este juego y quiero que sepas que estoy pensando en ti. ¡Espero que la sorpresa que tienes preparada para tu Amigo Secreto le guste mucho!
            
            Recuerda que lo más importante es el espíritu de amistad y diversión que estamos compartiendo. ¡Nos vemos pronto!
            
            Con cariño,
            Tu organizador de Amigo Secreto.
            """;

    @PostConstruct
    public void postConstructRoutine() {
        fromFile();
    }

    private void addEntry(Person person) throws ExceededEntryException {
        if (persons.size() == MAX_ENTRIES) {
            String errorMessage = "More that " + MAX_ENTRIES + " entries are not accepted.";
            LOG.error(errorMessage);
            throw new ExceededEntryException(errorMessage);
        }
        if (!persons.contains(person)) {
            persons.add(person);
            toFile();
            LOG.info("{} added.", person.toString());
            choice = new MultipleChoice(persons.size());
        } else LOG.warn("{} already Registered.", person);
    }

    public void addEntry(String name, String email) throws ExceededEntryException {
        addEntry(new Person(name, email, false));
    }

    public void addEntry(String name, String email, boolean sent) throws ExceededEntryException {
        addEntry(new Person(name, email, sent));
    }

    public void setPersons(List<Person> persons) throws ExceededEntryException {
        for (Person person : persons) {
            addEntry(person);
        }
    }

    public String getName(int i) {
        return persons.get(i).getName();
    }

    public String getEmail(int i) {
        return persons.get(i).getEmail();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void fromFile() {
        persons.clear();
        Path file = Paths.get(FILENAME);
        try {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                String[] split = line.split("\\s*,\\s*");
                addEntry(split[0], split[1], split[2].equals("true"));
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (ExceededEntryException e) {
            LOG.error(e.getMessage());
        }
    }

    public void toFile() {
        List<String> lines = persons.stream().map(person -> person.getName() + ", " + person.getEmail() + ", " + person.isSent()).toList();
        Path file = Paths.get(FILENAME);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    public int sendMessages() {
        int counter = 0;
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (!person.isSent()) {
                Email email = getEmailMessage(person);
                boolean sent = emailService.sendSimpleEmail(email);
                if (sent) {
                    person.setSent(true);
                    toFile();
                    ++counter;
                }
            }
        }
        return counter;
    }

    public List<Email> getAllMailMessages() {
        List<Email> emails = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            Email email = getEmailMessage(i);
            email.setContent(email.getContent().replaceAll("\\*\\*.*\\*\\*", "**INVISIBLE**"));
            emails.add(email);
        }
        return emails;
    }

    public Email getEmailMessage(int i) {
        String content = TEMPLATE_CONTENT.replace("[PARTICIPANT]",
                StringUtils.capitalizeFirstLetter(getName(i))).replace(("[SECRET_FRIEND]"),
                StringUtils.capitalizeFirstLetter(getName(choice.get(i))));
        return new Email(getEmail(i), "Amigo secreto", content);
    }

    public Email getEmailMessage(Person person) {
        int i = persons.indexOf(person);
        if (i < 0) throw new RuntimeException("This Person object does not exist");
        String content = TEMPLATE_CONTENT.replace("[PARTICIPANT]",
                StringUtils.capitalizeFirstLetter(person.getName())).replace(("[SECRET_FRIEND]"),
                StringUtils.capitalizeFirstLetter(getName(choice.get(i))));
        return new Email(getEmail(i), "Amigo secreto", content);
    }

    public void deletePersons(Integer id) {
        Person person = persons.get(id);
        persons.remove(person);
        toFile();
        LOG.info("Remove: " + person.toString());
    }

    public void clear() {
        persons.clear();
        toFile();
        LOG.info("All person entries gone.");
    }
}

