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

}
