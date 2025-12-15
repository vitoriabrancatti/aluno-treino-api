package com.academia.persistence;

import com.academia.domain.Treinador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreinadorRepository {

    private final ConnectionProvider connectionProvider;

    public TreinadorRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Treinador save(Treinador treinador) {
        String sql = "INSERT INTO treinador (nome, cpf, email, idade, especialidade) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, treinador.getNome());
            statement.setString(2, treinador.getCpf());
            statement.setString(3, treinador.getEmail());
            statement.setInt(4, treinador.getIdade());
            statement.setString(5, treinador.getEspecialidade());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    treinador.setId(keys.getLong(1));
                }
            }
            return treinador;

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao salvar treinador", e);
        }
    }

    public List<Treinador> findAll() {
        String sql = "SELECT id, nome, cpf, email, idade, especialidade FROM treinador";
        List<Treinador> treinadores = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                treinadores.add(mapRow(resultSet));
            }
            return treinadores;

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar treinadores", e);
        }
    }

    public Optional<Treinador> findById(Long id) {
        String sql = "SELECT id, nome, cpf, email, idade, especialidade FROM treinador WHERE id = ?";
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
            throw new IllegalStateException("Erro ao buscar treinador", e);
        }
    }

    public void update(Treinador treinador) {
        String sql = """
                UPDATE treinador
                SET nome = ?, cpf = ?, email = ?, idade = ?, especialidade = ?
                WHERE id = ?
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, treinador.getNome());
            statement.setString(2, treinador.getCpf());
            statement.setString(3, treinador.getEmail());
            statement.setInt(4, treinador.getIdade());
            statement.setString(5, treinador.getEspecialidade());
            statement.setLong(6, treinador.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar treinador", e);
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM treinador WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover treinador", e);
        }
    }

    private Treinador mapRow(ResultSet resultSet) throws SQLException {
        Treinador treinador = new Treinador();
        treinador.setId(resultSet.getLong("id"));
        treinador.setNome(resultSet.getString("nome"));
        treinador.setCpf(resultSet.getString("cpf"));
        treinador.setEmail(resultSet.getString("email"));
        treinador.setIdade(resultSet.getInt("idade"));
        treinador.setEspecialidade(resultSet.getString("especialidade"));
        return treinador;
    }
}
