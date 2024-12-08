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

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import blockchain.utils.GuiUtils;
import blockchain.utils.RMI;

/**
 *
 * @author zulu
 */
public class NodeP2PGui extends javax.swing.JFrame implements P2Plistener{

    OremoteP2P myremoteObject;

    /**
     * Creates new form MessengerGUI
     */
    public NodeP2PGui() {
        initComponents();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnServer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btStartServer = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtServerListeningPort = new javax.swing.JTextField();
        txtServerListeningObjectName = new javax.swing.JTextField();
        imgServerRunning = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtServerLog = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        txtNodeAddress = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNetwork = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtLstTransdactions = new javax.swing.JTextArea();
        txtTranaction = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

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

        btStartServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/curriculumdigital/media/startServer.png"))); // NOI18N
        btStartServer.setText("Start");
        btStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartServerActionPerformed(evt);
            }
        });
        jPanel3.add(btStartServer, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        txtServerListeningPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerListeningPort.setText("10010");
        txtServerListeningPort.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Number"));
        txtServerListeningPort.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel7.add(txtServerListeningPort);

        txtServerListeningObjectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerListeningObjectName.setText("remoteP2P");
        txtServerListeningObjectName.setBorder(javax.swing.BorderFactory.createTitledBorder("ObjectName"));
        jPanel7.add(txtServerListeningObjectName);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        imgServerRunning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/curriculumdigital/media/loading_green.gif"))); // NOI18N
        imgServerRunning.setEnabled(false);
        jPanel3.add(imgServerRunning, java.awt.BorderLayout.EAST);

        pnServer.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        txtServerLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Server"));
        txtServerLog.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(txtServerLog);

        pnServer.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Server", pnServer);

        txtNodeAddress.setText("//10.10.208.35:10010/remoteP2P");
        txtNodeAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNodeAddressActionPerformed(evt);
            }
        });

        jButton1.setText("Connect");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNetwork.setColumns(20);
        txtNetwork.setRows(5);
        jScrollPane2.setViewportView(txtNetwork);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNodeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNodeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("P2pNetwork", jPanel2);

        txtLstTransdactions.setColumns(20);
        txtLstTransdactions.setRows(5);
        jScrollPane3.setViewportView(txtLstTransdactions);

        txtTranaction.setText("transaction to network");

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 129, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTranaction, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTranaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Transaction", jPanel4);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartServerActionPerformed
        try {
            int port = Integer.parseInt(txtServerListeningPort.getText());
            String name = txtServerListeningObjectName.getText();
            //local adress of server
            String host = InetAddress.getLocalHost().getHostAddress();
            //create registry to object
            LocateRegistry.createRegistry(port);
            //create adress of remote object
            String address = String.format("//%s:%d/%s", host, port, name);
            myremoteObject = new OremoteP2P(address,this);
            //link adress to object
            Naming.rebind(address, myremoteObject);
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            myremoteObject.addTransaction(txtTranaction.getText());
        } catch (RemoteException ex) {
            onException(ex, "transactions");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton btStartServer;
    private javax.swing.JLabel imgServerRunning;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnServer;
    private javax.swing.JTextArea txtLstTransdactions;
    private javax.swing.JTextArea txtNetwork;
    private javax.swing.JTextField txtNodeAddress;
    private javax.swing.JTextField txtServerListeningObjectName;
    private javax.swing.JTextField txtServerListeningPort;
    private javax.swing.JTextPane txtServerLog;
    private javax.swing.JTextField txtTranaction;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onStart(String message) {
        imgServerRunning.setEnabled(true);
        btStartServer.setEnabled(false);
        GuiUtils.addText(txtServerLog, "Start server", message);
     }
    
      public void onException(Exception e, String title){
          JOptionPane.showMessageDialog(this, e.getMessage(),title, JOptionPane.WARNING_MESSAGE);
      }
    

    @Override
    public void onConect(String address) {
        try {
            List<IremoteP2P> net =  myremoteObject.getNetwork();
            String txt = "";
            for (IremoteP2P iremoteP2P : net) {
                txt+= iremoteP2P.getAdress()+"\n";
            }
            txtNetwork.setText(txt);
        } catch (RemoteException ex) {
            onException(ex, "On conect");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        }

    @Override
    public void onTransaction(String transaction) {
        try {
            String txt = "";
            List<String> tr = myremoteObject.getTransactions();
            for (String string : tr) {
                txt += string + "\n";
                
            }
            txtLstTransdactions.setText(txt);
        } catch (RemoteException ex) {
            onException(ex, "on transaction");
            Logger.getLogger(NodeP2PGui.class.getName()).log(Level.SEVERE, null, ex);
        }
       }

}
