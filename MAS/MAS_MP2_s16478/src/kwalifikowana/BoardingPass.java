package kwalifikowana;


public class BoardingPass {

    private String ticketNumber;
    private String origin;
    private String destination;
    //private List<Person> people = new ArrayList<>();
    private Person person;

    public BoardingPass(String ticketNumber, String origin, String destination) {
        this.ticketNumber = ticketNumber;
        this.origin = origin;
        this.destination = destination;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person newPerson) {
        if (person != null) {
            // if person exist, remove his or her data from the map
            person.getBoardingPassMap().remove(ticketNumber);
            person = newPerson;
        } else {
            person = newPerson;
            // and adding reverse connection
            newPerson.addBoardingPassQualified(this);
        }
    }


    @Override
    public String toString() {
        String info = "\nticketNumber: " + ticketNumber + ", origin: " + origin + ", destination: " + destination +", person: ";
        info += person.getFirstName() + " " + person.getLastName();
        return info;
    }
}
