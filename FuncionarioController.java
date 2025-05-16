package com.iudigital.controller;

import com.iudigital.domain.Funcionario;
import com.iudigital.exceptions.DatabaseException;
import com.iudigital.data.FuncionarioDao;

import java.util.List;

public class FuncionarioController {

    private final FuncionarioDao funcionarioDao;

    public FuncionarioController() {
        this.funcionarioDao = new FuncionarioDao();
    }

    // Crear un nuevo funcionario
    public void createFuncionario(Funcionario funcionario) throws DatabaseException {
        try {
            funcionarioDao.create(funcionario);
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar el funcionario", e);
        }
    }

    // Obtener todos los funcionarios
    public List<Funcionario> getFuncionarios() throws DatabaseException {
        try {
            return funcionarioDao.getFuncionarios(); 
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener los funcionarios", e);
        }
    }

    // Obtener un funcionario por su ID
    public Funcionario getFuncionario(int id) throws DatabaseException {
        try {
            return funcionarioDao.getFuncionario(id); // corregido: nombre del método exacto
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener el funcionario con ID: " + id, e);
        }
    }

    // Actualizar los datos de un funcionario
    public void updateFuncionario(Funcionario funcionario) throws DatabaseException {
        try {
            funcionarioDao.update(funcionario);
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar el funcionario", e);
        }
    }

    // Eliminar un funcionario por su ID
    public void deleteFuncionario(int id) throws DatabaseException {
        try {
            funcionarioDao.delete(id);
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar el funcionario con ID: " + id, e);
        }
    }
}
