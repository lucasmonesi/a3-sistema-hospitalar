package com.mycompany.sistemahospitalar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


class ConectorDB {
  private String url, usuario, senha;
  public ConectorDB(String url, String usuario, String senha) {
    this.url = url;
    this.usuario = usuario;
    this.senha = senha;
    testarConexao();   
  }

  public boolean testarConexao(){
    try (Connection connect = DriverManager.getConnection(url, usuario, senha) ) {
      connect.close();
      return true;
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Connection getConexao(){
      try {
          return DriverManager.getConnection(url, usuario, senha);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
  public boolean criarBanco() {
    String sql = "CREATE DATABASE IF NOT EXISTS hospital";
    try (Connection connect = DriverManager.getConnection(url, usuario, senha);
      Statement statement = connect.createStatement()) {
      statement.executeUpdate(sql);
      System.out.println("Banco de dados 'hospital' criado com sucesso!");
    }
    catch (SQLException e) {
      System.err.println("Erro ao criar o banco de dados: " + e.getMessage());
    }
    return true;
  }
  
  public boolean criarTabelas() {
    String criarTabelaPacientes = 
        "CREATE TABLE IF NOT EXISTS pacientes (" +
            "id_paciente INT AUTO_INCREMENT PRIMARY KEY," +
            "nome_paciente VARCHAR(100) NOT NULL," +
            "sobrenome VARCHAR(100)," +
            "genero VARCHAR(20)," +
            "data_nascimento DATE," +
            "numero_quarto VARCHAR(7)," + 
            "telefone_familia VARCHAR(20)," +
            "medico VARCHAR(100)," +
            "telefone_medico VARCHAR(20)"+
        ");";
        
    String criarTabelaMedicamentos = 
        "CREATE TABLE IF NOT EXISTS medicamentos (" +
            "id_medicamento INT AUTO_INCREMENT PRIMARY KEY," +
            "id_paciente INT," + 
            "nome_medicamento VARCHAR(100) NOT NULL," +
            "tipo_medicamento VARCHAR(100)," +
            "instrucoes VARCHAR(3000)," +
            "frequencia INT," + // minutos entre doses
            "ultima_dose TIMESTAMP," +
            "doses_restantes INT," +
            "motivo_uso VARCHAR(500)," +
            "FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE" +
        ");";
        
    try (Connection connect = DriverManager.getConnection(url, usuario, senha);
      Statement statement = connect.createStatement()) {
      statement.executeUpdate(criarTabelaPacientes);
      System.out.println("Tabela 'pacientes' criada com sucesso!");
      statement.executeUpdate(criarTabelaMedicamentos);
      System.out.println("Tabela 'medicamento' criada com sucesso!");
    }
    catch (SQLException e) {
      System.err.println("Erro ao criar a tabela: " + e.getMessage());
    }
    return true;
  }
}