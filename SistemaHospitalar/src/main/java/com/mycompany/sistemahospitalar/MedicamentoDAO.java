package com.mycompany.sistemahospitalar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {
    private ConectorDB conectorDB;

    public MedicamentoDAO(ConectorDB conectorDB){
        this.conectorDB = conectorDB;
    }

    public void cadastrarMedicamento(Medicamento medicamento){
        String sql = "INSERT INTO medicamentos ("+
        "id_paciente, nome_medicamento," +  
        "tipo_medicamento, instrucoes," +
        "frequencia, ultima_dose," +
        "doses_restantes, motivo_uso" +
        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, medicamento.getId_paciente());
            ps.setString(2, medicamento.getNome_medicamento());
            ps.setString(3, medicamento.getTipo_medicamento());
            ps.setString(4, medicamento.getInstrucoes());
            ps.setInt(5,medicamento.getFrequencia());
            ps.setObject(6, medicamento.getUltima_dose());
            ps.setInt(7, medicamento.getDoses_restantes());
            ps.setString(8, medicamento.getMotivo_uso());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Criacao de medicamento falhou.");
                }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    medicamento.setId_medicamento(generatedKeys.getInt(1)); 
                }
                else {
                    throw new SQLException("Criacao de medicamento falhou, nenhum ID obtido.");
                }
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }   
    }

    public Medicamento converterResultParaMedicamento(ResultSet rs){
        try {
            LocalDateTime ldt = null;
            if(rs.getTimestamp("ultima_dose") != null)
                ldt = rs.getTimestamp("ultima_dose").toLocalDateTime();
            Medicamento medicamento = new Medicamento(
                rs.getInt("id_medicamento"),
                rs.getInt("id_paciente"),
                rs.getString("nome_medicamento"),
                rs.getString("tipo_medicamento"),
                rs.getString("instrucoes"),
                rs.getInt("frequencia"),
                ldt,
                rs.getInt("doses_restantes"),
                rs.getString("motivo_uso")
            );
            return medicamento;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Medicamento> listarMedicamentos(){
        String sql = "SELECT * FROM medicamentos";
        List<Medicamento> medicamentos = new ArrayList<>();

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                medicamentos.add(converterResultParaMedicamento(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicamentos;

    }

    public void atualizarMedicamento(Medicamento medicamento){
    String sql = "UPDATE medicamentos SET id_paciente = ?, nome_medicamento = ?,"+
                 "tipo_medicamento = ?, instrucoes = ?,"+ 
                 "frequencia = ?, ultima_dose = ?,"+
                 "doses_restantes = ?, motivo_uso = ?"+
                 "WHERE id_medicamento = ?";

    try{
        PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

        ps.setInt(1, medicamento.getId_paciente());
        ps.setString(2, medicamento.getNome_medicamento());
        ps.setString(3, medicamento.getTipo_medicamento());
        ps.setString(4, medicamento.getInstrucoes());
        ps.setInt(5, medicamento.getFrequencia());
        ps.setObject(6, medicamento.getUltima_dose());
        ps.setInt(7, medicamento.getDoses_restantes());
        ps.setString(8, medicamento.getMotivo_uso());
        ps.setInt(9, medicamento.getId_medicamento());

        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    
    public void excluirMedicamentoPorID(int id){
        String sql = "DELETE FROM medicamentos WHERE id_medicamento = ?";

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Medicamento getMedicamentoPorID(int id){
        String sql = "SELECT * FROM medicamentos WHERE id_medicamento = ?";

        try {
            PreparedStatement ps = conectorDB.getConexao().prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return converterResultParaMedicamento(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}


