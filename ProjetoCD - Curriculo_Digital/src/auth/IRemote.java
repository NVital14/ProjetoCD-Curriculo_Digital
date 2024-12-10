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
package auth;

import curriculumdigital.core.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.List;
import p2p.IremoteP2P;

/**
 *
 * @author M@nso
 */
public interface IRemote extends Remote {
    //remote method 
    
    public void setName(String name) throws RemoteException;
    public PublicKey getPub() throws RemoteException;
    public User getUser() throws RemoteException;
    public void logIn(String pass) throws RemoteException;
    public void register( String pass) throws RemoteException;
    public void generateKeys() throws RemoteException;
    public void setInstitute( boolean i) throws RemoteException;
    //:::: N E T WO R K  :::::::::::
    public String getAdress() throws RemoteException;
    public void addNode(IRemote node) throws RemoteException;
    public List<IRemote> getNetwork() throws  RemoteException;
    //::::::::::: T R A NS A C T IO N S  :::::::::::
    public void addTransaction(String data) throws RemoteException;
    public List<String> getTransactions() throws RemoteException;
    public void removeTransaction(String data )throws RemoteException;
    public void sinchronizeTransactions(IRemote node) throws RemoteException;
    public void annonceObjectName(String message) throws RemoteException;
    public void listenObjectName() throws RemoteException;
}
