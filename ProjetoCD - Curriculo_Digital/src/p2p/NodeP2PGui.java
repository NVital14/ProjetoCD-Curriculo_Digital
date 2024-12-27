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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package p2p;

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.GuiUtils;
import blockchain.utils.Miner;
import blockchain.utils.RMI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import blockchain.utils.Miner;
import blockchain.utils.GuiUtils;
import blockchain.utils.RMI;

/**
 *
 * @author zulu
 */
public class NodeP2PGui extends javax.swing.JFrame implements P2Plistener {

    OremoteP2P myremoteObject;

    String multicastAddress = "224.0.0.1"; // multicast Address
    int port = 5000; // multicast port

    public NodeP2PGui(int port) {
        this();
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int largura = (int) tamanhoTela.getWidth() / 6;
        int altura = (int) tamanhoTela.getHeight() / 6;
        setLocation(largura * port, altura * port);

        txtPort.setText(10010 + port + "");
        btStartServerActionPerformed(null);

        txtTranaction.setText(txtTranaction.getText() + port);
        btAddTransactionActionPerformed(null);

    }

    /**
     * Creates new form MessengerGUI
     */
    public NodeP2PGui() {
        initComponents();
        try {
            txtAddress.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            txtAddress.setText("Localhost");
        }
    }

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tpMain = new javax.swing.JTabbedPane();
        pnServer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btStartServer = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtAddress = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtObjectName = new javax.swing.JTextField();
        imgServerRunning = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtServerLog = new javax.swing.JTextPane();
        pnNetwork = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNetwork = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        txtNodeAddress = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        pnTransaction = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btAddTransaction = new javax.swing.JButton();
        txtTranaction = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtListTransdactions = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtLogMining = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        lblMining = new javax.swing.JLabel();
        lblWinner = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        btMining = new javax.swing.JButton();
        spZeros = new javax.swing.JSpinner();
        pnBlockchain = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtBlockHeader = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtBlockTransactions = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        lstBlcockchain = new javax.swing.JList<>();
        pnAbout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtExceptionLog = new javax.swing.JLabel();
        txtTimeLog = new javax.swing.JLabel();
        txtTitleLog = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnServer.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout(10, 10));

        btStartServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/startServer.png"))); // NOI18N
        btStartServer.setText("Start");
        btStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartServerActionPerformed(evt);
            }
        });
        jPanel3.add(btStartServer, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.GridLayout(3, 0));

        txtAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAddress.setText("localhost");
        txtAddress.setBorder(javax.swing.BorderFactory.createTitledBorder("Address"));
        txtAddress.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel7.add(txtAddress);

        txtPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPort.setText("10010");
        txtPort.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Number"));
        txtPort.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel7.add(txtPort);

        txtObjectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtObjectName.setText("remoteP2P");
        txtObjectName.setBorder(javax.swing.BorderFactory.createTitledBorder("ObjectName"));
        jPanel7.add(txtObjectName);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        imgServerRunning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/loading_green.gif"))); // NOI18N
        imgServerRunning.setEnabled(false);
        jPanel3.add(imgServerRunning, java.awt.BorderLayout.EAST);

        pnServer.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        txtServerLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Server"));
        txtServerLog.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(txtServerLog);

        pnServer.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tpMain.addTab("Server", new javax.swing.ImageIcon(getClass().getResource("/multimedia/server_32.png")), pnServer); // NOI18N

        pnNetwork.setLayout(new java.awt.BorderLayout());

        txtNetwork.setEditable(false);
        txtNetwork.setColumns(20);
        txtNetwork.setRows(5);
        txtNetwork.setBorder(javax.swing.BorderFactory.createTitledBorder("Network Nodes"));
        jScrollPane2.setViewportView(txtNetwork);

        pnNetwork.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.BorderLayout());

        txtNodeAddress.setText("//10.10.208.35:10010/remoteP2P");
        txtNodeAddress.setBorder(javax.swing.BorderFactory.createTitledBorder("Remote Object Address"));
        txtNodeAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNodeAddressActionPerformed(evt);
            }
        });
        jPanel5.add(txtNodeAddress, java.awt.BorderLayout.CENTER);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/startClient.jpg"))); // NOI18N
        jButton1.setText("Connect");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, java.awt.BorderLayout.WEST);

        pnNetwork.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        tpMain.addTab("P2pNetwork", new javax.swing.ImageIcon(getClass().getResource("/multimedia/p2p_32.png")), pnNetwork); // NOI18N

        pnTransaction.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        btAddTransaction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/transaction_64.png"))); // NOI18N
        btAddTransaction.setText("Add");
        btAddTransaction.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btAddTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddTransactionActionPerformed(evt);
            }
        });
        jPanel6.add(btAddTransaction, java.awt.BorderLayout.EAST);

        txtTranaction.setText("transaction to network");
        txtTranaction.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction"));
        jPanel6.add(txtTranaction, java.awt.BorderLayout.CENTER);

        pnTransaction.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel12.setLayout(new java.awt.BorderLayout());

        txtListTransdactions.setEditable(false);
        txtListTransdactions.setColumns(20);
        txtListTransdactions.setRows(5);
        txtListTransdactions.setText("transaction");
        txtListTransdactions.setBorder(javax.swing.BorderFactory.createTitledBorder("List of Transactions"));
        jScrollPane3.setViewportView(txtListTransdactions);

        jPanel12.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new java.awt.BorderLayout());

        txtLogMining.setEditable(false);
        txtLogMining.setColumns(20);
        txtLogMining.setRows(5);
        txtLogMining.setBorder(javax.swing.BorderFactory.createTitledBorder("Miner Log"));
        jScrollPane5.setViewportView(txtLogMining);

        jPanel11.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel13.setLayout(new java.awt.BorderLayout());

        lblMining.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/working.gif"))); // NOI18N
        lblMining.setText("mining");
        jPanel13.add(lblMining, java.awt.BorderLayout.CENTER);

        lblWinner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/winner.gif"))); // NOI18N
        jPanel13.add(lblWinner, java.awt.BorderLayout.EAST);

        jPanel11.add(jPanel13, java.awt.BorderLayout.SOUTH);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.BorderLayout());

        btMining.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/mining.png"))); // NOI18N
        btMining.setText("Start Mining");
        btMining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMiningActionPerformed(evt);
            }
        });
        jPanel14.add(btMining, java.awt.BorderLayout.CENTER);

        spZeros.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spZeros.setModel(new javax.swing.SpinnerNumberModel(3, 0, 6, 1));
        spZeros.setBorder(javax.swing.BorderFactory.createTitledBorder("Nº Zeros"));
        jPanel14.add(spZeros, java.awt.BorderLayout.EAST);

        jPanel10.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel12.add(jPanel10, java.awt.BorderLayout.WEST);

        pnTransaction.add(jPanel12, java.awt.BorderLayout.CENTER);

        tpMain.addTab("Transaction", new javax.swing.ImageIcon(getClass().getResource("/multimedia/transaction_32.png")), pnTransaction); // NOI18N

        pnBlockchain.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Block Data"));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane7.setPreferredSize(new java.awt.Dimension(252, 105));

        txtBlockHeader.setEditable(false);
        txtBlockHeader.setColumns(20);
        txtBlockHeader.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtBlockHeader.setRows(5);
        txtBlockHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Header"));
        jScrollPane7.setViewportView(txtBlockHeader);

        jPanel4.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.WEST);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jScrollPane6.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Transactions"));

        txtBlockTransactions.setEditable(false);
        txtBlockTransactions.setColumns(20);
        txtBlockTransactions.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        txtBlockTransactions.setRows(5);
        jScrollPane6.setViewportView(txtBlockTransactions);

        jPanel8.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        pnBlockchain.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        lstBlcockchain.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstBlcockchain.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstBlcockchainValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(lstBlcockchain);

        pnBlockchain.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        tpMain.addTab("Blockchain", new javax.swing.ImageIcon(getClass().getResource("/multimedia/blockchain_32.png")), pnBlockchain); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/manso.png"))); // NOI18N
        jLabel1.setText("(c) Antóni0 M@nso 2024");

        javax.swing.GroupLayout pnAboutLayout = new javax.swing.GroupLayout(pnAbout);
        pnAbout.setLayout(pnAboutLayout);
        pnAboutLayout.setHorizontalGroup(
            pnAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAboutLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 295, Short.MAX_VALUE))
        );
        pnAboutLayout.setVerticalGroup(
            pnAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAboutLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 235, Short.MAX_VALUE))
        );

        tpMain.addTab("About", new javax.swing.ImageIcon(getClass().getResource("/multimedia/about.png")), pnAbout); // NOI18N

        getContentPane().add(tpMain, java.awt.BorderLayout.CENTER);

        jPanel15.setLayout(new java.awt.BorderLayout());

        txtExceptionLog.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtExceptionLog.setForeground(new java.awt.Color(255, 51, 102));
        txtExceptionLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtExceptionLog.setText("Network node");
        txtExceptionLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(txtExceptionLog, java.awt.BorderLayout.CENTER);

        txtTimeLog.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTimeLog.setText("00:00:00.000");
        jPanel15.add(txtTimeLog, java.awt.BorderLayout.WEST);

        txtTitleLog.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTitleLog.setText("                                                     ");
        jPanel15.add(txtTitleLog, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel15, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartServerActionPerformed
        try {
            int serverPort = Integer.parseInt(txtPort.getText());
            String name = txtObjectName.getText();

            //local adress of server
            String host = txtAddress.getText().trim(); //
            //create registry to object
            LocateRegistry.createRegistry(serverPort);
            //create adress of remote object
            String address = String.format("//%s:%d/%s", host, serverPort, name);
            myremoteObject = new OremoteP2P(address, this);
            //link adress to object
            Naming.rebind(address, myremoteObject);


            onBlockchainUpdate(myremoteObject.getBlockchain());
        } catch (Exception ex) {
            onException(ex, "Starting server");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btStartServerActionPerformed

    private void txtNodeAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNodeAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNodeAddressActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String address = txtNodeAddress.getText();
            IremoteP2P node = (IremoteP2P) RMI.getRemote(address);
            myremoteObject.addNode(node);
        } catch (Exception ex) {
            onException(ex, "connect");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btAddTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddTransactionActionPerformed
        try {
            myremoteObject.addTransaction(txtTranaction.getText());
        } catch (RemoteException ex) {
            onException(ex, "transactions");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAddTransactionActionPerformed

    private void btMiningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMiningActionPerformed

        new Thread(() -> {
            try {
                //fazer um bloco
                List<String> blockTransactions = myremoteObject.getTransactions();
                if (blockTransactions.size() < 0) {
                    return;
                }
                Block b = new Block(myremoteObject.getBlockchainLastHash(), blockTransactions);
                //remover as transacoes
                myremoteObject.removeTransactions(blockTransactions);
                //minar o bloco
                int zeros = (Integer) spZeros.getValue();
                int nonce = myremoteObject.mine(b.getMinerData(), zeros);
                //atualizar o nonce
                b.setNonce(nonce, zeros);
                //adiconar o bloco
                myremoteObject.addBlock(b);

            } catch (Exception ex) {
                onException(ex, "Start ming");
                Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();


    }//GEN-LAST:event_btMiningActionPerformed

    private void lstBlcockchainValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstBlcockchainValueChanged
        try {
            BlockChain bc = myremoteObject.getBlockchain();
            int index = bc.getSize() - lstBlcockchain.getSelectedIndex() - 1;
            if (index < 0 || index >= bc.getSize() ) {
                return;
            }
            
            Block selected = bc.get(index);
            txtBlockHeader.setText(selected.getHeaderString());
            txtBlockHeader.setCaretPosition(0);
            txtBlockTransactions.setText(selected.getTransactionsString());
            txtBlockTransactions.setCaretPosition(0);

        } catch (RemoteException ex) {
            onException(ex, "list selection");
        }
    }//GEN-LAST:event_lstBlcockchainValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NodeP2PGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NodeP2PGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NodeP2PGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NodeP2PGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NodeP2PGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddTransaction;
    private javax.swing.JButton btMining;
    private javax.swing.JButton btStartServer;
    private javax.swing.JLabel imgServerRunning;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblMining;
    private javax.swing.JLabel lblWinner;
    private javax.swing.JList<String> lstBlcockchain;
    private javax.swing.JPanel pnAbout;
    private javax.swing.JPanel pnBlockchain;
    private javax.swing.JPanel pnNetwork;
    private javax.swing.JPanel pnServer;
    private javax.swing.JPanel pnTransaction;
    private javax.swing.JSpinner spZeros;
    private javax.swing.JTabbedPane tpMain;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextArea txtBlockHeader;
    private javax.swing.JTextArea txtBlockTransactions;
    private javax.swing.JLabel txtExceptionLog;
    private javax.swing.JTextArea txtListTransdactions;
    private javax.swing.JTextArea txtLogMining;
    private javax.swing.JTextArea txtNetwork;
    private javax.swing.JTextField txtNodeAddress;
    private javax.swing.JTextField txtObjectName;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextPane txtServerLog;
    private javax.swing.JLabel txtTimeLog;
    private javax.swing.JLabel txtTitleLog;
    private javax.swing.JTextField txtTranaction;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onStartRemote(String message) {
        setTitle(message);
        imgServerRunning.setEnabled(true);
        btStartServer.setEnabled(false);
        GuiUtils.addText(txtServerLog, "Start server", message);

    }
    static DateTimeFormatter hfmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public void onException(Exception e, String title) {
        txtTimeLog.setText(LocalTime.now().format(hfmt));
        txtExceptionLog.setForeground(new java.awt.Color(255, 0, 0));
        txtExceptionLog.setText(e.getMessage());
        txtTitleLog.setText(title);
        // JOptionPane.showMessageDialog(this, e.getMessage(), title, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void onMessage(String title, String message) {
        GuiUtils.addText(txtServerLog, title, message);
        tpMain.setSelectedComponent(pnServer);
    }

    @Override
    public void onConect(String address) {
        try {
            List<IremoteP2P> net = myremoteObject.getNetwork();
            String txt = "";
            for (IremoteP2P iremoteP2P : net) {
                txt += iremoteP2P.getAdress() + "\n";
            }
            txtNetwork.setText(txt);
            tpMain.setSelectedComponent(pnNetwork);
        } catch (RemoteException ex) {
            onException(ex, "On conect");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onTransaction(String transaction) {
        try {
            onMessage("Transaction ", transaction);
            String txt = "";
            List<String> tr = myremoteObject.getTransactions();
            for (String string : tr) {
                txt += string + "\n";
            }
            txtListTransdactions.setText(txt);
            tpMain.setSelectedComponent(pnTransaction);
        } catch (RemoteException ex) {
            onException(ex, "on transaction");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onStartMining(String message, int zeros) {
        SwingUtilities.invokeLater(() -> {
            tpMain.setSelectedComponent(pnTransaction);
            btMining.setEnabled(false);
            lblMining.setVisible(true);
            lblWinner.setVisible(false);
            txtLogMining.setText("[START]" + message + "[" + zeros + "]\n");
            lblMining.setText("mining " + zeros + " zeros");
            repaint();
        });
    }

    @Override
    public void onStopMining(String message, int nonce) {
        SwingUtilities.invokeLater(() -> {
            txtLogMining.setText("[STOP]" + message + "[" + nonce + "]\n" + txtLogMining.getText());
            lblMining.setVisible(false);
            tpMain.setSelectedComponent(pnTransaction);
            btMining.setEnabled(true);
            txtLogMining.setText("Nounce Found [" + nonce + "]\n" + txtLogMining.getText());
            System.out.println(" NONCE " + nonce + "\t" + message);
            repaint();
        });
    }

    @Override
    public void onNounceFound(String message, int nonce) {
        try {
            myremoteObject.stopMining(nonce);
        } catch (RemoteException ex) {
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
            txtLogMining.setText("Nounce Found [" + nonce + "]\n" + txtLogMining.getText());
            lblMining.setVisible(false);
            lblWinner.setText(message);
            lblWinner.setVisible(true);
            tpMain.setSelectedComponent(pnTransaction);
            txtTitleLog.setText(Miner.getHash(myremoteObject.myMiner.getMessage(), myremoteObject.myMiner.getNonce()));
            repaint();
            System.out.println(" NONCE " + nonce + "\t" + message);
        });

    }

    @Override
    public void onBlockchainUpdate(BlockChain b) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel model = new DefaultListModel();
            for (int i = b.getSize() - 1; i >= 0; i--) {
                model.addElement(b.get(i));
            }
            lstBlcockchain.setModel(model);
            lstBlcockchain.setSelectedIndex(0);
            tpMain.setSelectedComponent(pnBlockchain);
            repaint();
        });
    }
}
