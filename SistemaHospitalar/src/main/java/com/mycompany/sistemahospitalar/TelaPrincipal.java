/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemahospitalar;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author luca0
 */
public class TelaPrincipal extends javax.swing.JFrame {

    // IDs de pacientes com pelo menos um medicamento atrasado
    private final Set<Integer> pacientesAtrasados = new HashSet<>();

    public TelaPrincipal() {
        initComponents();
        carregarTabela();

        // Atualiza as cores a cada 1 minuto automaticamente
        javax.swing.Timer timer = new javax.swing.Timer(60000, e -> {
            recalcularAtrasados();
            jTable1.repaint();
        });
        timer.start();
    }

    /**
     * Consulta o banco uma única vez e preenche o Set pacientesAtrasados.
     * Chamado antes de qualquer repaint/render.
     */
    private void recalcularAtrasados() {
        pacientesAtrasados.clear();
        try {
            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/hospital", "root", "root");
            MedicamentoDAO dao = new MedicamentoDAO(conector);

            for (Medicamento m : dao.listarMedicamentos()) {
                int frequencia     = m.getFrequencia();
                int dosesRestantes = m.getDoses_restantes();
                LocalDateTime ultimaDose = m.getUltima_dose();

                if (frequencia <= 0 || dosesRestantes == 0) continue;

                boolean atrasado;
                if (ultimaDose == null) {
                    atrasado = true;
                } else {
                    atrasado = LocalDateTime.now().isAfter(ultimaDose.plusMinutes(frequencia));
                }

                if (atrasado) {
                    pacientesAtrasados.add(m.getId_paciente());
                }
            }
        } catch (Exception ex) {
            // Sem conexão: não marca ninguém
        }
    }

    public void carregarTabela() {
        javax.swing.table.DefaultTableModel modeloTabela = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modeloTabela.setRowCount(0);

        try {
            ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/hospital", "root", "root");
            PacienteDAO dao = new PacienteDAO(conector);

            for (Paciente p : dao.listarPacientes()) {
                modeloTabela.addRow(new Object[]{
                    p.getId_paciente(),
                    p.getNome_paciente(),
                    p.getSobrenome(),
                    p.getGenero(),
                    p.getData_nascimento(),
                    p.getNumero_quarto(),
                    p.getTelefone_familia(),
                    p.getMedico(),
                    p.getTelefone_medico()
                });
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao recarregar a tabela: " + ex.getMessage());
        }

        recalcularAtrasados();
        aplicarRenderer();
    }

    /**
     * Instala o renderer na tabela. Usa o Set já calculado — sem tocar no banco.
     * Precisa ser chamado de novo sempre que setRowSorter for usado,
     * pois o sorter pode redefinir o renderer.
     */
    private void aplicarRenderer() {
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                try {
                    int modelRow = table.convertRowIndexToModel(row);
                    int idPaciente = Integer.parseInt(
                            table.getModel().getValueAt(modelRow, 0).toString()
                    );

                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                        c.setForeground(table.getSelectionForeground());
                    } else if (pacientesAtrasados.contains(idPaciente)) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                } catch (Exception ex) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Buscar Pacientes:");

        txtBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscaActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Sobrenome", "Gênero", "Data Nasc.", "Quarto", "Tel. Família", "Médico", "Tel. Médico"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Novo Paciente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Gerenciar Remédios");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Excluir Selecionado");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Editar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Atualizar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String busca = txtBusca.getText();

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(modelo);
        jTable1.setRowSorter(sorter);

        if (busca.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + busca));
        }

        // Reinstala o renderer pois setRowSorter pode resetá-lo
        aplicarRenderer();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TelaCadastro tela = new TelaCadastro(this, true);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

        carregarTabela();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int linhaSelecionada = jTable1.getSelectedRow();

        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "selecione um paciente na tabela");
            return;
        }

        int id = Integer.parseInt(jTable1.getValueAt(linhaSelecionada, 0).toString());
        String nome = jTable1.getValueAt(linhaSelecionada, 1).toString();
        String sobrenome = jTable1.getValueAt(linhaSelecionada, 2).toString();

        TelaMedicamentos tela = new TelaMedicamentos(this, true, id, nome, sobrenome);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

        carregarTabela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "selecione um paciente na tabela");
            return;
        }

        int id = Integer.parseInt(jTable1.getValueAt(linha, 0).toString());
        String nome = jTable1.getValueAt(linha, 1).toString();
        String sobrenome = jTable1.getValueAt(linha, 2).toString();
        String genero = jTable1.getValueAt(linha, 3).toString();
        String data = jTable1.getValueAt(linha, 4).toString();
        String quarto = jTable1.getValueAt(linha, 5).toString();
        String telFam = jTable1.getValueAt(linha, 6).toString();
        String medico = jTable1.getValueAt(linha, 7).toString();
        String telMed = jTable1.getValueAt(linha, 8).toString();

        TelaEdicaoPaciente telaEdit = new TelaEdicaoPaciente(this, true);
        telaEdit.carregarDados(id, nome, sobrenome, genero, data, quarto, telFam, medico, telMed);
        telaEdit.setLocationRelativeTo(null);
        telaEdit.setVisible(true);

        carregarTabela();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "selecione um paciente na tabela");
            return;
        }

        int resposta = javax.swing.JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (resposta == javax.swing.JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(jTable1.getValueAt(linha, 0).toString());

                ConectorDB conector = new ConectorDB("jdbc:mysql://localhost:3306/hospital", "root", "root");
                PacienteDAO dao = new PacienteDAO(conector);

                dao.excluirPaciente(id);

                ((javax.swing.table.DefaultTableModel) jTable1.getModel()).removeRow(linha);
                javax.swing.JOptionPane.showMessageDialog(this, "Excluído com sucesso!");

            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        carregarTabela();
    }//GEN-LAST:event_jButton6ActionPerformed

    public static void main(String args[]) {
        ConectorDB criadorDataBase = new ConectorDB("jdbc:mysql://localhost:3306", "root", "root");
        criadorDataBase.criarBanco();
        ConectorDB conectorData = new ConectorDB("jdbc:mysql://localhost:3306/hospital", "root", "root");
        conectorData.criarTabelas();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBusca;
    // End of variables declaration//GEN-END:variables
}