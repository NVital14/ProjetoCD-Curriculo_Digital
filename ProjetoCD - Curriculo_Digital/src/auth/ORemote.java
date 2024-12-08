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
package auth;

import blockchain.utils.SecurityUtils;
import curriculumdigital.core.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trabalho
 */
public class ORemote extends UnicastRemoteObject implements IRemote {

    User user;

    public ORemote(int port) throws RemoteException, Exception {
        super(port);
        this.user = new User();
    }

    @Override
    public PublicKey getPub() {
        return this.user.getPub();
    }

    @Override
    public void setName(String name) {
        this.user.setName(name);
    }

    @Override
    public User getUser() {
        return this.user;
    }

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

    @Override
    public void register(String pass) throws RemoteException {
        try {
            this.user.save(pass);
        } catch (ServerNotActiveException ex) {
            System.out.println("Serving anonimous host");
        } catch (Exception ex) {
            Logger.getLogger(ORemote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
