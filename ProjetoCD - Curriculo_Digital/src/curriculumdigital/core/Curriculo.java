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
import java.nio.file.Path;
import java.nio.file.Paths;
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
//        Submission s = new Submission("Default", "Default", "Default");
//        this.bc.add(s.toString(), DIFICULTY);
//        bc.add(ObjectUtils.convertObjectToBase64(s), DIFICULTY);
    }

//    @Override
//    public String toString() {
//        StringBuilder txt = new StringBuilder();
//        for (Block b : bc.getChain()) {
//            String s = b.getData();
////            Submission s = (Submission) ObjectUtils.convertBase64ToObject(b.getData());
//            txt.append("[" + b.getPreviousHash() + " -> "
//                    + s.toString() + " "
//                    + b.getNonce() + " <- "
//                    + b.getCurrentHash()
//                    + "] \n"
//            );
//        }
//        return txt.toString();
//    }

//    public void save(String fileName, boolean isClosing) throws IOException {
//
//        if (submissions.size() == 8 || isClosing) {
//            try {
//                // Cria a Merkle Tree com as submissões
//                MerkleTree mt = new MerkleTree(submissions.toArray());
//
//                // Adiciona à blockchain um bloco com a root da Merkle Tree  
//                bc.add(mt.getRoot(), (int) DIFICULTY);
//
//                // Salva a blockchain no arquivo especificado
//                Path blockchainFilePath = Paths.get("blockchainfiles", fileName);
//                try (ObjectOutputStream out = new ObjectOutputStream(
//                        new FileOutputStream(blockchainFilePath.toString()))) {
//                    out.writeObject(bc);
//                }
//
//                // Limpa as submissões após salvar
//                submissions.clear();
//
//                // Salva a Merkle Tree na pasta especificado
//                Path merkleTreeFilePath = Paths.get("blockchainfiles", bc.getLastBlockHash() + ".mkt");
//                mt.saveToFile(merkleTreeFilePath.toString());
//
//            } catch (IOException ex) {
//                System.out.println("Erro ao salvar blockchain ou Merkle Tree: " + ex.getMessage());
//                ex.printStackTrace();
//                throw ex; // Relança a exceção para ser tratada por quem chamou o método
//            }
//        }
//    }

    public static Curriculo load(String fileName) throws IOException, ClassNotFoundException, Exception {
        List<Submission> elements = new ArrayList<>();
        Curriculo c = new Curriculo();

        // vai buscar a blockchain ao ficheiro
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(Paths.get("blockchainfiles", fileName).toString()))) {

            c.bc = (blockchain.utils.BlockChain) in.readObject();
            List<Block> chain = c.bc.getChain();

            // verifica se a blockchain tem mais de um bloco
            if (chain.size() > 2) {
                // Itera por cada bloco da blockchain, ignorando os dois primeiros (default)
                for (int i = 2; i < chain.size(); i++) {
                    Block block = chain.get(i);

                    // define o caminho relativo para o arquivo da Merkle Tree
                    Path merkleTreeFilePath = Paths.get("blockchainfiles", block.getCurrentHash() + ".mkt");

                    // vai buscar a Merkle Tree do ficheiro .mkt
                    MerkleTree merkleTree = MerkleTree.loadFromFile(merkleTreeFilePath.toString());

                    // verificar se a raiz da Merkle Tree corresponde à root guardada no bloco
                    if (!merkleTree.getRoot().equals(block.getMerkleRoot())) {
                        throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
                    }

                    // adicionar os elementos da Merkle Tree à lista
                    elements.addAll(merkleTree.getElements());
                }

                // atualiza o Curriculo com os elementos carregados
                c.submissions = elements;
            }
            return c;

        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Erro ao carregar a blockchain: " + ex.getMessage());
            ex.printStackTrace();
        }

        return c;

    }

    public String loadPersonEvents(String n, boolean isShowAll) throws IOException, FileNotFoundException, ClassNotFoundException {
//        StringBuilder eventosCurriculo = new StringBuilder();
//        List<Block> chain = bc.getChain();
//        List<Submission> elements = new ArrayList();
//
//        // itera por cada bloco da blockchain
//        for (int i = 0; i < chain.size(); i++) {
//            Block block = chain.get(i);
//
//            // Cada bloco tem o hash que usamos para nomear o arquivo da Merkle Tree
//            String merkleTreeFileName = "/blockchainfiles/" + block.getCurrentHash() + ".mkt";
//
//            // vai buscar a Merkle Tree do ficheiro .mkt
//            merkleTree = MerkleTree.loadFromFile(merkleTreeFileName);
//
//            // verificar se a raiz da Merkle Tree corresponde à hash do bloco
//            if (!merkleTree.getRoot().equals(block.getData())) {
//                throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
//            }
//            elements.addAll(merkleTree.getElements());
//            for (Submission el : elements) {
//                if (isShowAll == true) {
//                    eventosCurriculo.append(el.getUser() + " --> " + el.getName() + " - " + el.getEvent() + "\n");
//                } else {
//                    if (el.getName().equals(n)) {
//                        eventosCurriculo.append(el.getUser() + " --> " + el.getEvent() + "\n");
//                    }
//                }
//            }
//            elements.clear();
//        }
//        return eventosCurriculo.toString();

        StringBuilder eventosCurriculo = new StringBuilder();
        List<Block> chain = bc.getChain();

// Itera por cada bloco da blockchain
        for (Block block : chain) {
            // Define o nome do arquivo da Merkle Tree
            Path merkleTreeFilePath = Paths.get("blockchainfiles", block.getCurrentHash() + ".mkt");

            // Carrega a Merkle Tree do arquivo
            MerkleTree merkleTree = MerkleTree.loadFromFile(merkleTreeFilePath.toString());

            // Verifica se a raiz da Merkle Tree corresponde à root guardada no bloco
            if (!merkleTree.getRoot().equals(block.getMerkleRoot())) {
                throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
            }

            // Obtém os elementos da Merkle Tree
            List<Submission> elements = merkleTree.getElements();

            // Itera pelos elementos e adiciona os eventos ao StringBuilder
            for (Submission el : elements) {
                if (isShowAll) {
                    eventosCurriculo.append(el.getUser())
                            .append(" --> ")
                            .append(el.getName())
                            .append(" - ")
                            .append(el.getEvent())
                            .append("\n");
                } else if (el.getName().equals(n)) {
                    eventosCurriculo.append(el.getUser())
                            .append(" --> ")
                            .append(el.getEvent())
                            .append("\n");
                }
            }
        }

        return eventosCurriculo.toString();

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
