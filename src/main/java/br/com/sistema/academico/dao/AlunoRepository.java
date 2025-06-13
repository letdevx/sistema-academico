package br.com.sistema.academico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.sistema.academico.database.DatabaseManager;
import br.com.sistema.academico.model.Aluno;

public class AlunoRepository implements Repository<Aluno> {
    private final Connection connection;

    public AlunoRepository() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    @Override
    public void save(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, cpf, data_nascimento, sexo, email, telefone, endereco, curso, grau, turno, data_ingresso, situacao, periodo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getDataNascimento());
            stmt.setString(4, aluno.getSexo());
            stmt.setString(5, aluno.getEmail());
            stmt.setString(6, aluno.getTelefone());
            stmt.setString(7, aluno.getEndereco());
            stmt.setString(8, aluno.getCurso());
            stmt.setString(9, aluno.getGrau());
            stmt.setString(10, aluno.getTurno());
            stmt.setString(11, aluno.getDataIngresso());
            stmt.setString(12, aluno.getSituacao());
            stmt.setString(13, aluno.getPeriodo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome=?, data_nascimento=?, sexo=?, email=?, telefone=?, endereco=?, curso=?, grau=?, turno=?, data_ingresso=?, situacao=?, periodo=? WHERE cpf=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setString(3, aluno.getSexo());
            stmt.setString(4, aluno.getEmail());
            stmt.setString(5, aluno.getTelefone());
            stmt.setString(6, aluno.getEndereco());
            stmt.setString(7, aluno.getCurso());
            stmt.setString(8, aluno.getGrau());
            stmt.setString(9, aluno.getTurno());
            stmt.setString(10, aluno.getDataIngresso());
            stmt.setString(11, aluno.getSituacao());
            stmt.setString(12, aluno.getPeriodo());
            stmt.setString(13, aluno.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Aluno aluno) {
        String sql = "DELETE FROM alunos WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Aluno> findById(Long id) {
        // Supondo que não há campo id, mas sim cpf como identificador único
        throw new UnsupportedOperationException("Use findByCpf para buscar aluno pelo CPF.");
    }

    public Optional<Aluno> findByCpf(String cpf) {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(createAlunoFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por CPF: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Aluno> findAll() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(createAlunoFromResultSet(rs));
            }
            return alunos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os alunos: " + e.getMessage(), e);
        }
    }

    private Aluno createAlunoFromResultSet(ResultSet rs) throws SQLException {
        return new Aluno(
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("data_nascimento"),
            rs.getString("sexo"),
            rs.getString("email"),
            rs.getString("telefone"),
            rs.getString("endereco"),
            rs.getString("curso"),
            rs.getString("grau"),
            rs.getString("turno"),
            rs.getString("data_ingresso"),
            rs.getString("situacao"),
            rs.getString("periodo")
        );
    }
}
