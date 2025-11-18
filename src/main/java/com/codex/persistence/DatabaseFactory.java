package com.codex.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseFactory {
    private static final String URL = "jdbc:h2:./data/treinos";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        initializeIfNeeded();
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static synchronized void initializeIfNeeded() {
        if (initialized) {
            return;
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS aluno (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "nome VARCHAR(120) NOT NULL, " +
                    "cpf VARCHAR(20) NOT NULL UNIQUE, " +
                    "email VARCHAR(150) NOT NULL, " +
                    "idade INT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS treino (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "descricao VARCHAR(200) NOT NULL, " +
                    "data DATE NOT NULL, " +
                    "duracao_minutos INT NOT NULL, " +
                    "nivel VARCHAR(40) NOT NULL, " +
                    "aluno_id BIGINT NOT NULL, " +
                    "CONSTRAINT fk_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE)");
            initialized = true;
        } catch (SQLException e) {
            throw new IllegalStateException("Falha ao inicializar o banco de dados", e);
        }
    }
}
