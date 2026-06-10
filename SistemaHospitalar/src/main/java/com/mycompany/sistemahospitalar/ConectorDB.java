package com.mycompany.sistemahospitalar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    String sql = "SHOW COLUMNS FROM pacientes";
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
            Statement statement = conexao.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Column Name: " + rs.getString("Field"));
                System.out.println("Data Type: " + rs.getString("Type"));
            }

    } catch (SQLException e) {
        System.err.println("Erro ao CONECTAR: " + e.getMessage());
    }
    sql = "SHOW COLUMNS FROM medicamentos";
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
            Statement statement = conexao.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Column Name: " + rs.getString("Field"));
                System.out.println("Data Type: " + rs.getString("Type"));
            }

    } catch (SQLException e) {
        System.err.println("Erro ao CONECTAR: " + e.getMessage());
    }
    return true;
  }
}