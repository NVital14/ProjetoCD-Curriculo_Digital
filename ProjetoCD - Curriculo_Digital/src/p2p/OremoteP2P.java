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
//::                                                               (c)2024   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package p2p;

import auth.ORemote;
import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.MerkleTree;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import blockchain.utils.Miner;
import blockchain.utils.RMI;
import blockchain.utils.SecurityUtils;
import curriculumdigital.core.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 27/11/2024, 17:48:32
 *
 * @author manso - computer
 */
public class OremoteP2P extends UnicastRemoteObject implements IremoteP2P {

    final static String FOLDER = "blockchainfiles/";
    final static String BLOCHAIN_FILENAME = "curriculo.obj";
    String address;
    CopyOnWriteArrayList<IremoteP2P> network;
    // Set - conjunto de elementos não repetidos para acesso concorrente
    CopyOnWriteArraySet<String> transactions;
    P2Plistener p2pListener;
    //objeto mineiro concorrente e distribuido
    Miner myMiner;
    //objeto da blockchain preparada para acesso concorrente
    BlockChain myBlockchain;
    //objeto User para fazer a autenticação/registo
    User user;
    //objeto
    String[] files;
    //chave simétrica para encriptar os ficheiros
    Key aes;
    //chave pública
    PublicKey kPub;
    //chave privada
    PrivateKey kPriv;

    public OremoteP2P(String address, P2Plistener listener) throws RemoteException, Exception {
        super(RMI.getAdressPort(address));
        this.address = address;
        this.network = new CopyOnWriteArrayList<>();
        transactions = new CopyOnWriteArraySet<>();
        this.myMiner = new Miner(listener);
        this.myBlockchain = new BlockChain(FOLDER + BLOCHAIN_FILENAME);
        this.p2pListener = listener;
        this.user = new User();
        this.files = new File(FOLDER).list();
        try {
            //tenta ler a chave simétrica
            this.aes = SecurityUtils.loadAESKey(FOLDER + "secret.key");
        } catch (Exception ex) {

            //cria a chave simétrica
            this.aes = SecurityUtils.generateAESKey(256);
            SecurityUtils.saveKey(aes, "secret.key");

        }
        try {
            //tenta ler a chave pública e privada
            this.kPub = SecurityUtils.loadPublicKey(FOLDER + "iremote.pub");
            this.kPriv = SecurityUtils.loadPrivateKey(FOLDER + "iremote.priv");
        } catch (Exception ex) {
            //cria um par de chave
            KeyPair kp = SecurityUtils.generateRSAKeyPair(2048);
            this.kPub = kp.getPublic();
            this.kPriv = kp.getPrivate();
            //guarda a chave pública
            SecurityUtils.saveKey(kPub, "iremote.pub");
            //guarda a chave privada
            SecurityUtils.saveKey(kPriv, "iremote.priv");

        }
        listener.onStartRemote("Object " + address + " listening");

    }

    /**
     * Método para obter a chave pública do nó
     * @return chave pública
     */
    @Override
    public PublicKey getPublicKey() {
        return this.kPub;
    }

    /**
     * Método para mudar a variável ficheiros
     * @param f 
     */
    @Override
    public void setFiles(String[] f) {
        this.files = f;
    }

    /**
     * Método para obter o endereço
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getAdress() throws RemoteException {
        return address;
    }

    /**
     * Método que verifica se um no está na rede e elmina os que não responderem
     *
     * @param adress endereço do no
     * @return true se estiver na rede falso caso contrario
     */
    private boolean isInNetwork(String adress) {
        //fazer o acesso iterado pelo fim do array para remover os nos inativos
        for (int i = network.size() - 1; i >= 0; i--) {
            try {
                //se o no responder e o endereço for igaul
                if (network.get(i).getAdress().equals(adress)) {
                    // no esta na rede 
                    return true;
                }
            } catch (RemoteException ex) {
                //remover os nós que não respondem
                network.remove(i);
            }
        }
        return false;
    }

    /**
     * Método que adiciona um novo nó à redes
     *
     * @param node
     * @throws RemoteException
     */
    @Override
    public void addNode(IremoteP2P node) throws RemoteException {
        //se já tiver o nó  ---  não faz nada
        if (isInNetwork(node.getAdress())) {
            return;
        }
        p2pListener.onMessage("Network addNode ", node.getAdress());
        //adicionar o no á rede
        network.add(node);

        p2pListener.onConect(node.getAdress());
        // pedir ao no para nos adicionar
        node.addNode(this);
        //propagar o no na rede
        for (IremoteP2P iremoteP2P : network) {
            iremoteP2P.addNode(node);
        }

        //sicronizar as transaçoes
        synchronizeTransactions(node);
        //sincronizar a blockchain
        synchnonizeBlockchain();

    }

