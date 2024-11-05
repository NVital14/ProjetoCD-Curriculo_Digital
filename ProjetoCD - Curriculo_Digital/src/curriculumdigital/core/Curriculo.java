/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.Hash;
import blockchain.utils.MerkleTree;
import blockchain.utils.ObjectUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bea⚝
 */
public class Curriculo implements Serializable {

    blockchain.utils.BlockChain bc;
    private MerkleTree merkleTree;
    public List<Submission> submissions;
    public static int DIFICULTY = 4;

    public Curriculo() throws Exception{
        this.submissions = new ArrayList<>();
        this.bc = new BlockChain();
        this.merkleTree = new MerkleTree();
        Submission s = new Submission("Default", "Default");
        this.bc.add(s.toString(), DIFICULTY);
        bc.add(ObjectUtils.convertObjectToBase64(s), DIFICULTY);
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();
        for (Block b : bc.getChain()) {
            Submission s = (Submission) ObjectUtils.convertBase64ToObject(b.getData());
            txt.append(b.getPreviousHash() + " " +
                    s.toString() + " "
                    + b.getNonce() +" "
                    + b.getCurrentHash()
                    +"\n"
                    );
        }
        return txt.toString();
    }

    public void save(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
//            out.writeObject(this);
            //guarda a blockchain
            out.writeObject(bc);
            //guarda a lista de submissões
            out.writeObject(submissions);
        }
    }

    public static Curriculo load(String fileName) throws IOException, ClassNotFoundException, Exception {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {
            Curriculo curriculo = new Curriculo();
            curriculo.bc = (blockchain.utils.BlockChain) in.readObject(); // Carrega a blockchain
            curriculo.submissions = (List<Submission>) in.readObject(); // Carrega a lista de submissões
            return curriculo;
        }
    }

    public boolean isValid(Submission s) throws Exception {
        if (s.getName().trim().isEmpty()) {
            throw new Exception("Name is empty");
        }
        if (s.getEvent().trim().isEmpty()) {
            throw new Exception("Event is empty");
        }
        return true;
    }

    public void add(Submission s) throws Exception {
        if (isValid(s)) {

            //adiciona a submissão à lista
            submissions.add(s);
            //atualiza a Merkle Tree
            updateMerkleTree();
            //adiciona a merkle rooot na blockchain
            bc.add(merkleTree.getRoot(), DIFICULTY);
            String txtSubmission = ObjectUtils.convertObjectToBase64(s);
            bc.add(txtSubmission, DIFICULTY);
        } else {
            throw new Exception("Submission not valid");
        }
    }

    private void updateMerkleTree() {
        // constrói uma nova Merkle Tree a partir das submissões
        merkleTree = new MerkleTree(submissions.toArray());
    }

    public List<String> getUsers() {
//        ArrayList<String> users = new ArrayList<>();
//        //get Users
//        for (Submission submission : ledger) {
//            if (!users.contains(submission.getName())) {
//                users.add(submission.getName());
//            }
//        }
//        return users;
//    }

        ArrayList<String> users = new ArrayList<>();
        // Iterate through each block in the blockchain
        for (Block block : bc.getChain()) {

            String blockData = block.toString();
            String[] submissions = blockData.split(" - ");

            for (String submissionStr : submissions) {
                Submission submission = Submission.fromString(submissionStr); // Parse submission from string
                users.add(submission.getName()); // Add user to set
            }
        }

        return new ArrayList<>(users); // Convert set to list and return

    }
}
