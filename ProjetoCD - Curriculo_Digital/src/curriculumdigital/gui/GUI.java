/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package curriculumdigital.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import curriculumdigital.core.Curriculo;
import curriculumdigital.core.Submission;

/**
 *
 * @author noemi
 */
public class GUI extends javax.swing.JFrame {
    public static String fileCurriculo = "curriculo.obj";
    Curriculo curriculo;
    
    /**
     * Creates new form Interface
     */
    public GUI() {
        initComponents();
        setTitle("Curriculum Digital");
        curriculo = new Curriculo();
        try {
            curriculo = Curriculo.load(fileCurriculo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.print(e);
        }
        txtCV.setText(curriculo.toString());
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        App = new javax.swing.JTabbedPane();
        Curriculum = new javax.swing.JPanel();
        lbCurriculum = new javax.swing.JLabel();
        ScrollName = new javax.swing.JScrollPane();
        txtName = new javax.swing.JTextField();
        ScrollEvent = new javax.swing.JScrollPane();
        txtEvent = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        ScrollCV = new javax.swing.JScrollPane();
        txtCV = new javax.swing.JTextArea();
        ListaPessoas = new javax.swing.JPanel();
        btnPeople = new javax.swing.JButton();
        ListaCurriculum = new javax.swing.JPanel();
        btnCV = new javax.swing.JButton();
        AcercadeNos = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbCurriculum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbCurriculum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCurriculum.setText("Curriculum Digital");

        ScrollName.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ScrollName.setViewportView(txtName);

        ScrollEvent.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtEvent.setColumns(20);
        txtEvent.setLineWrap(true);
        txtEvent.setRows(5);
        txtEvent.setWrapStyleWord(true);
        txtEvent.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Evento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ScrollEvent.setViewportView(txtEvent);

        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        ScrollCV.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtCV.setEditable(false);
        txtCV.setColumns(20);
        txtCV.setLineWrap(true);
        txtCV.setRows(5);
        txtCV.setWrapStyleWord(true);
        ScrollCV.setViewportView(txtCV);

        javax.swing.GroupLayout CurriculumLayout = new javax.swing.GroupLayout(Curriculum);
        Curriculum.setLayout(CurriculumLayout);
        CurriculumLayout.setHorizontalGroup(
            CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbCurriculum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CurriculumLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ScrollEvent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addComponent(ScrollName, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(ScrollCV, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        CurriculumLayout.setVerticalGroup(
            CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CurriculumLayout.createSequentialGroup()
                .addComponent(lbCurriculum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollCV, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CurriculumLayout.createSequentialGroup()
                        .addComponent(ScrollName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ScrollEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );

        App.addTab("Curriculum", Curriculum);

        btnPeople.setText("Pessoas");
        btnPeople.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeopleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ListaPessoasLayout = new javax.swing.GroupLayout(ListaPessoas);
        ListaPessoas.setLayout(ListaPessoasLayout);
        ListaPessoasLayout.setHorizontalGroup(
            ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaPessoasLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(btnPeople, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(538, Short.MAX_VALUE))
        );
        ListaPessoasLayout.setVerticalGroup(
            ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaPessoasLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnPeople, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        App.addTab("Lista Pessoas", ListaPessoas);

        btnCV.setText("Curriculum");
        btnCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ListaCurriculumLayout = new javax.swing.GroupLayout(ListaCurriculum);
        ListaCurriculum.setLayout(ListaCurriculumLayout);
        ListaCurriculumLayout.setHorizontalGroup(
            ListaCurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaCurriculumLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(btnCV, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(533, Short.MAX_VALUE))
        );
        ListaCurriculumLayout.setVerticalGroup(
            ListaCurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaCurriculumLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(btnCV, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
        );

        App.addTab("Lista Curriculum", ListaCurriculum);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Noemi Vital Nº24872");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Acerca de Nós");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Beatriz Moreira Nº25262");

        javax.swing.GroupLayout AcercadeNosLayout = new javax.swing.GroupLayout(AcercadeNos);
        AcercadeNos.setLayout(AcercadeNosLayout);
        AcercadeNosLayout.setHorizontalGroup(
            AcercadeNosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AcercadeNosLayout.setVerticalGroup(
            AcercadeNosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercadeNosLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jLabel6)
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addContainerGap(319, Short.MAX_VALUE))
        );

        App.addTab("Acerca de Nós", AcercadeNos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(App, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(App)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            Submission s = new Submission(
                    txtName.getText(),
                    txtEvent.getText()
            );
            
            curriculo.add(s);
            txtCV.setText(curriculo.toString());
            curriculo.save(fileCurriculo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCVActionPerformed

    }//GEN-LAST:event_btnCVActionPerformed

    private void btnPeopleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeopleActionPerformed

    }//GEN-LAST:event_btnPeopleActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AcercadeNos;
    private javax.swing.JTabbedPane App;
    private javax.swing.JPanel Curriculum;
    private javax.swing.JPanel ListaCurriculum;
    private javax.swing.JPanel ListaPessoas;
    private javax.swing.JScrollPane ScrollCV;
    private javax.swing.JScrollPane ScrollEvent;
    private javax.swing.JScrollPane ScrollName;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCV;
    private javax.swing.JButton btnPeople;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lbCurriculum;
    private javax.swing.JTextArea txtCV;
    private javax.swing.JTextArea txtEvent;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
