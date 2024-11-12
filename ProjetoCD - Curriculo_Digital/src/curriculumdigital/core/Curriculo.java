/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package curriculumdigital.core;

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.MerkleTree;
import blockchain.utils.ObjectUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Bea⚝
 */
public class Curriculo implements Serializable {

    blockchain.utils.BlockChain bc;
    private MerkleTree merkleTree;
    public List<Submission> submissions;
    public static int DIFICULTY = 4;

    public Curriculo() throws Exception {
        this.submissions = new ArrayList<>();
        this.bc = new BlockChain();
        this.merkleTree = new MerkleTree();
        Submission s = new Submission("Default", "Default", "Default");
        this.bc.add(s.toString(), DIFICULTY);
        bc.add(ObjectUtils.convertObjectToBase64(s), DIFICULTY);
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();
        for (Block b : bc.getChain()) {
            String s = b.getData();
//            Submission s = (Submission) ObjectUtils.convertBase64ToObject(b.getData());
            txt.append("[" + b.getPreviousHash() + " -> "
                    + s.toString() + " "
                    + b.getNonce() + " <- "
                    + b.getCurrentHash()
                    + "] \n"
            );
        }
        return txt.toString();
    }

    public void save(String fileName, boolean isClosing) throws IOException {
        //só cria um bloco quando houver 8 submissões ou o programa for fechado
        if (submissions.size() == 2 || isClosing) {
            try {
                //cria a Merkle Tree com as submissões
                MerkleTree mt = new MerkleTree(submissions.toArray());
                //adicina à blockchain um bloco com a root da Merkle Tree  
                bc.add(mt.getRoot(), (int) DIFICULTY);
                try (ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(fileName))) {
                    //guarda a blockchain
                    out.writeObject(bc);
                    submissions.clear();
                }
                //guarda a Merkle Tree
                mt.saveToFile(bc.getLastBlockHash() + ".mkt");

            } catch (IOException ex) {
                System.out.println("ERRO:" + ex);
            }
        }
    }

    public static Curriculo load(String fileName) throws IOException, ClassNotFoundException, Exception {
        List elements = new ArrayList();
        // vai buscar a blockchain ao ficheiro
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(fileName))) {
            Curriculo c = new Curriculo();
            c.bc = (blockchain.utils.BlockChain) in.readObject();
            List<Block> chain = c.bc.getChain();

            // verifica se a blockchaain tem mais de um bloco
            if (chain.size() > 2) {
                // itera por cada bloco da blockchain, mas ignorar os dois primeiros (são os default)
                for (int i = 2; i < chain.size(); i++) { // Começa do índice 2 (terceiro bloco)
                    Block block = chain.get(i);

                    // Cada bloco tem o hash que usamos para nomear o arquivo da Merkle Tree
                    String merkleTreeFileName = block.getCurrentHash() + ".mkt";

                    // vai buscar a Merkle Tree do ficheiro .mkt
                    c.merkleTree = MerkleTree.loadFromFile(merkleTreeFileName);

                    // verificar se a raiz da Merkle Tree corresponde à hash do bloco
                    if (!c.merkleTree.getRoot().equals(block.getData())) {
                        throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
                    }
                    //adicionar os elementos das Merkle Tree à lista
                    elements.add(c.merkleTree.getElements());
                    MerkleTree mT = new MerkleTree();
                    c.merkleTree = mT;

                    System.out.println("Bloco e Merkle Tree carregados com sucesso: " + block.getCurrentHash());
                }
                c.submissions = elements;
                return c;
            }
            return c;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro" + ex);
        }
        return null;
    }

    public String loadPersonEvents( String n) throws IOException, FileNotFoundException, ClassNotFoundException {
        StringBuilder eventosCurriculo = new StringBuilder();
        List<Block> chain = bc.getChain();
        List <Submission> elements = new ArrayList();

        // verifica se a blockchaain tem mais de um bloco
        if (chain.size() > 2) {
            // itera por cada bloco da blockchain, mas ignorar os dois primeiros (são os default)
            for (int i = 2; i < chain.size(); i++) { // Começa do índice 2 (terceiro bloco)
                Block block = chain.get(i);

                // Cada bloco tem o hash que usamos para nomear o arquivo da Merkle Tree
                String merkleTreeFileName = block.getCurrentHash() + ".mkt";

                // vai buscar a Merkle Tree do ficheiro .mkt
                merkleTree = MerkleTree.loadFromFile(merkleTreeFileName);

                // verificar se a raiz da Merkle Tree corresponde à hash do bloco
                if (!merkleTree.getRoot().equals(block.getData())) {
                    throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
                }
                elements.addAll(merkleTree.getElements());
                for (Submission el : elements) {
                    if(el.getName().equals(n)){
                        eventosCurriculo.append(el.getUser() + " --> " + el.getName() + " - " + el.getEvent() + "\n");
                    }
                }
                elements.clear();
            }
            return eventosCurriculo.toString();
        }
        return null;

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
        } else {
            throw new Exception("Submission not valid");
        }
    }
}
