package com.mycompany.sistemahospitalar;

import com.mycompany.sistemahospitalar.Paciente;
import com.mycompany.sistemahospitalar.ConectorDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private ConectorDB conectorDB;

    public PacienteDAO(ConectorDB conectorDB) {
        this.conectorDB = conectorDB;
    }

    public void cadastrarPaciente(Paciente paciente){
        String sql = "INSERT INTO pacientes (nome_paciente, sobrenome, genero, data_nascimento, numero_quarto, telefone_familia, medico, telefone_medico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

            ps.setString(1, paciente.getNome_paciente());
            ps.setString(2, paciente.getSobrenome());
            ps.setString(3, paciente.getGenero());
            ps.setDate(4, Date.valueOf(paciente.getData_nascimento()));
            ps.setString(5, paciente.getNumero_quarto());
            ps.setString(6, paciente.getTelefone_familia());
            ps.setString(7, paciente.getMedico());
            ps.setString(8, paciente.getTelefone_medico());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Paciente> listarPacientes(){
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Paciente paciente = new Paciente(
                        rs.getString("nome_paciente"),
                        rs.getString("sobrenome"),
                        rs.getString("genero"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("numero_quarto"),
                        rs.getString("telefone_familia"),
                        rs.getString("medico"),
                        rs.getString("telefone_medico")
                );
                
                paciente.setId_paciente(rs.getInt("id_paciente"));
                
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pacientes;
    }

    public void atualizarPaciente(Paciente paciente){
        String sql = "UPDATE pacientes SET nome_paciente = ?, sobrenome = ?, genero = ?, data_nascimento = ?, numero_quarto = ?, telefone_familia = ?, medico = ?, telefone_medico = ? WHERE id_paciente = ?";

        try{
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

            ps.setString(1, paciente.getNome_paciente());
            ps.setString(2, paciente.getSobrenome());
            ps.setString(3, paciente.getGenero());
            ps.setDate(4, java.sql.Date.valueOf(paciente.getData_nascimento()));
            ps.setString(5, paciente.getNumero_quarto());
            ps.setString(6, paciente.getTelefone_familia());
            ps.setString(7, paciente.getMedico());
            ps.setString(8, paciente.getTelefone_medico());
            ps.setInt(9, paciente.getId_paciente());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirPaciente(int id){
        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}