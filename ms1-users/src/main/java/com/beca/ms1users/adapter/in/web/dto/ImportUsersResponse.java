package com.beca.ms1users.adapter.in.web.dto;

import java.util.List;

public class ImportUsersResponse {

    private int totalImportados;
    private List<UserResponse> usuarios;

    public ImportUsersResponse() {
    }

    public ImportUsersResponse(int totalImportados, List<UserResponse> usuarios) {
        this.totalImportados = totalImportados;
        this.usuarios = usuarios;
    }

    public int getTotalImportados() {
        return totalImportados;
    }

    public void setTotalImportados(int totalImportados) {
        this.totalImportados = totalImportados;
    }

    public List<UserResponse> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserResponse> usuarios) {
        this.usuarios = usuarios;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int totalImportados;
        private List<UserResponse> usuarios;

        public Builder totalImportados(int totalImportados) {
            this.totalImportados = totalImportados;
            return this;
        }

        public Builder usuarios(List<UserResponse> usuarios) {
            this.usuarios = usuarios;
            return this;
        }

        public ImportUsersResponse build() {
            return new ImportUsersResponse(totalImportados, usuarios);
        }
    }
}
