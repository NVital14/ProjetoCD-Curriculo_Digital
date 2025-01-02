/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.SecurityUtils;
import java.io.IOException;
import java.io.Serializable;
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
 * A classe User representa um utilizador no sistema. Ela contém informações
 * sobre o nome do utilizador, chaves públicas e privadas, uma chave simétrica e
 * se o utilizador é um instituto.
 *
 * @author Bea⚝
 */
public class User implements Serializable {

    private String name; // nome do utilizador
    private PublicKey pub; // chave pública     
    private PrivateKey priv; // chave privada
    private Key sim; // chave simétrica
    private boolean isInstitute; // indica se o utilizador é um instituto

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

    /**
     * Gera um par de chaves assimétricas e uma chave simétrica para o
     * utilizador
     *
     */
    public void generateKeys() throws Exception {
        this.sim = SecurityUtils.generateAESKey(256);
        KeyPair kp = SecurityUtils.generateECKeyPair(256);
        this.pub = kp.getPublic();
        this.priv = kp.getPrivate();
    }

    /**
     * Regista o utilizador, guarda as chaves do utilizador num ficheiro
     * encriptado
     *
     * @param password a password para encriptar as chaves
     */
    public void save(String password) throws Exception {
        Path folderPath = Paths.get("blockchainfiles");
        //garante que a pasta existe
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        // Caminhos dos arquivos baseados na pasta
        Path privateKeyPath = folderPath.resolve(this.name + ".priv");
        Path symmetricKeyPath = folderPath.resolve(this.name + ".sim");
        Path publicKeyPath = folderPath.resolve(this.name + ".pub");
        Path instituteFlagPath = folderPath.resolve(this.name + ".institute");

        // Encripta e salva a chave privada
        byte[] secret = SecurityUtils.encrypt(priv.getEncoded(), password);
        Files.write(privateKeyPath, secret);

        // Encripta e salva a chave simétrica
        byte[] simData = SecurityUtils.encrypt(sim.getEncoded(), password);
        Files.write(symmetricKeyPath, simData);

        // Salva a chave pública
        Files.write(publicKeyPath, pub.getEncoded());

        // Encripta e salva o estado do atributo isInstitute
        byte[] instituteData = SecurityUtils.encrypt(String.valueOf(isInstitute).getBytes(), password);
        Files.write(instituteFlagPath, instituteData);

    }

    /**
     * Faz login do utilizador, carregando as suas chaves de um ficheiro
     * encriptado
     *
     * @param password a password para desencriptar as chaves
     */
    public void load(String password) throws Exception {
        Path folderPath = Paths.get("blockchainfiles");

        //  caminhos dos ficheiros
        Path privateKeyPath = folderPath.resolve(this.name + ".priv");
        Path symmetricKeyPath = folderPath.resolve(this.name + ".sim");
        Path publicKeyPath = folderPath.resolve(this.name + ".pub");
        Path instituteFlagPath = folderPath.resolve(this.name + ".institute");

        // desencripta a chave privada
        byte[] privData = Files.readAllBytes(privateKeyPath);
        privData = SecurityUtils.decrypt(privData, password);

        // desencripta a chave simétrica
        byte[] simData = Files.readAllBytes(symmetricKeyPath);
        simData = SecurityUtils.decrypt(simData, password);

        // lê a chave pública
        byte[] pubData = Files.readAllBytes(publicKeyPath);

        this.priv = SecurityUtils.getPrivateKey(privData);
        this.pub = SecurityUtils.getPublicKey(pubData);
        this.sim = SecurityUtils.getAESKey(simData);

        // desencripta e carrega o estado do isInstitute
        byte[] encryptedInstituteData = Files.readAllBytes(instituteFlagPath);
        byte[] decryptedInstituteData = SecurityUtils.decrypt(encryptedInstituteData, password);
        this.isInstitute = Boolean.parseBoolean(new String(decryptedInstituteData));

    }

    /**
     * Carrega a chave pública de um ficheiro
     */
    public void loadPublic() throws Exception {
        //ler a publica
        byte[] pubData = Files.readAllBytes(Path.of("/blockchainfiles/" + this.name + ".pub"));
        this.pub = SecurityUtils.getPublicKey(pubData);
    }

    /**
     * Obtém uma lista dos utilizadores registados
     */
    public static List<String> getExistingUsers() throws IOException {
        List<String> users = new ArrayList<>();
        Path dir = Paths.get("blockchainfiles");
        // verifica se a pasta existe
        if (!Files.exists(dir)) {
            System.out.println("A pasta não existe: " + dir.toAbsolutePath());
            return users; // Retorna uma lista vazia
        }
        // obtém todos os ficheiros .pub
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.pub")) {
            for (Path entry : stream) {
                // remove a extensão para obter o nome do utilizador
                String fileName = entry.getFileName().toString();
                String userName = fileName.substring(0, fileName.lastIndexOf('.'));
                users.add(userName);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ir buscar os utilizadores: " + e.getMessage());
            throw e;
        }

        return users;
    }

    /**
     * Obtém o nome do utilizador
     *
     * @return o nome do utilizador
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do utilizador
     *
     * @param name o nome do utilizador
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém a chave pública
     *
     * @return a chave pública
     */
    public PublicKey getPub() {
        return pub;
    }

    /**
     * Define a chave pública
     *
     * @param pub a chave pública
     */
    public void setPub(PublicKey pub) {
        this.pub = pub;
    }

    /**
     * Obtém a chave privada
     *
     * @return a chave privada
     */
    public PrivateKey getPriv() {
        return priv;
    }

    /**
     * Define a chave privada
     *
     * @param priv a chave privada
     */
    public void setPriv(PrivateKey priv) {
        this.priv = priv;
    }

    /**
     * Obtém a chave simétrica
     *
     * @return a chave simétrica
     */
    public Key getSim() {
        return sim;
    }

    /**
     * Define a chave simétrica
     *
     * @param sim a chave simétrica
     */
    public void setSim(Key sim) {
        this.sim = sim;
    }

    /**
     * Verifica se o utilizador é um instituto
     *
     * @return boolean
     */
    public boolean isInstitute() {
        return isInstitute;
    }

    /**
     * Define se o utilizador é um instituto
     *
     * @param isInstitute boolean
     */
    public void setInstitute(boolean isInstitute) {
        this.isInstitute = isInstitute;
    }
}