    /**
     * Método que dá a todos os nós da redes
     *
     * @return Lista de todos os nós da rede
     * @throws RemoteException
     */
    @Override
    public List<IremoteP2P> getNetwork() throws RemoteException {
        return new ArrayList<>(network);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::            T R A N S A C T I O N S       ::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * Método que devolve o tamanho das transações
     *
     * @return o tamanho das transações
     * @throws RemoteException
     */
    public int getTransactionsSize() throws RemoteException {
        return transactions.size();
    }

    /**
     * Método que adiciona transações a adiciona a mesma a todos os nós da rede
     *
     * @param data transacao
     * @throws RemoteException
     */
    public void addTransaction(String data) throws RemoteException {
        //seja tiver a transacao não faz nada
        if (transactions.contains(data)) {
            p2pListener.onTransaction("Transaçao repetida " + data);
            //sair
            return;
        }
        //Adicionar a transaçao ao no local
        transactions.add(data);
        //Adicionar a transacao aos nos da rede
        for (IremoteP2P iremoteP2P : network) {
            iremoteP2P.addTransaction(data);
        }

    }

    /**
     * Método que devolve as transações
     *
     * @return transações
     * @throws RemoteException
     */
    @Override
    public List<String> getTransactions() throws RemoteException {
        return new ArrayList<>(transactions);
    }

    /**
     * Método que sincroniza as transações entre todos os nós da rede
     *
     * @param node
     * @throws RemoteException
     */
    @Override
    public void synchronizeTransactions(IremoteP2P node) throws RemoteException {
        //tamanho anterior
        int oldsize = transactions.size();
        p2pListener.onMessage("sinchronizeTransactions", node.getAdress());
        // juntar as transacoes todas (SET elimina as repetidas)
        this.transactions.addAll(node.getTransactions());
        int newSize = transactions.size();
        //se o tamanho for incrementado
        if (oldsize < newSize) {
            p2pListener.onMessage("sinchronizeTransactions", "tamanho diferente");
            //pedir ao no para sincronizar com as nossas
            node.synchronizeTransactions(this);
            p2pListener.onTransaction(address);
            p2pListener.onMessage("sinchronizeTransactions", "node.sinchronizeTransactions(this)");
            //pedir á rede para se sincronizar
            for (IremoteP2P iremoteP2P : network) {
                //se o tamanho for menor
                if (iremoteP2P.getTransactionsSize() < newSize) {
                    //cincronizar-se com o no actual
                    p2pListener.onMessage("sinchronizeTransactions", " iremoteP2P.sinchronizeTransactions(this)");
                    iremoteP2P.synchronizeTransactions(this);
                }
            }
        }

    }

    /**
     * Método que remove todas as transações
     *
     * @param myTransactions lista de transações
     * @throws RemoteException
     */
    @Override
    public void removeTransactions(List<String> myTransactions) throws RemoteException {
        //remover as transações da lista atual
        transactions.removeAll(myTransactions);
        p2pListener.onTransaction("remove " + myTransactions.size() + "transactions");
        //propagar as remoções
        for (IremoteP2P iremoteP2P : network) {
            //se houver algum elemento em comum nas transações remotas
            if (iremoteP2P.getTransactions().retainAll(transactions)) {
                //remover as transaçoies
                iremoteP2P.removeTransactions(myTransactions);
            }
        }

    }
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::      M I N E R   :::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Método que começa a minração
     *
     * @param msg mensagem a ser minada
     * @param zeros dificultadade
     * @throws RemoteException
     */
    @Override
    public void startMining(String msg, int zeros) throws RemoteException {
        try {
            //colocar a mineiro a minar
            myMiner.startMining(msg, zeros);
            p2pListener.onStartMining(msg, zeros);
            //mandar minar a rede
            for (IremoteP2P iremoteP2P : network) {
                //se o nodo nao estiver a minar
                if (!iremoteP2P.isMining()) {
                    p2pListener.onStartMining(iremoteP2P.getAdress() + " mining", zeros);
                    //iniciar a mineracao no nodo
                    iremoteP2P.startMining(msg, zeros);
                }
            }
        } catch (Exception ex) {
            p2pListener.onException(ex, "startMining");
        }

    }

    /**
     * Método que para a mineração
     *
     * @param nonce
     * @throws RemoteException
     */
    @Override
    public void stopMining(int nonce) throws RemoteException {
        //parar o mineiro e distribuir o nonce
        myMiner.stopMining(nonce);
        //mandar parar a rede
        for (IremoteP2P iremoteP2P : network) {
            //se o nodo estiver a minar   
            if (iremoteP2P.isMining()) {
                //parar a mineração no nodo 
                iremoteP2P.stopMining(nonce);
            }
        }
    }

    /**
     * Método que inicia a minar e espera pelo nonce
     *
     * @param msg mensagem a ser minada
     * @param zeros dificuldade
     * @return
     * @throws RemoteException
     */
    @Override
    public int mine(String msg, int zeros) throws RemoteException {
        try {
            //começar a minar a mensagem
            startMining(msg, zeros);
            //esperar que o nonce seja calculado
            return myMiner.waitToNonce();
        } catch (InterruptedException ex) {
            p2pListener.onException(ex, "Mine");
            return -1;
        }

    }

    /**
     * Método que verifica se estamos a minar
     *
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean isMining() throws RemoteException {
        return myMiner.isMining();
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::: B L O C K C H A I N :::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Método que adiciona u bloco à blockchain
     *
     * @param b bloco a ser adicionado
     * @throws RemoteException
     */
    @Override
    public void addBlock(Block b) throws RemoteException {
        try {
            //se não for válido
            if (!b.isValid()) {
                throw new RemoteException("invalid block");
            }
            //se encaixar adicionar o bloco
            if (myBlockchain.getLastBlockHash().equals(b.getPreviousHash())) {
                myBlockchain.add(b);
                //guardar a blockchain
                myBlockchain.save(BLOCHAIN_FILENAME);
                //////MERKLE TREE////////
                //guardar a merkle tree
//               
//                MerkleTree mkt = b.getMerkleTree();
//                Path merkleTreeFilePath = Paths.get("blockchainfiles", myBlockchain.getLastBlockHash() + ".mkt");
//                mkt.saveToFile(merkleTreeFilePath.toString());

                p2pListener.onBlockchainUpdate(myBlockchain);
            }
            //propagar o bloco pela rede
            for (IremoteP2P iremoteP2P : network) {
                //guardar a merkle tree em todos os nós da redes
                iremoteP2P.saveMerkleTree(b.getMerkleTree(), myBlockchain.getLastBlockHash());
                //se encaixar na blockcahin dos nodos remotos
                if (!iremoteP2P.getBlockchainLastHash().equals(b.getPreviousHash())
                        || //ou o tamanho da remota for menor
                        iremoteP2P.getBlockchainSize() < myBlockchain.getSize()) {
                    //adicionar o bloco ao nodo remoto
                    iremoteP2P.addBlock(b);
                }
            }
            //se não encaixou)
            if (!myBlockchain.getLastBlockHash().equals(b.getCurrentHash())) {
                //sincronizar a blockchain
                synchnonizeBlockchain();
            }

        } catch (Exception ex) {
            p2pListener.onException(ex, "Add bloco " + b);
        }
    }

    /**
     * Método para obter o tamanho da blockchain
     *
     * @return tamanho da blockchain
     * @throws RemoteException
     */
    @Override
    public int getBlockchainSize() throws RemoteException {
        return myBlockchain.getSize();
    }

    /**
     * Método para obter o Hash do último bloco
     *
     * @return hash do último bloco
     * @throws RemoteException
     */
    @Override
    public String getBlockchainLastHash() throws RemoteException {
        return myBlockchain.getLastBlockHash();
    }

    /**
     * Método para obter a blockchain
     *
     * @return blockchain
     * @throws RemoteException
     */
    @Override
    public BlockChain getBlockchain() throws RemoteException {
        return myBlockchain;
    }

    /**
     * Método que sincroniza a blockchain entre todos os nós
     *
     * @throws RemoteException
     */
    @Override
    public void synchnonizeBlockchain() throws RemoteException {
        //para todos os nodos da rede
        for (IremoteP2P iremoteP2P : network) {
            //se a blockchain for maior
            if (iremoteP2P.getBlockchainSize() > myBlockchain.getSize()) {
                BlockChain remote = iremoteP2P.getBlockchain();
                //e a blockchain for válida
                if (remote.isValid()) {
                    //atualizar toda a blockchain
                    myBlockchain = remote;
                    //deveria sincronizar apenas os blocos que faltam
                    p2pListener.onBlockchainUpdate(myBlockchain);
                }
            }
        }
    }

    /**
     * Método para obter as transações da blockchain
     *
     * @return transações
     * @throws RemoteException
     */
    @Override
    public List<String> getBlockchainTransactions() throws RemoteException {
        ArrayList<String> allTransactions = new ArrayList<>();
        for (Block b : myBlockchain.getChain()) {
            allTransactions.addAll(b.transactions());
        }
        return allTransactions;
    }

    /**
     * Método para guardar a merkle tree num ficheiro
     *
     * @param mkt merkle tree
     * @param hash hash do bloco
     * @throws RemoteException
     */
    @Override
    public void saveMerkleTree(MerkleTree mkt, String hash) throws RemoteException {
        Path merkleTreeFilePath = Paths.get(FOLDER, hash + ".mkt");
        try {
            mkt.saveToFile(merkleTreeFilePath.toString());
        } catch (IOException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para obter os ficheiros de um nó
     *
     * @return nomes de ficheiros
     * @throws RemoteException
     */
    @Override
    public String[] getFiles() throws RemoteException {
        String[] f = new File(FOLDER).list();
        return f;
    }

    /**
     * Método que sincroniza os ficheiro entre todos os nós da rede
     *
     * @throws RemoteException
     */
    @Override
    public void synchnonizeFiles() throws RemoteException {
        setFiles(new File(FOLDER).list());
        //percorre todos os nós
        for (IremoteP2P iremote : network) {
            //vai buscar os ficheiros do nó
            String[] iremoteFiles = iremote.getFiles();
            List listIremoteFiles = Arrays.asList(iremoteFiles);
            for (String f : files) {
                //verifica se o nó tem o ficheiro
                if (!listIremoteFiles.contains(f)) {
                    //guarda o ficheiro
                    byte[] fBytes;
                    try {
                        fBytes = Files.readAllBytes(Path.of(FOLDER, f));
                        //encriptar o ficheiro com a chave simétrica
                        byte[] fBytesEncrypt = SecurityUtils.encrypt(fBytes, aes);
                        //enviar o ficheiro encriptado e a chave simétrica encriptada com a chave pública do iremote
                        iremote.saveFiles(fBytesEncrypt, f, SecurityUtils.encrypt(aes.getEncoded(), iremote.getPublicKey()));
                    } catch (IOException ex) {
                        Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
    }

    /**
     * Método para guardar ficheiros
     *
     * @param f bytes do ficheiro
     * @param nameFile nome do ficheiro a guardar
     * @param k chave simétrica encriptada com a pública
     * @param i iremoteP2P de onde vem o ficheiro
     * @throws RemoteException
     */
    @Override
    public void saveFiles(byte[] f, String nameFile, byte[] k) throws RemoteException {

        try {
            //desencriptar a chave simétrica, com a chave privada
            k = SecurityUtils.decrypt(k, kPriv);
            //fazer a chave
            Key serverKey = SecurityUtils.getAESKey(k);
            //desencriptar ficheiro com a chave simétrica
            byte[] fDecrypt = SecurityUtils.decrypt(f, serverKey);
            //guardar ficheiro
            Files.write(Path.of(FOLDER, nameFile), fDecrypt);
        } catch (IOException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para obter a chave simétrica
     *
     * @param pub chave publica do nó
     * @return
     * @throws RemoteException
     */
    @Override
    public byte[] getKeySim(PublicKey pub) throws RemoteException {
        try {
            return SecurityUtils.encrypt(aes.getEncoded(), pub);
        } catch (Exception ex) {
            return null;
        }
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::: A U T H :::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //////////////////////////////////////////////////////////////////////////////
    /**
     * Método para ir buscar a chave pública do utilizador
     *
     * @return chave pública
     */
    @Override
    public PublicKey getPub() {
        return this.user.getPub();
    }

    /**
     * Método que define o nome do user
     *
     * @param name nome do user
     */
    @Override
    public void setName(String name) {
        this.user.setName(name);
    }

    /**
     * Método para ir buscar o user
     *
     * @return user
     */
    @Override
    public User getUser() {
        return this.user;
    }

    /**
     * Método para autenticar utilizador no sistema
     *
     * @param pass palavra-passe
     * @throws RemoteException
     */
    @Override
    public void logIn(String pass) throws RemoteException {
        try {
            this.user.load(pass);
        } catch (ServerNotActiveException ex) {
            System.out.println("Serving anonimous host");
        } catch (Exception ex) {
            Logger.getLogger(ORemote.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método para registar um utilizador no sistema
     *
     * @param pass palavra-passe
     * @throws RemoteException
     */
    @Override
    public void register(String pass) throws RemoteException {
        try {
            this.user.save(pass);
            synchnonizeFiles();
        } catch (ServerNotActiveException ex) {
            System.out.println("Serving anonimous host");
        } catch (Exception ex) {
            Logger.getLogger(ORemote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que gera um par de chaves
     *
     * @throws RemoteException
     */
    @Override
    public void generateKeys() throws RemoteException {
        try {
            this.user.generateKeys();
        } catch (ServerNotActiveException ex) {
            System.out.println("Serving anonimous host");
        } catch (Exception ex) {
            Logger.getLogger(ORemote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para definir se um utilizador é instuto
     *
     * @param i boolean
     * @throws RemoteException
     */
    @Override
    public void setInstitute(boolean i) throws RemoteException {
        try {
            this.user.setInstitute(i);
            System.out.println("Serving " + RemoteServer.getClientHost());
        } catch (ServerNotActiveException ex) {
            System.out.println("Serving anonimous host");
        } catch (Exception ex) {
            Logger.getLogger(ORemote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
