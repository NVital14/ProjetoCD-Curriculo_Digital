//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2022   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package blockchain.utils;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Created on 22/08/2022, 09:23:49
 *
 * Block with consensus of Proof of Work
 *
 * @author IPT - computer
 * @version 1.0
 */
public class Block implements Serializable {

//    String previousHash; // link to previous block
//    String data;         // data in the block
//    int nonce;           // proof of work 
//    String currentHash;  // Hash of block
//
//    public Block(String previousHash, String data, int nonce) {
//        this.previousHash = previousHash;
//        this.data = data;
//        this.nonce = nonce;
//        this.currentHash = calculateHash();
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public String getPreviousHash() {
//        return previousHash;
//    }
//
//    public int getNonce() {
//        return nonce;
//    }
//    
//    
//
//    public String calculateHash() {
//        return Hash.getHash(nonce + previousHash + data);
//    }
//    
//    public String getCurrentHash(){
//        return currentHash;
//    }
//
//    public String toString() {
//        return // (isValid() ? "OK\t" : "ERROR\t")+
//                 String.format("[ %8s", previousHash) + " <- " + 
//                   String.format("%-10s", data) +  String.format(" %7d ] = ", nonce) + 
//                String.format("%8s",currentHash);
//
//    }
//
//    public boolean isValid() {
//        return currentHash.equals(calculateHash());
//    }
    String previousHash; // link to previous block
    String merkleRoot;   // merkleRoot in the block
    MerkleTree merkleTree; //merkleTree das transações do bloco
    List<String> transactions; // transações do bloco (devem ser guardadas em separado)
    int nonce;           // proof of work 
    String currentHash;  // Hash of block
    String time;        //data e hora da criação do bloco

    public Block(String previousHash, List<String> transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;
        merkleTree = new MerkleTree(transactions);
        this.merkleRoot = merkleTree.getRoot();

        // obtém o tempo atual na timezone UTC
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));

        // formatar
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        time = utcNow.format(formatter);
    }

    public void setNonce(int nonce, int zeros) throws Exception {
        this.nonce = nonce;
        //calcular o hash
        this.currentHash = calculateHash();
        //calcular o prefixo
        String prefix = String.format("%0" + zeros + "d", 0);
        if (!currentHash.startsWith(prefix)) {
            throw new Exception(nonce + " not valid Hash=" + currentHash);
        }

    }

    public String getMinerData() {
        return previousHash + merkleRoot;
    }

    public String getTime() {
        return this.time;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public MerkleTree getMerkleTree() {
        return merkleTree;
    }

    public List<String> transactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public String calculateHash() {
        return Miner.getHash(getMinerData(), nonce);
    }

    public String getCurrentHash() {
        return currentHash;
    }

    @Override
    public String toString() {
        return // (isValid() ? "OK\t" : "ERROR\t")+
                String.format("[ %8s", previousHash) + " <- "
                + String.format("%-10s", merkleRoot) + String.format(" %7d ] = ", nonce)
                + String.format("%8s", currentHash);

    }

    public String getHeaderString() {
        return "prev Hash: " + previousHash
                + "\nMkt Root : " + merkleRoot
                + "\nnonce    : " + nonce
                + "\ncurr Hash: " + currentHash;
    }

    public String getTransactionsString() {
        StringBuilder txt = new StringBuilder();
        for (String transaction : transactions) {
            txt.append(transaction + "\n");
        }
        return txt.toString();
    }

    public boolean isValid() {
        return currentHash.equals(calculateHash());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        if (this.nonce != other.nonce) {
            return false;
        }
        if (!Objects.equals(this.previousHash, other.previousHash)) {
            return false;
        }
        if (!Objects.equals(this.merkleRoot, other.merkleRoot)) {
            return false;
        }
        return Objects.equals(this.currentHash, other.currentHash);
    }

    public int compareTo(Block o) {
        return this.currentHash.compareTo(o.currentHash);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202208220923L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2022  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
