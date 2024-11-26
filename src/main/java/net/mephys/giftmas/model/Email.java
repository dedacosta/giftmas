package net.mephys.giftmas.model;

public class Email {
    private final String address;
    private final String subject;
    private String content;

    public Email(String address, String subject, String content) {
        this.address = address;
        this.subject = subject;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "E-mail: " + address+ "\nSubject: " + subject+"\nContent: \n" + content+"\n";
    }

    public void setContent(String content) {
        this.content=content;
    }
}
