/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projetocd.curriculo_digital;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author noemi
 */
public class Interface extends javax.swing.JFrame {
    public static String fileCurriculo = "curriculo.obj";
    Curriculo curriculo;
    
    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
        setTitle("Curriculum Digital");
        curriculo = new Curriculo();
        try {
            curriculo = Curriculo.load(fileCurriculo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.print(e);
        }
        txtCV.setText(curriculo.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCV = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnPeople = new javax.swing.JButton();
        btnCV = new javax.swing.JButton();
        txtEvent = new javax.swing.JTextField();
        btnAboutUs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Curriculum Digital");

        jLabel2.setText("Nome");

        jLabel3.setText("Evento");

        txtCV.setColumns(20);
        txtCV.setRows(5);
        jScrollPane2.setViewportView(txtCV);

        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnPeople.setText("Pessoas");
        btnPeople.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeopleActionPerformed(evt);
            }
        });

        btnCV.setText("Curriculum");
        btnCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCVActionPerformed(evt);
            }
        });

        btnAboutUs.setText("Acerca de Nós");
        btnAboutUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutUsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnCV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnPeople, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAboutUs)
                                .addGap(51, 51, 51))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEvent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPeople)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCV)
                    .addComponent(btnAboutUs))
                .addContainerGap(9, Short.MAX_VALUE))
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
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
//        txtCV.setText("");
//        //caminho do ficheiro
//        File ficheiro = new File("C:\\Users\\noemi\\Documents\\IPT\\Computacao Distribuida\\ProjetoCD - Curriculo_Digital\\texto_guardado.txt");
//        //nome da pessoa
//        String name = txtName.getText();   
//        name = name.toLowerCase();
//        //texto do evento
//        String eventText = txtEvent.getText();
//        
//        if(name.contains("-") || eventText.contains("-")){
//            txtCV.setText("Não pode utilizar \"-\" no seu texto");
//            return;
//        }
//        
//        try {
//            //o true garante que o texto é adicionado no final do ficheiro
//            FileWriter escritor = new FileWriter(ficheiro, true);
//            BufferedWriter bufferedWriter = new BufferedWriter(escritor);
//
//            // escreve o texto no ficheiro 
//            bufferedWriter.write(name + " - " + eventText);
//             bufferedWriter.newLine();  
//            bufferedWriter.newLine();
//            // fecha o writer 
//            bufferedWriter.close();
//        } catch (IOException ex) {
//            System.out.print(ex);
//        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnPeopleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeopleActionPerformed
        File ficheiro = new File("C:\\Users\\noemi\\Documents\\IPT\\Computacao Distribuida\\ProjetoCD - Curriculo_Digital\\texto_guardado.txt");
        txtCV.setText("");
        try {
            // cria um BufferedReader para ler o ficheiro
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ficheiro));
            String linha;

            // loop para ler cada linha do ficheiro
            while ((linha = bufferedReader.readLine()) != null) {
                // Divide a linha no caractere " - "
                String[] partes = linha.split(" - ");
                if (partes.length > 0) {
                    String name = partes[0]; // O nome é a primeira parte
                    txtCV.append(name + "\n");
                }
            }

            // fecha o BufferedReader
            bufferedReader.close();

        } catch (IOException ex) {
            System.out.print("Erro ao ler o ficheiro: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnPeopleActionPerformed

    private void btnCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCVActionPerformed
File ficheiro = new File("C:\\Users\\noemi\\Documents\\IPT\\Computacao Distribuida\\ProjetoCD - Curriculo_Digital\\texto_guardado.txt");
        txtCV.setText("");
        try {
            // cria um BufferedReader para ler o ficheiro
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ficheiro));
            String linha;

            // loop para ler cada linha do ficheiro
            while ((linha = bufferedReader.readLine()) != null) {
                // Divide a linha no caractere " - "
                String[] partes = linha.split(" - ");
                if (partes.length > 0) {
                    String name = partes[0]; // O nome é a primeira parte
                    String eventText = partes[1];
                    txtCV.append(name + "\n" + eventText);
                }
            }

            // fecha o BufferedReader
            bufferedReader.close();

        } catch (IOException ex) {
            System.out.print("Erro ao ler o ficheiro: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCVActionPerformed

    private void btnAboutUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutUsActionPerformed
        AboutUs a = new AboutUs();
        a.show();
        dispose();
    }//GEN-LAST:event_btnAboutUsActionPerformed

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
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAboutUs;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCV;
    private javax.swing.JButton btnPeople;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtCV;
    private javax.swing.JTextField txtEvent;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
