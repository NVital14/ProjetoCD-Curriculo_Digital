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

import blockchain.utils.RMI;
import curriculumdigital.core.User;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trabalho
 */
public class ORemote extends UnicastRemoteObject implements IRemote {

    User user;
    String multicastAddress = "224.0.0.1";
    int port = 2020;
    String address;
    CopyOnWriteArrayList<IRemote> network;
    CopyOnWriteArraySet<String> transactions;
    P2Plistener listener;

    public ORemote(int port) throws RemoteException, Exception {
        super(port);
        this.user = new User();
    }

    public void annonceObjectName(String message) {

        new Thread(() -> {

            //open multicast socket
            try (DatagramSocket socket = new DatagramSocket()) {
                //packet data
                InetAddress group = InetAddress.getByName(multicastAddress);

                byte[] buffer = message.getBytes();
                //build packet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                while (true) {
                    //dend packet
                    socket.send(packet);
                    System.out.println("message sent : " + message);
                    Thread.sleep(10_000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

    }

    public void listenObjectName() {
        new Thread(() -> {
            //open multicast socket
            try (MulticastSocket socket = new MulticastSocket(port)) {
                //group to listen
                SocketAddress group = new InetSocketAddress(multicastAddress, port);
                //interface to listen
                NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
                //networkInterface = Network.getNetwordIPV4Interfaces().get(0);
                //join group
                socket.joinGroup(group, networkInterface);
                System.out.println("Listen the group: " + multicastAddress);
                //packet to receive data
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                //forever
                while (true) {
                    socket.receive(packet); // wait to packet
                    String add = new String(packet.getData(), 0, packet.getLength());
                    IRemote node = (IRemote) RMI.getRemote(add);
                    this.addNode(node);
                    //extract string  
                    System.out.println("message : " + add);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    @Override
    public String getAdress() throws RemoteException {
        return address;
    }

    private boolean isInNetwork(String adress) {
        for (int i = network.size() - 1; i >= 0; i--) {
            try {
                if (network.get(i).getAdress().equals(adress)) {
                    return true;
                }
            } catch (RemoteException ex) {
                network.remove(i);
            }
        }
        return false;
    }

    @Override
    public void addNode(IRemote node) throws RemoteException {
        //se já tiver o nó 
        // não faz nada
        if (isInNetwork(node.getAdress())) {
            System.out.println("Já tenho o endereço " + node.getAdress());
            return;
        }

        //adicionar o no
        network.add(node);
        listener.onConect(node.getAdress());
        System.out.println("Adicionei o " + node.getAdress());
        node.addNode(this);

        //propagar o no
        for (IRemote iremote : network) {
            iremote.addNode(node);

        }

        sinchronizeTransactions(node);
        System.out.println("Rede p2p");
        for (IRemote iremote : network) {
            System.out.println(iremote.getAdress());

        }

    }

    @Override
    public List<IRemote> getNetwork() throws RemoteException {
        return new ArrayList<>(network);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void addTransaction(String data) throws RemoteException {
        if (transactions.contains(data)) {
            listener.onTransaction("Transaçao repetida " + data);
            System.out.println("Transaçao repetida " + data);
            return;
        }
        transactions.add(data);
        for (IRemote iremote : network) {
            iremote.addTransaction(data);
        }

        System.out.println("Transactions");
        for (String t : transactions) {
            System.out.println(t);
        }

    }

    public List<String> getTransactions() throws RemoteException {
        return new ArrayList<>(transactions);
    }

    public void removeTransaction(String data) throws RemoteException {
        if (!transactions.contains(data)) {
            System.out.println("Transaçao Não existe " + data);
            return;
        }
        transactions.remove(data);
        for (IRemote iremote : network) {
            iremote.removeTransaction(data);
        }
        System.out.println("Transactions");
        for (String t : transactions) {
            System.out.println(t);
        }
    }

    public boolean isListEqual(List<String> other) {
        for (String t : transactions) {
            if (!other.contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void sinchronizeTransactions(IRemote node) throws RemoteException {
//         if( isListEqual(node.getTransactions()))
//             return;
//         //juntar as transações no set
//         transactions.addAll(node.getTransactions());
//         node.sinchronizeTransactions(this);
//         
//         for (IremoteP2P iremoteP2P : network) {
//             iremoteP2P.sinchronizeTransactions(node);        
//         }
//         
//          System.out.println("Transactions");
//        for (String t : transactions) {
//            System.out.println(t);
//        }
        this.transactions.addAll(node.getTransactions());
        listener.onTransaction(address);

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
