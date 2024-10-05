/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

/**
 *
 * @author Bea⚝
 */
public class Submission {
    private String name;
    private String event;

    public Submission(String name, String event) {        
        this.name = name;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return String.format(name + " - " + event);
    }
    
     public static Submission fromString(String str) {
        // Assuming str is formatted as "name - event"
        String[] parts = str.split(" - ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid string format for Submission");
        }
        String name = parts[0];
        String event = parts[1];
        return new Submission(name, event);
    }
}
