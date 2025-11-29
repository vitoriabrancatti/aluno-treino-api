package com.academia.persistence;

import com.academia.domain.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoRepository {
    private final ConnectionProvider connectionProvider;

    public AlunoRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Aluno save(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, email, idade) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getCpf());
            statement.setString(3, aluno.getEmail());
            statement.setInt(4, aluno.getIdade());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    aluno.setId(keys.getLong(1));
                }
            }
            return aluno;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao salvar aluno", e);
        }
    }

    public List<Aluno> findAll() {
        String sql = "SELECT id, nome, cpf, email, idade FROM aluno";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                alunos.add(mapRow(resultSet));
            }
            return alunos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar alunos", e);
        }
    }

    public Optional<Aluno> findById(Long id) {
        String sql = "SELECT id, nome, cpf, email, idade FROM aluno WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar aluno", e);
        }
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, cpf = ?, email = ?, idade = ? WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getCpf());
            statement.setString(3, aluno.getEmail());
            statement.setInt(4, aluno.getIdade());
            statement.setLong(5, aluno.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar aluno", e);
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover aluno", e);
        }
    }

    private Aluno mapRow(ResultSet resultSet) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(resultSet.getLong("id"));
        aluno.setNome(resultSet.getString("nome"));
        aluno.setCpf(resultSet.getString("cpf"));
        aluno.setEmail(resultSet.getString("email"));
        aluno.setIdade(resultSet.getInt("idade"));
        return aluno;
    }
}
