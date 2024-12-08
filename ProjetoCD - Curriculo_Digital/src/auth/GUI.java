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
package auth;

/**
 *
 * @author zulu
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form MessengerGUI
     */
    public GUI() {
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
        pnCliente = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btStartClient = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtServerAdress = new javax.swing.JTextField();
        txtServerPort = new javax.swing.JTextField();
        txtServerObjectName = new javax.swing.JTextField();
        imgClientRunning = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtClientLog = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();

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
        jPanel3.add(btStartServer, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        txtServerListeningPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerListeningPort.setText("10010");
        txtServerListeningPort.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Number"));
        txtServerListeningPort.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel7.add(txtServerListeningPort);

        txtServerListeningObjectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerListeningObjectName.setText("remoteObject");
        txtServerListeningObjectName.setBorder(javax.swing.BorderFactory.createTitledBorder("ObjectName"));
        jPanel7.add(txtServerListeningObjectName);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        imgServerRunning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/loading_green.gif"))); // NOI18N
        imgServerRunning.setEnabled(false);
        jPanel3.add(imgServerRunning, java.awt.BorderLayout.EAST);

        pnServer.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        txtServerLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Server"));
        txtServerLog.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(txtServerLog);

        pnServer.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Server", pnServer);

        pnCliente.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout(10, 10));

        btStartClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/startClient.jpg"))); // NOI18N
        btStartClient.setText("Connect");
        jPanel4.add(btStartClient, java.awt.BorderLayout.WEST);

        jPanel8.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        txtServerAdress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerAdress.setText("127.0.0.1");
        txtServerAdress.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Adress"));
        txtServerAdress.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel8.add(txtServerAdress);

        txtServerPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerPort.setText("10010");
        txtServerPort.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Number"));
        txtServerPort.setPreferredSize(new java.awt.Dimension(200, 36));
        jPanel8.add(txtServerPort);

        txtServerObjectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerObjectName.setText("remoteObject");
        txtServerObjectName.setBorder(javax.swing.BorderFactory.createTitledBorder("Object Name"));
        jPanel8.add(txtServerObjectName);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        imgClientRunning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/loading_blue.gif"))); // NOI18N
        imgClientRunning.setEnabled(false);
        jPanel4.add(imgClientRunning, java.awt.BorderLayout.EAST);

        pnCliente.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        txtClientLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log Client"));
        txtClientLog.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(txtClientLog);

        pnCliente.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Client", pnCliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Service", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btStartClient;
    private javax.swing.JButton btStartServer;
    private javax.swing.JLabel imgClientRunning;
    private javax.swing.JLabel imgServerRunning;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnCliente;
    private javax.swing.JPanel pnServer;
    private javax.swing.JTextPane txtClientLog;
    private javax.swing.JTextField txtServerAdress;
    private javax.swing.JTextField txtServerListeningObjectName;
    private javax.swing.JTextField txtServerListeningPort;
    private javax.swing.JTextPane txtServerLog;
    private javax.swing.JTextField txtServerObjectName;
    private javax.swing.JTextField txtServerPort;
    // End of variables declaration//GEN-END:variables

  
}
