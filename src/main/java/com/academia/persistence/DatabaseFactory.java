package com.academia.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseFactory implements ConnectionProvider {
    private static final String DEFAULT_URL = "jdbc:h2:file:./data/treinos";
    private static final String DEFAULT_USER = "sa";
    private static final String DEFAULT_PASSWORD = "";

    private final String url;
    private final String user;
    private final String password;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public DatabaseFactory() {
        this(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public DatabaseFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        initializeIfNeeded();
        return DriverManager.getConnection(url, user, password);
    }

    private void initializeIfNeeded() {
        if (initialized.compareAndSet(false, true)) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
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
            } catch (SQLException e) {
                throw new IllegalStateException("Falha ao inicializar o banco de dados", e);
            }
        }
    }
}
