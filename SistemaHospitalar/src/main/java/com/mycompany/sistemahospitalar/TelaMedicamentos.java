/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.sistemahospitalar;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author luca0
 */
public class TelaMedicamentos extends javax.swing.JDialog {
    public int idPaciente = 0;
    /**
     * Creates new form TelaMedicamentos
     */
    public TelaMedicamentos(java.awt.Frame parent, boolean modal, int id, String nomeSelecionado, String sobrenomeSelecionado) {
        super(parent, modal);
        initComponents();
        
        this.idPaciente = id; // Guarda o ID secretamente
        lblNomePaciente.setText("Paciente: " + nomeSelecionado + " " + sobrenomeSelecionado);
        
        carregarTabelaRemedios();
        
        // Atualiza a tabela a cada 1 minuto
        javax.swing.Timer timer = new javax.swing.Timer(60000, e -> {
            jTable1.repaint();
        });

        timer.start();
    }
    
    public void carregarTabelaRemedios() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // Limpa a tabela velha

        try {
            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/projeto", "root", "root");
            MedicamentoDAO dao = new MedicamentoDAO(conector);

            for (Medicamento m : dao.listarMedicamentos()) {

                if (m.getId_paciente() == this.idPaciente) {

                    modelo.addRow(new Object[]{
                        m.getId_medicamento(),
                        m.getNome_medicamento(),
                        m.getFrequencia() + " horas",
                        m.getUltima_dose() != null ? m.getUltima_dose() : "Não tomada",
                        m.getDoses_restantes() 
                    });
                }
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar remédios: " + ex.getMessage());
        }
        aplicarCorHorarioMedicamento();
    }
    
