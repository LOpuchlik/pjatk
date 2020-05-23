package kwalifikowana;

import java.util.HashMap;
import java.util.Map;

public class Programmer {

    private String firstName;
    private String lastName;
    private Map<String, Application> applicationMap = new HashMap<>();

    public Programmer(String firstName, String lastName) {
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

    public Map<String, Application> getApplicationMap() {
        return applicationMap;
    }

    public void setApplicationMap(Map<String, Application> applicationMap) {
        this.applicationMap = applicationMap;
    }

    public void setApplicationQualified (Application newApplication) throws Exception{
        if (!applicationMap.containsKey(newApplication.getUniqueSymbol())) {
            applicationMap.put(newApplication.getUniqueSymbol(), newApplication);
            newApplication.getProgrammers().add(this);
        } else {
            throw new Exception("Unable to add application with uniqueSymbol " + newApplication.getUniqueSymbol());
        }
    }

    public Application findApplicationQualified(String uniqueSymbol) throws Exception {
        if (!applicationMap.containsKey(uniqueSymbol)) {
            //throw new Exception("Unable to find BoardingPass with number: " + ticketNumber);
            System.err.println("Application with number: " + uniqueSymbol + " does not exist");
        }
        // zwraca tylko value, czyli symbol i nazwe aplikacji
        return applicationMap.get(uniqueSymbol);
    }

    @Override
    public String toString() {
        String info =  "Programmer: " + firstName + " " + lastName;
        for (Map.Entry<String, Application> entry : applicationMap.entrySet()) {
            info += entry.getValue();
        }
        return info;
    }
}
