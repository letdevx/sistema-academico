package br.com.sistema.academico.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/data/sistema_academico.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inicializar o banco de dados: " + e.getMessage(), e);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Tabela de Professores
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS professores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    cpf TEXT UNIQUE NOT NULL,
                    departamento TEXT NOT NULL,
                    email TEXT NOT NULL
                )
            """);

            // Tabela de Alunos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS alunos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    cpf TEXT UNIQUE NOT NULL,
                    matricula TEXT UNIQUE NOT NULL,
                    email TEXT NOT NULL
                )
            """);

            // Tabela de Disciplinas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS disciplinas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo TEXT UNIQUE NOT NULL,
                    nome TEXT NOT NULL,
                    carga_horaria INTEGER NOT NULL,
                    professor_id INTEGER,
                    FOREIGN KEY (professor_id) REFERENCES professores (id)
                )
            """);

            // Tabela de Turmas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS turmas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo TEXT UNIQUE NOT NULL,
                    disciplina_id INTEGER NOT NULL,
                    periodo TEXT NOT NULL,
                    FOREIGN KEY (disciplina_id) REFERENCES disciplinas (id)
                )
            """);

            // Tabela de Matrículas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS matriculas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    aluno_id INTEGER NOT NULL,
                    turma_id INTEGER NOT NULL,
                    nota REAL,
                    FOREIGN KEY (aluno_id) REFERENCES alunos (id),
                    FOREIGN KEY (turma_id) REFERENCES turmas (id),
                    UNIQUE(aluno_id, turma_id)
                )
            """);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
