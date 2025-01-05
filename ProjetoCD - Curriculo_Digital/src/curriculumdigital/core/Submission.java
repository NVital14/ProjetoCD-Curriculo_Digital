/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import curriculumdigital.utils.SecurityUtils;
import curriculumdigital.utils.SecurityUtils;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;

/**
 *
 * @author Bea⚝
 */
public class Submission implements Serializable {

    private String name; //nome da pessoa a quem o currículo é emitido
    private String event; //evento curricular a emitir
    private String user; //nome do utilizador que emite a submissão
    private String userPub; //chave pública do utilizador que emite a submissão

    private String signature; //assinatura do utilizador que emite a submissão

    /**
     * Construtor
     *
     * @param user nome do utilizador que está a emitir a submissão
     * @param name nome da pessoa a quem o currículo é emitido
     * @param event evento curricular a emitir
     */
    public Submission(String user, String name, String event) {
        this.user = user;
        this.name = name;
        this.event = event;
    }

    /**
     * Construtor
     *
     * @param user utilizador que está a emitir a submissão
     * @param name nome da pessoa a quem o currículo é emitido
     * @param event evento curricular a emitir
     * @throws Exception
     */
    public Submission(User user, String name, String event) throws Exception {
        this.name = name;
        this.event = event;
        this.user = user.getName();
        this.userPub = Base64.getEncoder().encodeToString(user.getPub().getEncoded());
        sign(user.getPriv());
    }

    /**
     * Método que criar a assinatura do utilizador
     *
     * @param priv chave privada do utilizador
     * @throws Exception
     */
    public void sign(PrivateKey priv) throws Exception {
        byte[] dataSign = SecurityUtils.sign(
                (userPub + name + event).getBytes(),
                priv);
        this.signature = Base64.getEncoder().encodeToString(dataSign);
    }

    /**
     * Método que Verifica se a submissão é válida
     *
     * @return boolean
     */
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

    /**
     * Obtém o nome do utilizador associado à submissão
     *
     * @return nome do utilizador
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do utilizador associado à submissão
     *
     * @param name Nome do utilizador
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o evento associado à submissão
     *
     * @return evento
     */
    public String getEvent() {
        return event;
    }

    /**
     * Define o evento associado à submissão
     *
     * @param event evento
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * Retorna uma representação em string da submissão, incluindo o utilizador,
     * o nome e o evento.
     *
     * @return String submissão
     */
    @Override
    public String toString() {
        return String.format(user + "->" + name + " - " + event + "\n", isValid());
    }

    /**
     * Obtém o nome do utilizador associado à submissão
     *
     * @return nome do utilizador
     */
    public String getUser() {
        return user;
    }

    /**
     * Define o nome do utilizador associado à submissão
     *
     * @param user nome do utilizador
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Obtém a chave pública do utilizador associado à submissão
     *
     * @return chave pública do utilizador
     */
    public String getUserPub() {
        return userPub;
    }

    /**
     * Define a chave pública do utilizador associado à submissão
     *
     * @param userPub chave pública do utilizador
     */
    public void setUserPub(String userPub) {
        this.userPub = userPub;
    }

    /**
     * Obtém a assinatura associada à submissão
     *
     * @return assinatura
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Define a assinatura associada à submissão
     *
     * @param signature assinatura
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Verifica se esta submissão é igual a outra, comparando os seus atributos
     *
     * @param obj objeto a ser comparado com o this
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Mesma referência
        }
        if (!(obj instanceof Submission)) {
            return false; // Não é uma Submission
        }
        Submission sub = (Submission) obj;

        // Comparação dos atributos
        return Objects.equals(userPub, sub.userPub)
                && Objects.equals(user, sub.user)
                && Objects.equals(signature, sub.signature)
                && Objects.equals(name, sub.name)
                && Objects.equals(event, sub.event);
    }

    /**
     * Calcula o hash code desta submissão com base nos seus atributos
     *
     * @return valor do hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(userPub, user, signature, name, event);
    }
    
    private static final long serialVersionUID = -5423656298433479698L;
}
