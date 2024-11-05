/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.SecurityUtils;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * @author Bea⚝
 */
public class Submission implements Serializable {
    private String name;
    private String event;
    
    private String namePub;
    
    private String signature;

    public Submission(String name, String event) {        
        this.name = name;
        this.event = event;
    }
    
    public Submission(User name, String event) throws Exception {
        this.name = name.getName();
        this.namePub = Base64.getEncoder().encodeToString(name.getPub().getEncoded());
        this.event = event;
        sign(name.getPriv());
    }

    public void sign(PrivateKey priv) throws Exception {
        byte[] dataSign = SecurityUtils.sign(
                (namePub + event).getBytes(),
                priv);
        this.signature = Base64.getEncoder().encodeToString(dataSign);
    }

    public boolean isValid() {
        try {
            PublicKey pub = SecurityUtils.getPublicKey(Base64.getDecoder().decode(namePub));
            byte[] data = (namePub + event).getBytes();
            byte[] sign = Base64.getDecoder().decode(signature);
            return SecurityUtils.verifySign(data, sign, pub);
        } catch (Exception ex) {
            return false;
        }
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
        return String.format(name + " - " + event, isValid());
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

    public String getNamePub() {
        return namePub;
    }

    public void setNamePub(String namePub) {
        this.namePub = namePub;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
