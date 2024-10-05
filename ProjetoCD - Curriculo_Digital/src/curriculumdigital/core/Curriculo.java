/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.Hash;
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
    public static int DIFICULTY = 4;

    public Curriculo() {
        Submission s = new Submission("Default", "Default");
        bc = new BlockChain();

        bc.add(s.toString(), DIFICULTY);
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();
        for (Block b : bc.getChain()) {
            txt.append(b.toString());
        }
        return txt.toString();
    }

    public void save(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    public static Curriculo load(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {
            return (Curriculo) in.readObject();
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
            bc.add(s.toString(), DIFICULTY);
        } else {
            throw new Exception("Submission not valid");
        }
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
