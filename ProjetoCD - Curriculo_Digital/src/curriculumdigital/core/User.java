/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.SecurityUtils;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bea⚝
 */
public class User {
    private String name;

    private PublicKey pub;
    private PrivateKey priv;
    private Key sim;
    
    private boolean isInstitute;

    public User(String name) {
        this.name = name;
        this.pub = null;
        this.priv = null;
        this.sim = null;
        this.isInstitute = false;
    }

    public User() throws Exception {
        this.name = "noName";
        this.pub = null;
        this.priv = null;
        this.sim = null;
    }
    
    public void generateKeys() throws Exception {
        this.sim = SecurityUtils.generateAESKey(256);
        KeyPair kp = SecurityUtils.generateECKeyPair(256);
        this.pub = kp.getPublic();
        this.priv = kp.getPrivate();
    }

    public void save(String password) throws Exception {
        //encriptar a chave privada
        byte[] secret = SecurityUtils.encrypt(priv.getEncoded(), password);
        Files.write(Path.of(this.name + ".priv"), secret);
        //encrptar a chave simetrica
        byte[] simData = SecurityUtils.encrypt(sim.getEncoded(), password);
        Files.write(Path.of(this.name + ".sim"), simData);
        //guardar a public
        Files.write(Path.of(this.name + ".pub"), pub.getEncoded());
       // Criptografar e guardar o estado do atributo isInstitute
        byte[] instituteData = SecurityUtils.encrypt(String.valueOf(isInstitute).getBytes(), password);
        Files.write(Path.of(this.name + ".institute"), instituteData);
     }

    public void load(String password) throws Exception {
        //desencriptar a privada
        byte[] privData = Files.readAllBytes(Path.of(this.name + ".priv"));
        privData = SecurityUtils.decrypt(privData, password);
        //desencriptar a privada
        byte[] simData = Files.readAllBytes(Path.of(this.name + ".sim"));
        simData = SecurityUtils.decrypt(simData, password);
        //ler a publica
        byte[] pubData = Files.readAllBytes(Path.of(this.name + ".pub"));
        this.priv = SecurityUtils.getPrivateKey(privData);
        this.pub = SecurityUtils.getPublicKey(pubData);
        this.sim = SecurityUtils.getAESKey(simData);
        // Descriptografar e carregar o estado do atributo isInstitute
        byte[] encryptedInstituteData = Files.readAllBytes(Path.of(this.name + ".institute"));
        byte[] decryptedInstituteData = SecurityUtils.decrypt(encryptedInstituteData, password);
        this.isInstitute = Boolean.parseBoolean(new String(decryptedInstituteData));
   }
    
    public void loadPublic() throws Exception{
        //ler a publica
        byte[] pubData = Files.readAllBytes(Path.of(this.name + ".pub"));
        this.pub = SecurityUtils.getPublicKey(pubData);
    }
    
    // Método para obter uma lista dos utilizadores já criados
    public static List<String> getExistingUsers() throws IOException {
        List<String> users = new ArrayList<>();
        // Caminho onde os ficheiros de utilizador estão guardados (podes alterar conforme necessário)
        Path dir = Paths.get(".");
        // Obter todos os ficheiros com a extensão .pub (indica que o utilizador foi criado)
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.pub");
        for (Path entry : stream) {
            // Remover a extensão para obter o nome do utilizador
            String fileName = entry.getFileName().toString();
            String userName = fileName.substring(0, fileName.lastIndexOf('.'));
            users.add(userName);
        }
        return users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PublicKey getPub() {
        return pub;
    }

    public void setPub(PublicKey pub) {
        this.pub = pub;
    }

    public PrivateKey getPriv() {
        return priv;
    }

    public void setPriv(PrivateKey priv) {
        this.priv = priv;
    }

    public Key getSim() {
        return sim;
    }

    public void setSim(Key sim) {
        this.sim = sim;
    }
    
    public boolean isInstitute() {
        return isInstitute;
    }

    public void setInstitute(boolean isInstitute) {
        this.isInstitute = isInstitute;
    }
}