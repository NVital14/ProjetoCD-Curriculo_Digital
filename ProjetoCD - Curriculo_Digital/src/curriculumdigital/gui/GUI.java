/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package curriculumdigital.gui;

import blockchain.utils.Block;
import blockchain.utils.BlockChain;
import blockchain.utils.GuiUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import curriculumdigital.core.Curriculo;
import curriculumdigital.core.Submission;
import curriculumdigital.core.User;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import p2p.IremoteP2P;
import p2p.OremoteP2P;
import p2p.P2Plistener;

/**
 *
 * @author noemi
 */
public class GUI extends javax.swing.JFrame implements P2Plistener {

    public static String fileCurriculo = "curriculo.obj";
    Curriculo curriculo;
    List<Submission> elements = new ArrayList();
    User myUser;
    OremoteP2P myRemoteObject;

    /**
     * Creates new form Interface
     */
    public GUI() {
        initComponents();
        setTitle("Curriculum Digital");
        try {

            File file = new File("blockchainfiles", fileCurriculo);

            if (file.exists()) {
//                curriculo = Curriculo.load(fileCurriculo);
//                elements.addAll(curriculo.submissions);
                String txt = "";
                List<Submission> tr = myRemoteObject.getSubmissions();
                for (Submission s : tr) {
                    txt += s.getUser() + " --> " + s.getName() + " - " + s.getEvent() + "\n";
                }
//                txtListSubmissions.setText(txt);
//                txtCV.setText(elements.toString());
//                curriculo.submissions.clear();
                //textAreaCVAll.setText(curriculo.loadPersonEvents(null, true));
                onNewCurriculum();
                onBlockchainUpdate(myRemoteObject.getBlockchain());

            } else {
                curriculo = new Curriculo();
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    if (myRemoteObject.getSubmissionsSize() > 0 && myRemoteObject.getNetwork().size() == 1) {
                        new Thread(() -> {
                            try {
                                //fazer um bloco
                                List<Submission> blockSubmissions = myRemoteObject.getSubmissions();
                                if (blockSubmissions.size() < 0) {
                                    return;
                                }
                                Block b = new Block(myRemoteObject.getBlockchainLastHash(), blockSubmissions);
                                //remover as transacoes
                                myRemoteObject.removeSubmissions(blockSubmissions);
                                //minar o bloco
                                int zeros = 4;
                                int nonce = myRemoteObject.mine(b.getMinerData(), zeros);
                                //atualizar o nonce
                                b.setNonce(nonce, zeros);
                                //adiconar o bloco
                                myRemoteObject.addBlock(b);

                            } catch (Exception ex) {
                                onException(ex, "Start ming");
                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }).start();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
    }

    public GUI(User u, OremoteP2P remote) {
        this.myRemoteObject = remote;
        this();
        this.myUser = u;
        this.txtInstitute.setText(u.getName());
        this.txtInstitute1.setText(u.getName());
        myRemoteObject.setListener(this);

        onNewCurso();

        if (!u.isInstitute()) {
            // Remover o Tab "Curriculum" para utilizadores não institucionais
            App.remove(Curriculum); // Remove a aba do Curriculum
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

        App = new javax.swing.JTabbedPane();
        Curriculum = new javax.swing.JPanel();
        lbCurriculum = new javax.swing.JLabel();
        ScrollInstitute = new javax.swing.JScrollPane();
        txtInstitute = new javax.swing.JTextField();
        ScrollName = new javax.swing.JScrollPane();
        txtName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        ScrollCV = new javax.swing.JScrollPane();
        txtListSubmissions = new javax.swing.JTextArea();
        cbCursos = new javax.swing.JComboBox<>();
        spNota = new javax.swing.JSpinner();
        RegistarCursos = new javax.swing.JPanel();
        txtInstitute1 = new javax.swing.JTextField();
        txtCurso = new javax.swing.JTextField();
        btnRegistarCurso = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        textAreaCursos = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ListaPessoas = new javax.swing.JPanel();
        btnPersonCV = new javax.swing.JButton();
        txtNameCV = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaCVPerson = new javax.swing.JTextArea();
        lbSearchCurriculum = new javax.swing.JLabel();
        ListaCurriculum = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaCVAll = new javax.swing.JTextArea();
        lbAllCurriculum = new javax.swing.JLabel();
        blockchain = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtBlockHeader = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtBlockSubmissions = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstBlockchain = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbCurriculum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbCurriculum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCurriculum.setText("Curriculum Digital");

        ScrollInstitute.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtInstitute.setEditable(false);
        txtInstitute.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instituto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        txtInstitute.setMaximumSize(new java.awt.Dimension(70, 70));
        ScrollInstitute.setViewportView(txtInstitute);

        ScrollName.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        ScrollName.setViewportView(txtName);

        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        ScrollCV.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtListSubmissions.setEditable(false);
        txtListSubmissions.setColumns(20);
        txtListSubmissions.setLineWrap(true);
        txtListSubmissions.setRows(5);
        txtListSubmissions.setWrapStyleWord(true);
        ScrollCV.setViewportView(txtListSubmissions);

        cbCursos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCursos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Curso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        spNota.setModel(new javax.swing.SpinnerNumberModel(15, 10, 20, 1));
        spNota.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nota Final do Curso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        spNota.setRequestFocusEnabled(false);
        spNota.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout CurriculumLayout = new javax.swing.GroupLayout(Curriculum);
        Curriculum.setLayout(CurriculumLayout);
        CurriculumLayout.setHorizontalGroup(
            CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbCurriculum, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(CurriculumLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spNota)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ScrollInstitute)
                    .addComponent(ScrollName)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ScrollCV, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );
        CurriculumLayout.setVerticalGroup(
            CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CurriculumLayout.createSequentialGroup()
                .addComponent(lbCurriculum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ScrollCV, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CurriculumLayout.createSequentialGroup()
                        .addComponent(ScrollInstitute, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScrollName, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spNota, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        App.addTab("Curriculum", Curriculum);

        txtInstitute1.setEditable(false);
        txtInstitute1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instituto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        txtCurso.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome do Curso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btnRegistarCurso.setText("Guardar Curso");
        btnRegistarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistarCursoActionPerformed(evt);
            }
        });

        textAreaCursos.setEditable(false);
        textAreaCursos.setColumns(20);
        textAreaCursos.setLineWrap(true);
        textAreaCursos.setRows(5);
        textAreaCursos.setWrapStyleWord(true);
        jScrollPane6.setViewportView(textAreaCursos);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registar Cursos");

        javax.swing.GroupLayout RegistarCursosLayout = new javax.swing.GroupLayout(RegistarCursos);
        RegistarCursos.setLayout(RegistarCursosLayout);
        RegistarCursosLayout.setHorizontalGroup(
            RegistarCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistarCursosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(RegistarCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCurso)
                    .addComponent(txtInstitute1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(btnRegistarCurso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegistarCursosLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        RegistarCursosLayout.setVerticalGroup(
            RegistarCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistarCursosLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(RegistarCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addGroup(RegistarCursosLayout.createSequentialGroup()
                        .addComponent(txtInstitute1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnRegistarCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(296, Short.MAX_VALUE))
        );

        App.addTab("Cursos", RegistarCursos);

        btnPersonCV.setText("Procurar");
        btnPersonCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonCVActionPerformed(evt);
            }
        });

        txtNameCV.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome da Pessoa", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        textAreaCVPerson.setEditable(false);
        textAreaCVPerson.setColumns(20);
        textAreaCVPerson.setLineWrap(true);
        textAreaCVPerson.setRows(5);
        textAreaCVPerson.setWrapStyleWord(true);
        jScrollPane2.setViewportView(textAreaCVPerson);

        lbSearchCurriculum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbSearchCurriculum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSearchCurriculum.setText("Procurar Curriculum");

        javax.swing.GroupLayout ListaPessoasLayout = new javax.swing.GroupLayout(ListaPessoas);
        ListaPessoas.setLayout(ListaPessoasLayout);
        ListaPessoasLayout.setHorizontalGroup(
            ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaPessoasLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNameCV, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(btnPersonCV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(72, 72, 72)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lbSearchCurriculum, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        ListaPessoasLayout.setVerticalGroup(
            ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaPessoasLayout.createSequentialGroup()
                .addComponent(lbSearchCurriculum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ListaPessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ListaPessoasLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(txtNameCV, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPersonCV, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        App.addTab("Lista Pessoas", ListaPessoas);

        textAreaCVAll.setEditable(false);
        textAreaCVAll.setColumns(20);
        textAreaCVAll.setLineWrap(true);
        textAreaCVAll.setRows(5);
        textAreaCVAll.setWrapStyleWord(true);
        jScrollPane1.setViewportView(textAreaCVAll);

        lbAllCurriculum.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbAllCurriculum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbAllCurriculum.setText("Todos os Curriculums");

        javax.swing.GroupLayout ListaCurriculumLayout = new javax.swing.GroupLayout(ListaCurriculum);
        ListaCurriculum.setLayout(ListaCurriculumLayout);
        ListaCurriculumLayout.setHorizontalGroup(
            ListaCurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaCurriculumLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
            .addComponent(lbAllCurriculum, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        ListaCurriculumLayout.setVerticalGroup(
            ListaCurriculumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListaCurriculumLayout.createSequentialGroup()
                .addComponent(lbAllCurriculum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
        );

        App.addTab("Lista Curriculum", ListaCurriculum);

        blockchain.setBorder(javax.swing.BorderFactory.createTitledBorder("Block Data"));

        txtBlockHeader.setColumns(20);
        txtBlockHeader.setRows(5);
        txtBlockHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Header"));
        jScrollPane3.setViewportView(txtBlockHeader);

        txtBlockSubmissions.setColumns(20);
        txtBlockSubmissions.setRows(5);
        txtBlockSubmissions.setBorder(javax.swing.BorderFactory.createTitledBorder("Submissions"));
        jScrollPane4.setViewportView(txtBlockSubmissions);

        lstBlockchain.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstBlockchain.setRequestFocusEnabled(false);
        lstBlockchain.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstBlockchainValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(lstBlockchain);

        javax.swing.GroupLayout blockchainLayout = new javax.swing.GroupLayout(blockchain);
        blockchain.setLayout(blockchainLayout);
        blockchainLayout.setHorizontalGroup(
            blockchainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, blockchainLayout.createSequentialGroup()
                .addGroup(blockchainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, blockchainLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        blockchainLayout.setVerticalGroup(
            blockchainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blockchainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blockchainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        App.addTab("Blockchain", blockchain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(App, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(App)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPersonCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonCVActionPerformed
        try {
            String txt = "";
            textAreaCVPerson.setText("");
            if ("".equals(txtNameCV.getText())) {
                JOptionPane.showMessageDialog(this, "Coloca o nome da pessoa que queres procurar.", "Coloca o nome!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Submission> tr = myRemoteObject.getBlockchainSubmissions();
            for (Submission s : tr) {
                if (txtNameCV.getText().equals(s.getName())) {
                    txt += s.getUser() + " --> " + s.getName() + " - " + s.getEvent() + "\n";
                }
            }
            if (!"".equals(txt)) {
                textAreaCVPerson.setText(txt);
            } else {
                // Mostra uma mensagem de erro no caso de não haver essa pessoa
                JOptionPane.showMessageDialog(this, "Não existem currículos dessa pessoa.", "Não existe!", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPersonCVActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        btnAdd.setEnabled(false);
        System.out.println("Inicio");
        try {
            // Verifica se o utilizador é uma instituição
            if (myUser.isInstitute()) {
                String nome = txtName.getText();
                if ("".equals(nome)) {
                    // tem que escrever um nome
                    JOptionPane.showMessageDialog(this, "Tem que colocar um nome", "Nome Vazio", JOptionPane.ERROR_MESSAGE);
                   btnAdd.setEnabled(true);
                    return;
                }
                String cursoSelecionado = (String) cbCursos.getSelectedItem();
                List<String> cursos = myRemoteObject.loadCursos(myUser.getName());
                if (!cursos.contains(cursoSelecionado)) {
                    // mostra uma mensagem de erro caso o curso seja inválido
                    JOptionPane.showMessageDialog(this, "Tem que selecionar um curso válido", "Curso inválido", JOptionPane.ERROR_MESSAGE);
                   btnAdd.setEnabled(true);
                    return;
                }
                int nota = (Integer) spNota.getValue();
                if (nota < 10 || nota > 20) {
                    // mostra uma mensagem de erro caso a nota seja inválida
                    JOptionPane.showMessageDialog(this, "Tem que inserir uma nota válida (entre 10 e 20).", "Nota inválida", JOptionPane.ERROR_MESSAGE);
                    btnAdd.setEnabled(true);
                    return;
                }
                
                String event = cursoSelecionado + " " + nota;
                // cria uma submissão 
                Submission s = new Submission(
                        myUser,
                        txtName.getText(),
                        event
                );

                // Adiciona a submissão ao currículo e atualiza o campo de texto
                myRemoteObject.addSubmission(s);

                System.out.println("Registei a submissão: " + s.getName() + " - " + s.getEvent());

            } else {
                // Mostra uma mensagem de erro caso o utilizador não seja uma instituição
                JOptionPane.showMessageDialog(this, "Apenas Instituições podem adicionar submissões.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Antes da thread");
        try {
            System.out.println(myRemoteObject.getSubmissions().toString());
        } catch (RemoteException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (myRemoteObject.getSubmissionsSize() == 2) {
                new Thread(() -> {
                    try {
                        System.out.println("Dentro da thread");
                        //fazer um bloco
                        List<Submission> blockSubmissions = myRemoteObject.getSubmissions();
                        if (blockSubmissions.size() < 0) {
                            return;
                        }
                        Block b = new Block(myRemoteObject.getBlockchainLastHash(), blockSubmissions);
                        //remover as transacoes
                        myRemoteObject.removeSubmissions(blockSubmissions);
                        //minar o bloco
                        int zeros = 4;
                        int nonce = myRemoteObject.mine(b.getMinerData(), zeros);
                        //atualizar o nonce
                        b.setNonce(nonce, zeros);
                        //adiconar o bloco
                        myRemoteObject.addBlock(b);
                        SwingUtilities.invokeLater(() -> {
                            btnAdd.setEnabled(true);
                        });

                    } catch (Exception ex) {
                        onException(ex, "Start ming");
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    SwingUtilities.invokeLater(() -> {
                        btnAdd.setEnabled(true);
                    });
                    System.out.println("Acabou thread");
                }).start();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnAdd.setEnabled(true);
        txtName.setText("");
        cbCursos.setSelectedIndex(0);
        spNota.setValue(15);
        System.out.println("Fim thread");
    }//GEN-LAST:event_btnAddActionPerformed

    private void lstBlockchainValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstBlockchainValueChanged
        String txt = "";
        try {
            BlockChain bc = myRemoteObject.getBlockchain();
            int index = bc.getSize() - lstBlockchain.getSelectedIndex() - 1;
            if (index < 0 || index >= bc.getSize()) {
                return;
            }

            Block selected = bc.get(index);
            txtBlockHeader.setText(selected.getHeaderString());
            txtBlockHeader.setCaretPosition(0);
            for (Submission s : selected.getSubmissionsString()) {
                txt += s.getUser() + " --> " + s.getName() + " - " + s.getEvent() + "\n";
            }
            txtBlockSubmissions.setText(txt);
            txtBlockSubmissions.setCaretPosition(0);

        } catch (RemoteException ex) {
            onException(ex, "list selection");
        }
    }//GEN-LAST:event_lstBlockchainValueChanged

    private void btnRegistarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistarCursoActionPerformed
        // TODO add your handling code here:
        String nomeCurso = txtCurso.getText().trim();
        if (nomeCurso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do curso não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            myRemoteObject.addCurso(myUser.getName(), nomeCurso);
            txtCurso.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o curso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistarCursoActionPerformed
    static DateTimeFormatter hfmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Override
    public void onMessage(String title, String message) {
        //GuiUtils.addText(txtServerLog, title, message);
//        tpMain.setSelectedComponent(pnServer);
    }

    @Override
    public void onException(Exception e, String title) {
//        txtTimeLog.setText(LocalTime.now().format(hfmt));
//        txtExceptionLog.setForeground(new java.awt.Color(255, 0, 0));
//        txtExceptionLog.setText(e.getMessage());
//        txtTitleLog.setText(title);
        // JOptionPane.showMessageDialog(this, e.getMessage(), title, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void onStartRemote(String message) {
//        setTitle(message);
//        imgServerRunning.setEnabled(true);
//        btStartServer.setEnabled(false);
//        GuiUtils.addText(txtServerLog, "Start server", message);

    }

    @Override
    public void onConect(String address) {
//        try {
//            List<IremoteP2P> net = myRemoteObject.getNetwork();
//            String txt = "";
//            for (IremoteP2P iremoteP2P : net) {
//                txt += iremoteP2P.getAdress() + "\n";
//            }
//            txtNetwork.setText(txt);
////            tpMain.setSelectedComponent(pnNetwork);
//        } catch (RemoteException ex) {
//            onException(ex, "On conect");
//            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    @Override
    public void onBlockchainUpdate(BlockChain b) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel model = new DefaultListModel();
            for (int i = b.getSize() - 1; i >= 0; i--) {
                model.addElement(b.get(i));
            }
            lstBlockchain.setModel(model);
//            lstBlockchain.setSelectedIndex(0);
//           App.setSelectedComponent(jPanel1);
//            repaint();
        });
    }

    @Override
    public void onSubmission(String transaction) {
        try {
            onMessage("Transaction ", transaction);
            String txt = "";
            List<Submission> tr = myRemoteObject.getSubmissions();
            for (Submission s : tr) {
                txt += s.getUser() + " --> " + s.getName() + " - " + s.getEvent() + "\n";
            }
            txtListSubmissions.setText(txt);
//            tpMain.setSelectedComponent(pnTransaction);
        } catch (RemoteException ex) {
            onException(ex, "on transaction");
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onStartMining(String message, int zeros) {
        SwingUtilities.invokeLater(() -> {
//            tpMain.setSelectedComponent(pnTransaction);
//            btMining.setEnabled(false);
//            lblMining.setVisible(true);
//            lblWinner.setVisible(false);
//            txtLogMining.setText("[START]" + message + "[" + zeros + "]\n");
//            lblMining.setText("mining " + zeros + " zeros");
//            repaint();
        });
    }

    @Override
    public void onStopMining(String message, int nonce) {
        SwingUtilities.invokeLater(() -> {
//            txtLogMining.setText("[STOP]" + message + "[" + nonce + "]\n" + txtLogMining.getText());
//            lblMining.setVisible(false);
//            tpMain.setSelectedComponent(pnTransaction);
//            btMining.setEnabled(true);
//            txtLogMining.setText("Nounce Found [" + nonce + "]\n" + txtLogMining.getText());
//            System.out.println(" NONCE " + nonce + "\t" + message);
//            repaint();
        });
    }

    @Override
    public void onNounceFound(String message, int nonce) {
        try {
            myRemoteObject.stopMining(nonce);
        } catch (RemoteException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
//            txtLogMining.setText("Nounce Found [" + nonce + "]\n" + txtLogMining.getText());
//            lblMining.setVisible(false);
//            lblWinner.setText(message);
//            lblWinner.setVisible(true);
//            tpMain.setSelectedComponent(pnTransaction);
//            txtTitleLog.setText(Miner.getHash(myremoteObject.myMiner.getMessage(), myremoteObject.myMiner.getNonce()));
//            repaint();
//            System.out.println(" NONCE " + nonce + "\t" + message);
        });

    }

    @Override
    public void onNewCurriculum() {
        try {
            String txt = "";
            List<Submission> tr = myRemoteObject.getBlockchainSubmissions();
            for (Submission s : tr) {
                txt += s.getUser() + " --> " + s.getName() + " - " + s.getEvent() + "\n";
            }
            textAreaCVAll.setText(txt);
        } catch (RemoteException ex) {
            onException(ex, "on new block");
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onNewCurso() {
        try {
            cbCursos.removeAllItems();
            String txt = "";
            List<String> cursos = myRemoteObject.loadCursos(myUser.getName());
            if (!cursos.isEmpty()) {
                cbCursos.addItem("--Escolha um curso--");
                for (String s : cursos) {
                    txt += s + "\n";
                    cbCursos.addItem(s);
                }
                textAreaCursos.setText(txt);
            } else {
                cbCursos.addItem("Comece a adicionar cursos:)");
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    private javax.swing.JTabbedPane App;
    private javax.swing.JPanel Curriculum;
    private javax.swing.JPanel ListaCurriculum;
    private javax.swing.JPanel ListaPessoas;
    private javax.swing.JPanel RegistarCursos;
    private javax.swing.JScrollPane ScrollCV;
    private javax.swing.JScrollPane ScrollInstitute;
    private javax.swing.JScrollPane ScrollName;
    private javax.swing.JPanel blockchain;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnPersonCV;
    private javax.swing.JButton btnRegistarCurso;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbAllCurriculum;
    private javax.swing.JLabel lbCurriculum;
    private javax.swing.JLabel lbSearchCurriculum;
    private javax.swing.JList<String> lstBlockchain;
    private javax.swing.JSpinner spNota;
    private javax.swing.JTextArea textAreaCVAll;
    private javax.swing.JTextArea textAreaCVPerson;
    private javax.swing.JTextArea textAreaCursos;
    private javax.swing.JTextArea txtBlockHeader;
    private javax.swing.JTextArea txtBlockSubmissions;
    private javax.swing.JTextField txtCurso;
    private javax.swing.JTextField txtInstitute;
    private javax.swing.JTextField txtInstitute1;
    private javax.swing.JTextArea txtListSubmissions;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameCV;
    // End of variables declaration//GEN-END:variables
}
