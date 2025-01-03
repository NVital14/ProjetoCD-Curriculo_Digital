//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
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

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.MerkleTree;
import curriculumdigital.core.Submission;
import curriculumdigital.core.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.Key;
import java.security.PublicKey;
import java.util.List;

/**
 * Created on 27/11/2024, 17:42:15
 *
 * @author manso - computer
 */
public interface IremoteP2P extends Remote {

    public void setListenerProgram(P2PlistenerProgram l) throws RemoteException;

    public PublicKey getPublicKey() throws RemoteException;

    public byte[] getSimKey(PublicKey pub) throws RemoteException;

    //:::: N E T WO R K  :::::::::::
    public String getAdress() throws RemoteException;

    public void addNode(IremoteP2P node) throws RemoteException;

    public List<IremoteP2P> getNetwork() throws RemoteException;

    //::::::::::: S U B M I S S I O N S  :::::::::::
    public int getSubmissionsSize() throws RemoteException;

    public void addSubmission(Submission s) throws RemoteException;

    public List<Submission> getSubmissions() throws RemoteException;

    public void removeSubmissions(List<Submission> submissions) throws RemoteException;

    public void synchronizeSubmissions(IremoteP2P node) throws RemoteException;

    //::::::::::::::::: M I N E R :::::::::::::::::::::::::::::::::::::::::::
    public void startMining(String msg, int zeros) throws RemoteException;

    public void stopMining(int nonce) throws RemoteException;

    public boolean isMining() throws RemoteException;

    public int mine(String msg, int zeros) throws RemoteException;

    //::::::::::::::::: B L O C K C H A I N :::::::::::::::::::::::::::::::::::::::::::
    public void addBlock(Block b) throws RemoteException;

    public int getBlockchainSize() throws RemoteException;

    public String getBlockchainLastHash() throws RemoteException;

    public BlockChain getBlockchain() throws RemoteException;

    public void synchnonizeBlockchain() throws RemoteException;

    public List<Submission> getBlockchainSubmissions() throws RemoteException;

    public void saveMerkleTree(MerkleTree mkt, String hash) throws RemoteException;

    //::::::::::::::::::::::SINCRONIZAR FICHEIROS:::::::::::::::::::::::
    public String[] getFiles() throws RemoteException;

    public void synchnonizeFiles() throws RemoteException;

    public byte[] getFileBytes(String nameFile) throws RemoteException;

    public void saveFile(byte[] f, String nameFile, byte[] k) throws RemoteException;

//::::::::::::::::: A U T H :::::::::::::::::::::::::::::::::::::::::::
    public void setName(String name) throws RemoteException;

    public PublicKey getPub() throws RemoteException;

    public User getUser() throws RemoteException;

    public void logIn(String pass) throws RemoteException;

    public void register(String pass) throws RemoteException;

    public void generateKeys() throws RemoteException;

    public void setInstitute(boolean i) throws RemoteException;

    //::::::::::::::::: C U R S O S:::::::::::::::::::::::::::::::::::::::::::
    public void addCurso(String instituicao, String curso) throws RemoteException;

    public List<String> loadCursos(String instituicao) throws RemoteException;
}
