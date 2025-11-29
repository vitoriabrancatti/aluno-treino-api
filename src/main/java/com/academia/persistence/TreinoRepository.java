package com.academia.persistence;

import com.academia.domain.Aluno;
import com.academia.domain.Treino;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreinoRepository {
    private final ConnectionProvider connectionProvider;

    public TreinoRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Treino save(Treino treino) {
        String sql = "INSERT INTO treino (descricao, data, duracao_minutos, nivel, aluno_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            applyStatement(treino, statement);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    treino.setId(keys.getLong(1));
                }
            }
            return treino;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao salvar treino", e);
        }
    }

    public List<Treino> findAll() {
        String sql = "SELECT id, descricao, data, duracao_minutos, nivel, aluno_id FROM treino";
        return queryTreinos(sql, null);
    }

    public Optional<Treino> findById(Long id) {
        String sql = "SELECT id, descricao, data, duracao_minutos, nivel, aluno_id FROM treino WHERE id = ?";
        List<Treino> treinos = queryTreinos(sql, statement -> statement.setLong(1, id));
        if (treinos.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(treinos.get(0));
    }

    public List<Treino> findByAlunoId(Long alunoId) {
        String sql = "SELECT id, descricao, data, duracao_minutos, nivel, aluno_id FROM treino WHERE aluno_id = ?";
        return queryTreinos(sql, statement -> statement.setLong(1, alunoId));
    }

    public void update(Treino treino) {
        String sql = "UPDATE treino SET descricao = ?, data = ?, duracao_minutos = ?, nivel = ?, aluno_id = ? WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            applyStatement(treino, statement);
            statement.setLong(6, treino.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar treino", e);
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM treino WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover treino", e);
        }
    }

    private void applyStatement(Treino treino, PreparedStatement statement) throws SQLException {
        statement.setString(1, treino.getDescricao());
        statement.setDate(2, Date.valueOf(treino.getData()));
        statement.setInt(3, treino.getDuracaoMinutos());
        statement.setString(4, treino.getNivel());
        statement.setLong(5, treino.getAluno().getId());
    }

    private List<Treino> queryTreinos(String sql, StatementConfigurer configurer) {
        List<Treino> treinos = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (configurer != null) {
                configurer.accept(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    treinos.add(mapRow(resultSet));
                }
            }
            return treinos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar treinos", e);
        }
    }

    private Treino mapRow(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String descricao = resultSet.getString("descricao");
        LocalDate data = resultSet.getDate("data").toLocalDate();
        int duracao = resultSet.getInt("duracao_minutos");
        String nivel = resultSet.getString("nivel");
        Long alunoId = resultSet.getLong("aluno_id");
        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        return new Treino(id, descricao, data, duracao, nivel, aluno);
    }

    @FunctionalInterface
    private interface StatementConfigurer {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
