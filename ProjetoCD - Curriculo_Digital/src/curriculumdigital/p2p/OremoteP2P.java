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
package curriculumdigital.p2p;

import curriculumdigital.core.Block;
import curriculumdigital.core.BlockChain;
import curriculumdigital.core.MerkleTree;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import curriculumdigital.core.Miner;
import curriculumdigital.utils.RMI;
import curriculumdigital.utils.SecurityUtils;
import curriculumdigital.core.Submission;
import curriculumdigital.core.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    final static String BLOCKCHAIN_FILENAME = "curriculo.obj";
    String address;
    CopyOnWriteArrayList<IremoteP2P> network;
    // Set - conjunto de elementos não repetidos para acesso concorrente
    CopyOnWriteArraySet<Submission> submissions;
    P2Plistener p2pListener;
    //objeto mineiro concorrente e distribuido
    Miner myMiner;
    //objeto da blockchain preparada para acesso concorrente
    BlockChain myBlockchain;
    //objeto User para fazer a autenticação/registo
    User user;
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
        submissions = new CopyOnWriteArraySet<Submission>();
        this.myMiner = new Miner(listener);
        this.myBlockchain = new BlockChain(Paths.get("blockchainfiles", BLOCKCHAIN_FILENAME).toString());
        this.p2pListener = listener;
        this.user = new User();

        try {
            //tenta ler a chave simétrica
            this.aes = SecurityUtils.loadAESKey(Paths.get("blockchainfiles", "secret.key").toString());
        } catch (Exception ex) {

            //cria a chave simétrica
            this.aes = SecurityUtils.generateAESKey(256);
            SecurityUtils.saveKey(aes, "secret.key");

        }
        try {
            //tenta ler a chave pública e privada
            this.kPub = SecurityUtils.loadPublicKey(Paths.get("blockchainfiles", "iremote.pub").toString());
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
     * Define o listener do programa
     *
     * @param l P2Plistener
     */
    @Override
    public void setListener(P2Plistener l) {
        this.p2pListener = l;
    }

    /**
     * Método para obter a chave pública do nó
     *
     * @return chave pública
     */
    @Override
    public PublicKey getPublicKey() {
        return this.kPub;
    }

    /**
     * Método para obter a chave simétrica
     *
     * @param pub chave publica do nó que pede a chave simétrica
     * @return
     * @throws RemoteException
     */
    @Override
    public byte[] getSimKey(PublicKey pub) throws RemoteException {
        try {

            return SecurityUtils.encrypt(aes.getEncoded(), pub);
        } catch (Exception ex) {
            return null;
        }
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
     * Método que verifica se um no está na rede e elimina os que não
     * responderem
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
     * Método que adiciona um novo nó à rede
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
        synchronizeSubmissions(node);
        //sincronizar os ficheiros
        synchnonizeFiles();
        //sincronizar a blockchain
        synchnonizeBlockchain();

    }

    /**
     * Método que devolve uma lista de todos os nós da rede
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
     * Método que devolve o tamanho da lista de submissões
     *
     * @return o tamanho da lista de submissões
     * @throws RemoteException
     */
    @Override
    public int getSubmissionsSize() throws RemoteException {
        return submissions.size();
    }

    /**
     * Método que adiciona uma nova submissão e a propaga para toda a rede
     *
     * @param s submission
     * @throws RemoteException
     */
    @Override
    public void addSubmission(Submission s) throws RemoteException {
        //se já tiver a submissão não faz nada
        for (Submission sub : getSubmissions()) {
            if (sub.equals(s)) {
                p2pListener.onSubmission("Submissão repetida " + s.getName() + " - " + s.getEvent());
                //sair
                return;
            }
        }

        submissions.add(s);

        //Adicionar a submissão aos nós da rede
        if (getNetwork().size() > 1) {
            for (IremoteP2P iremoteP2P : network) {
                iremoteP2P.addSubmission(s);
            }
        }
        p2pListener.onSubmission("Submissão repetida " + s.getName() + " - " + s.getEvent());

    }

    /**
     * Método que devolve as submissões
     *
     * @return submissões
     * @throws RemoteException
     */
    @Override
    public List<Submission> getSubmissions() throws RemoteException {
        return new ArrayList<>(submissions);
    }

    /**
     * Método que sincroniza as submissões entre todos os nós da rede
     *
     * @param node
     * @throws RemoteException
     */
    @Override
    public void synchronizeSubmissions(IremoteP2P node) throws RemoteException {
        //tamanho anterior
        int oldsize = submissions.size();
        p2pListener.onMessage("sinchronizeTransactions", node.getAdress());
        // juntar as submissões todas (SET elimina as repetidas)
        this.submissions.addAll(node.getSubmissions());
        int newSize = submissions.size();
        //se o tamanho for incrementado
        if (oldsize < newSize) {
            p2pListener.onMessage("sinchronizeTransactions", "tamanho diferente");
            //pedir ao no para sincronizar com as nossas
            node.synchronizeSubmissions(this);
            p2pListener.onSubmission(address);
            p2pListener.onMessage("sinchronizeTransactions", "node.sinchronizeTransactions(this)");
            //pedir á rede para se sincronizar
            for (IremoteP2P iremoteP2P : network) {
                //se o tamanho for menor
                if (iremoteP2P.getSubmissionsSize() < newSize) {
                    //sincronizar-se com o no actual
                    p2pListener.onMessage("sinchronizeTransactions", " iremoteP2P.sinchronizeTransactions(this)");
                    iremoteP2P.synchronizeSubmissions(this);
                }
            }
        }

    }

    /**
     * Método que remove todas as submissões
     *
     * @param mySubmissions lista de submissões
     * @throws RemoteException
     */
    @Override
    public void removeSubmissions(List<Submission> mySubmissions) throws RemoteException {
        //remover as transações da lista atual
        submissions.removeAll(mySubmissions);
        p2pListener.onSubmission("remove " + mySubmissions.size() + "transactions");
        //propagar as remoções
        for (IremoteP2P iremoteP2P : network) {
            //se houver algum elemento em comum nas transações remotas
            if (iremoteP2P.getSubmissions().retainAll(submissions)) {
                //remover as transaçoies
                iremoteP2P.removeSubmissions(mySubmissions);
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
//            System.out.println(ex, "startMining");
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
                myBlockchain.save(FOLDER + BLOCKCHAIN_FILENAME);
                p2pListener.onBlockchainUpdate(myBlockchain);
            }
            //guardar a merkle tree 
            String hashmkt = b.getCurrentHash().replace("/", "");
            saveMerkleTree(b.getMerkleTree(), hashmkt);
            p2pListener.onNewCurriculum();
            //propagar o bloco pela rede

            for (IremoteP2P iremoteP2P : network) {
//                String hashmkt = b.getCurrentHash().replace("/", "");
//                iremoteP2P.saveMerkleTree(b.getMerkleTree(), hashmkt);
                //se encaixar na blockcahin dos nodos remotos
                //titei negação !
                if (iremoteP2P.getBlockchainLastHash().equals(b.getPreviousHash())
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
            //sincronizar ficheiros para todos os nós da redes ficarem com a merkle tree
            //synchnonizeFiles();

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
     * Método para obter as submissões da blockchain
     *
     * @return submissões
     * @throws RemoteException
     */
    @Override
    public List<Submission> getBlockchainSubmissions() throws RemoteException {
        ArrayList<Submission> allSubmissions = new ArrayList<>();
        for (Block b : myBlockchain.getChain()) {
            String hashmkt = b.getCurrentHash().replace("/", "");
            // define o caminho relativo para o arquivo da Merkle Tree
            Path merkleTreeFilePath = Paths.get("blockchainfiles", hashmkt + ".mkt");

            // vai buscar a Merkle Tree do ficheiro .mkt
            MerkleTree merkleTree = null;
            try {
                merkleTree = MerkleTree.loadFromFile(merkleTreeFilePath.toString());
            } catch (IOException ex) {
                Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
            }

            // verificar se a raiz da Merkle Tree corresponde à root guardada no bloco
            if (merkleTree != null && !merkleTree.getRoot().equals(b.getMerkleRoot())) {
                throw new RuntimeException("Erro: a Merkle Tree não corresponde ao bloco!");
            }

            // adicionar os elementos da Merkle Tree à lista
            allSubmissions.addAll(merkleTree.getElements());
        }
        return allSubmissions;
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
        Path merkleTreeFilePath = Paths.get("blockchainfiles", hash + ".mkt");
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
        String[] myFiles = new File(FOLDER).list();
        List listMyFiles = Arrays.asList(myFiles);
        if (network.size() > 1) {
            //percorre todos os nós
            for (IremoteP2P iremote : network) {
                //vai buscar os ficheiros do nó
                String[] iremoteFiles = iremote.getFiles();
                List listIremoteFiles = Arrays.asList(iremoteFiles);
                //guardar nos outros nós os ficheiros que eu tenho e eles não têm
                for (String f : myFiles) {
                    //verifica se o nó tem o ficheiro
                    if (!"secret.key".equals(f) && !"iremote.pub".equals(f) && !"iremote.priv".equals(f)) {
                        if (!listIremoteFiles.contains(f)) {
                            //vai buscar o ficheiro
                            byte[] fileToSave = getFileBytes(f);
                            if (fileToSave != null) {
                                try {
                                    //iremote guarda o ficheiro e envia a chave simétrica usada para encriptar o ficheiro, 
                                    //encriptada com a chave pública do iremote
                                    iremote.saveFile(fileToSave, f, SecurityUtils.encrypt(aes.getEncoded(), iremote.getPublicKey()));
                                } catch (Exception ex) {
                                    Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    }
                }
                //guardar os ficheiros que os outros têm e eu não
                for (String f : iremoteFiles) {
                    if (!"secret.key".equals(f) && !"iremote.pub".equals(f) && !"iremote.priv".equals(f)) {
                        //verifica se eu tenho o ficheiro
                        if (!listMyFiles.contains(f)) {
                            //vai buscar o ficheiro
                            byte[] fileToSave = iremote.getFileBytes(f);
                            if (fileToSave != null) {
                                //vai buscar a chave simétrica usada para encriptar o ficheiro
                                byte[] simKey = iremote.getSimKey(kPub);
                                //guardo o ficheiro e envio a chave pública usada para encriptar o ficheiro
                                //encriptada com a minha chave pública
                                saveFile(fileToSave, f, simKey);
                            }

                        }
                    }
                }
            }

        }
        p2pListener.onUpdateUsers();

    }

    /**
     * Método que vai buscar os bytes de um ficheiro
     *
     * @param nameFile nome do ficheiro
     * @return um array de bytes do ficheiro ou null
     * @throws RemoteException
     */
    @Override
    public byte[] getFileBytes(String nameFile) throws RemoteException {
        byte[] fBytes;
        try {
            fBytes = Files.readAllBytes(Path.of(FOLDER, nameFile));
            //encriptar o ficheiro com a chave simétrica
            byte[] fBytesEncrypt = SecurityUtils.encrypt(fBytes, aes);
            return fBytesEncrypt;
        } catch (IOException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
    public void saveFile(byte[] f, String nameFile, byte[] k) throws RemoteException {

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
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //::::::::::::::::: C U R S O S:::::::::::::::::::::::::::::::::::::::::::
    /**
     * Guardar um curso associado a uma instituição num ficheiro
     *
     * @param instituicao o nome da instituição, a que pertence o curso
     * @param curso nome do curso
     */
    @Override
    public void addCurso(String instituicao, String curso) throws RemoteException {
        File file = new File(FOLDER + instituicao + ".cursos");

        // cria o arquivo ficheiro se ele ainda não existir
        if (!file.exists()) {
            try {
                file.createNewFile();
                if (getNetwork().size() > 1) {
                    synchnonizeFiles();
                }
            } catch (IOException ex) {
                Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<String> cursos = loadCursos(instituicao); // vai buscar os cursos existentes (ou lista vazia)
        cursos.add(curso);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(cursos); // guarda a lista atualizada
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        p2pListener.onNewCurso();
        if (getNetwork().size() > 1) {
            for (IremoteP2P iremote : network) {
                List<String> l = iremote.loadCursos(instituicao);
                if (!l.contains(curso)) {
                    iremote.addCurso(instituicao, curso);
                }
            }
        }
    }

    /**
     * Carrega todos os cursos associados a uma instituição a partir de um
     * ficheiro
     *
     * @param instituicao o nome da instituição, usado para procurar o ficheiro
     * @return uma lista com o nome de todos os cursos Retorna uma lista vazia
     * se o ficheiro não existir
     */
    @Override
    public List<String> loadCursos(String instituicao) throws RemoteException {
        File file = new File(FOLDER + instituicao + ".cursos");
        if (!file.exists()) {
            return new ArrayList<>(); // nenhum curso registado ainda
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<String>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OremoteP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
}
