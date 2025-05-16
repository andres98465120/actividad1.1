package com.iudigital.data;

import com.iudigital.domain.Funcionario;
import com.iudigital.exceptions.DatabaseException;
import com.iudigital.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private static final String GET_FUNCIONARIOS = "SELECT * FROM funcionario";
    private static final String GET_FUNCIONARIO_BY_ID = "SELECT * FROM funcionario WHERE id = ?";
    private static final String CREATE_FUNCIONARIO = "INSERT INTO funcionario (tipo_documento, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_FUNCIONARIO = "UPDATE funcionario SET tipo_documento = ?, numero_identificacion = ?, nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE id = ?";
    private static final String DELETE_FUNCIONARIO = "DELETE FROM funcionario WHERE id = ? ";

    public List<Funcionario> getFuncionarios() throws DatabaseException {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_FUNCIONARIOS)) {

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setTipoDocumento(resultSet.getString("tipo_documento"));
                funcionario.setNumeroDocumento(resultSet.getString("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstadoCivil(resultSet.getString("estado_civil"));
                funcionario.setSexo(resultSet.getString("sexo"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar funcionarios", e);
        }
        return funcionarios;

    }

    public Funcionario getFuncionario(int id) throws DatabaseException {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(resultSet.getInt("id"));
                    funcionario.setTipoDocumento(resultSet.getString("tipo_documento"));
                    funcionario.setNumeroDocumento(resultSet.getString("numero_identificacion"));
                    funcionario.setNombres(resultSet.getString("nombres"));
                    funcionario.setApellidos(resultSet.getString("apellidos"));
                    funcionario.setEstadoCivil(resultSet.getString("estado_civil"));
                    funcionario.setSexo(resultSet.getString("sexo"));
                    funcionario.setDireccion(resultSet.getString("direccion"));
                    funcionario.setTelefono(resultSet.getString("telefono"));
                    funcionario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento"));

                    return funcionario;
                }

            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar funcionario ", e);
        }
        return null;

    }

    public void create(Funcionario funcionario) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FUNCIONARIO, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, funcionario.getTipoDocumento());
            preparedStatement.setString(2, funcionario.getNumeroDocumento());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getEstadoCivil());

            // Solo primer caracter de sexo para evitar error de truncamiento
            String sexo = funcionario.getSexo();
            if (sexo != null && sexo.length() > 1) {
                sexo = sexo.substring(0, 1);
            }
            preparedStatement.setString(6, sexo);

            preparedStatement.setString(7, funcionario.getDireccion());
            preparedStatement.setString(8, funcionario.getTelefono());
            preparedStatement.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcionario.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar funcionario ", e);
        }
    }
    public void update(Funcionario funcionario) throws DatabaseException {

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO)) {

            preparedStatement.setString(1, funcionario.getTipoDocumento());
            preparedStatement.setString(2, funcionario.getNumeroDocumento());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getEstadoCivil());

       
            String sexo = funcionario.getSexo();
            if (sexo != null && sexo.length() > 1) {
                sexo = sexo.substring(0, 1);
            }
            preparedStatement.setString(6, sexo);

            preparedStatement.setString(7, funcionario.getDireccion());
            preparedStatement.setString(8, funcionario.getTelefono());
            preparedStatement.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            preparedStatement.setInt(10, funcionario.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar funcionario ", e);
        }
    }

    public void delete(int id) throws DatabaseException {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FUNCIONARIO)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar funcionario", e);

        }

    }
}