    // QUE SUFOCOO
    private void aplicarCorHorarioMedicamento() {

    jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            try {

                // Frequência
                String freqTexto = table.getValueAt(row, 2).toString();
                int frequencia = Integer.parseInt(
                        freqTexto.replace(" horas", "").trim()
                );

                // Doses restantes
                int dosesRestantes = Integer.parseInt(
                        table.getValueAt(row, 4).toString()
                );

                // Última dose
                Object ultimaDoseObj = table.getValueAt(row, 3);

                boolean atrasado = false;

                if (frequencia <= 0) {

                    atrasado = false;

                } else if (dosesRestantes <= 0) {

                    atrasado = false;

                } else if (ultimaDoseObj == null ||
                        ultimaDoseObj.toString().equals("Não tomada")) {

                    atrasado = true;

                } else {

                    LocalDateTime ultimaDose =
                            LocalDateTime.parse(ultimaDoseObj.toString());

                    LocalDateTime proximaDose =
                            ultimaDose.plusMinutes(frequencia);

                    // Verifica se passou do horário
                    atrasado = LocalDateTime.now()
                            .isAfter(proximaDose);
                }

                if (isSelected) {

                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());

                } else {

                    if (atrasado) {

                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);

                    } else {

                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }

            } catch (Exception ex) {

                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    });
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNomePaciente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFrequencia = new javax.swing.JTextField();
        txtNomeRemedio = new javax.swing.JTextField();
        txtMotivoUso = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtInstrucoes = new javax.swing.JTextField();
        txtTipoMedicamento = new javax.swing.JTextField();
        txtDosesRestantes = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNomePaciente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNomePaciente.setText("Paciente: João");

        jLabel2.setText("Nome do Remédio:");

        jLabel3.setText("Frequência (Horas):");

        jLabel4.setText("Motivo de Uso:");

        txtFrequencia.setToolTipText("");
        txtFrequencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrequenciaActionPerformed(evt);
            }
        });

        jLabel5.setText("Tipo Medicamento:");

        jLabel6.setText("Intruções");

        jLabel7.setText("Doses Restantes");

        jButton1.setText("Adicionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Remédio", "Frequência", "Última Dose", "Doses Restantes"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Excluir Selecionado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Editar Selecionado");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Registrar Dose Tomada");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMotivoUso, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFrequencia, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNomeRemedio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDosesRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtInstrucoes, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblNomePaciente, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNomePaciente)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNomeRemedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFrequencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMotivoUso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtInstrucoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtDosesRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Botão adicionar
        // ARRUMARRRRRRRRRRRR
        try {
            String nome = txtNomeRemedio.getText();
            String tipo = txtTipoMedicamento.getText();
            String instrucoes = txtInstrucoes.getText();
            String motivo = txtMotivoUso.getText();

            int frequencia = 0;
            if (!txtFrequencia.getText().trim().isEmpty()) {
                frequencia = Integer.parseInt(txtFrequencia.getText().trim());
            }

            int doses = 0;
            if (!txtDosesRestantes.getText().trim().isEmpty()) {
                doses = Integer.parseInt(txtDosesRestantes.getText().trim());
            }

            java.time.LocalDateTime ultimaDose = null;

            Medicamento novoRemedio = new Medicamento(this.idPaciente, nome, tipo, instrucoes, frequencia, ultimaDose, doses, motivo);

            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/projeto", "root", "root");
            MedicamentoDAO dao = new MedicamentoDAO(conector);

            dao.cadastrarMedicamento(novoRemedio);

            javax.swing.JOptionPane.showMessageDialog(this, "Remédio adicionado com sucesso!");
            
            carregarTabelaRemedios();

            txtNomeRemedio.setText("");
            txtTipoMedicamento.setText("");
            txtInstrucoes.setText("");
            txtMotivoUso.setText("");
            txtFrequencia.setText("");
            txtDosesRestantes.setText("");

        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro: Digite apenas números inteiros na Frequência e Doses Restantes!", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Botão excluir
        // 1. Verifica qual é a linha selecionada na tabela
        int linhaSelecionada = jTable1.getSelectedRow();

        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecione um remédio na tabela para excluir!", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return; 
        }

        int resposta = javax.swing.JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja excluir este remédio permanentemente?", "Confirmar Exclusão", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
        if (resposta == javax.swing.JOptionPane.YES_OPTION) {
            try {

                int idRemedio = Integer.parseInt(jTable1.getValueAt(linhaSelecionada, 0).toString());


                ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/projeto", "root", "root");
                MedicamentoDAO dao = new MedicamentoDAO(conector);

                dao.excluirMedicamentoPorID(idRemedio);

                carregarTabelaRemedios();

                javax.swing.JOptionPane.showMessageDialog(this, "Remédio excluído com sucesso da base de dados!");

            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro ao excluir o remédio: " + ex.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // botão de registrar dose tomad
        int linhaSelecionada = jTable1.getSelectedRow();

        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecione um remédio na tabela primeiro!", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idMedicamento = Integer.parseInt(jTable1.getValueAt(linhaSelecionada, 0).toString());

            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/projeto", "root", "root");
            MedicamentoDAO dao = new MedicamentoDAO(conector);

            Medicamento remedioAtual = dao.getMedicamentoPorID(idMedicamento);

            if (remedioAtual != null) {

                remedioAtual.setUltima_dose(java.time.LocalDateTime.now());

                if (remedioAtual.getDoses_restantes() > 0) {
                    remedioAtual.setDoses_restantes(remedioAtual.getDoses_restantes() - 1);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "doses já acabaram", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                }

                dao.atualizarMedicamento(remedioAtual);

                carregarTabelaRemedios();

                javax.swing.JOptionPane.showMessageDialog(this, "Dose registada com sucesso! A hora e as doses restantes foram atualizadas.");
            }

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao registar a dose: " + ex.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrequenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrequenciaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Botão editar
        int linhaSelecionada = jTable1.getSelectedRow();

        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione um remédio na tabela primeiro!");
            return;
        }

        try {
            int idRemedio = Integer.parseInt(jTable1.getValueAt(linhaSelecionada, 0).toString());

            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/projeto", "root", "root");
            MedicamentoDAO dao = new MedicamentoDAO(conector);
            Medicamento remedioAtual = dao.getMedicamentoPorID(idRemedio);

            if (remedioAtual != null) {
                TelaEdicaoMedicamento telaEdit = new TelaEdicaoMedicamento(null, true);
                telaEdit.carregarDados(remedioAtual);
                telaEdit.setLocationRelativeTo(null);
                telaEdit.setVisible(true);

                carregarTabelaRemedios();
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaMedicamentos dialog = new TelaMedicamentos(new javax.swing.JFrame(), true, 0, "Teste", "Teste");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblNomePaciente;
    private javax.swing.JTextField txtDosesRestantes;
    private javax.swing.JTextField txtFrequencia;
    private javax.swing.JTextField txtInstrucoes;
    private javax.swing.JTextField txtMotivoUso;
    private javax.swing.JTextField txtNomeRemedio;
    private javax.swing.JTextField txtTipoMedicamento;
    // End of variables declaration//GEN-END:variables
}
