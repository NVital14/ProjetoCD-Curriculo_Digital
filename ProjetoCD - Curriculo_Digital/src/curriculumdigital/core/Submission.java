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
    
    private String user;
    private String userPub;
    
    private String signature;

    public Submission(String user, String name, String event) {     
        this.user = user;
        this.name = name;
        this.event = event;
    }
    
    public Submission(User user, String name, String event) throws Exception {
        this.name = name;
        this.event = event;
        this.user = user.getName();
        this.userPub = Base64.getEncoder().encodeToString(user.getPub().getEncoded());
        sign(user.getPriv());
    }

    public void sign(PrivateKey priv) throws Exception {
        byte[] dataSign = SecurityUtils.sign(
                (userPub + name + event).getBytes(),
                priv);
        this.signature = Base64.getEncoder().encodeToString(dataSign);
    }

    public boolean isValid() {
        try {
            PublicKey pub = SecurityUtils.getPublicKey(Base64.getDecoder().decode(userPub));
            byte[] data = (userPub + name + event).getBytes();
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
        return String.format(user+ "->" + name + " - " + event  + "\n", isValid());
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getUserPub() {
        return userPub;
    }

    public void setUserPub(String userPub) {
        this.userPub = userPub;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
