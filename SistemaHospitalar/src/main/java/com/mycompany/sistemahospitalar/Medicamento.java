package com.mycompany.sistemahospitalar;

import java.time.LocalDateTime;

public class Medicamento {
    private int id_medicamento;
    private int id_paciente;
    private String nomeMedicamento;
    private String tipoMedicamento;
    private String instrucoes;
    private int frequencia;
    private LocalDateTime ultimaDose;
    private int dosesRestantes;
    private String motivoUso;
    
    public Medicamento(int id_paciente, String nomeMedicamento, String tipoMedicamento, String instrucoes, int frequencia, LocalDateTime ultimaDose, int dosesRestantes, String motivoUso){
        this.id_paciente = id_paciente;
        this.nomeMedicamento = nomeMedicamento;
        this.tipoMedicamento = tipoMedicamento;
        this.instrucoes = instrucoes;
        this.frequencia = frequencia;
        this.ultimaDose = ultimaDose;
        this.dosesRestantes = dosesRestantes;
        this.motivoUso = motivoUso;
    }

     public Medicamento(int id_medicamento, int id_paciente, String nomeMedicamento, String tipoMedicamento, String instrucoes, int frequencia, LocalDateTime ultimaDose, int dosesRestantes, String motivoUso){
        this.id_medicamento = id_medicamento;
        this.id_paciente = id_paciente;
        this.nomeMedicamento = nomeMedicamento;
        this.tipoMedicamento = tipoMedicamento;
        this.instrucoes = instrucoes;
        this.frequencia = frequencia;
        this.ultimaDose = ultimaDose;
        this.dosesRestantes = dosesRestantes;
        this.motivoUso = motivoUso;
    }

    public int getId_medicamento(){
        return id_medicamento;
    }

    public void setId_medicamento(int id_medicamento){
        this.id_medicamento = id_medicamento;
    }

    public int getId_paciente(){
        return id_paciente;
    }
    
    public void setId_Paciente(int id_paciente){
        this.id_paciente = id_paciente;
    }

    public String getNome_medicamento(){
        return nomeMedicamento;
    }

    public void setNome_medicamento(String nomeMedicamento){
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getTipo_medicamento(){
        return tipoMedicamento;
    }
    
    public void setTipo_medicamento(String tipoMedicamento){
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getInstrucoes(){
        return instrucoes;
    }
    
    public void setInstrucoes(String instrucoes){
        this.instrucoes = instrucoes;
    }

    public int getFrequencia(){
        return frequencia;
    }
    
    public void setFrequencia(int frequencia){
        this.frequencia = frequencia;
    }

    public LocalDateTime getUltima_dose(){
        return ultimaDose;
    }

    public void setUltima_dose(LocalDateTime ultimaDose){
        this.ultimaDose = ultimaDose;
    }

    public int getDoses_restantes(){
        return dosesRestantes;
    }

    public void setDoses_restantes(int dosesRestantes){
        this.dosesRestantes = dosesRestantes;
    }

    public String getMotivo_uso(){
        return motivoUso;
    }

    public void setMotivo_uso(String motivoUso){
        this.motivoUso = motivoUso;
    }
    
    @Override
    public String toString() {
        String medicamentoStr = "ID: %d | Nome: %s | ID paciente: %d";
        return String.format(medicamentoStr, id_medicamento, nomeMedicamento, id_paciente);
    }
}
