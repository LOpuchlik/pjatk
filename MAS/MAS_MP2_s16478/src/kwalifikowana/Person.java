package kwalifikowana;

import java.util.HashMap;
import java.util.Map;

public class Person {

    private String firstName;
    private String lastName;
    private Map<String, BoardingPass> boardingPassMap = new HashMap<>();

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, BoardingPass> getBoardingPassMap() {
        return boardingPassMap;
    }

    public void setBoardingPassMap(Map<String, BoardingPass> boardingPassMap) {
        this.boardingPassMap = boardingPassMap;
    }


    public void addBoardingPassQualified (BoardingPass newBoardingPass) {
        if (!boardingPassMap.containsKey(newBoardingPass.getTicketNumber())) {
            boardingPassMap.put(newBoardingPass.getTicketNumber(), newBoardingPass);
            newBoardingPass.setPerson(this);
        }
    }


    public BoardingPass findBoardingPassQualified(String ticketNumber) throws Exception {
        if (!boardingPassMap.containsKey(ticketNumber)) {
            //throw new Exception("Unable to find BoardingPass with number: " + ticketNumber);
            System.err.println("BoardingPass with number: " + ticketNumber + " does not exist");
        }
        return boardingPassMap.get(ticketNumber);
    }



    @Override
    public String toString() {
        String info = "firstName = " + firstName + ", lastName = " + lastName;
        for (Map.Entry<String, BoardingPass> entry : boardingPassMap.entrySet()) {
            info += entry.getValue();
        }
        return info;
    }


}
