package com.mycompany.sistemahospitalar;

import java.time.LocalDate;

public class Paciente {
    private int id_paciente;
    private String nome_paciente;
    private String sobrenome;
    private String genero;
    private LocalDate data_nascimento;
    private String numero_quarto;
    private String telefone_familia;
    private String medico;
    private String telefone_medico;

    public Paciente(String nome_paciente, String sobrenome, String genero, LocalDate data_nascimento, String numero_quarto, String telefone_familia, String medico, String telefone_medico) {
        this.nome_paciente = nome_paciente;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.data_nascimento = data_nascimento;
        this.numero_quarto = numero_quarto;
        this.telefone_familia = telefone_familia;
        this.medico = medico;
        this.telefone_medico = telefone_medico;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNome_paciente() {
        return nome_paciente;
    }

    public void setNome_paciente(String nome_paciente) {
        this.nome_paciente = nome_paciente;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getNumero_quarto() {
        return numero_quarto;
    }

    public void setNumero_quarto(String numero_quarto) {
        this.numero_quarto = numero_quarto;
    }

    public String getTelefone_familia() {
        return telefone_familia;
    }

    public void setTelefone_familia(String telefone_familia) {
        this.telefone_familia = telefone_familia;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getTelefone_medico() {
        return telefone_medico;
    }

    public void setTelefone_medico(String telefone_medico) {
        this.telefone_medico = telefone_medico;
    }
}
