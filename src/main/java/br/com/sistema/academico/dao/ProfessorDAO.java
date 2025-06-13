package br.com.sistema.academico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.sistema.academico.database.DatabaseManager;
import br.com.sistema.academico.model.Professor;

public class ProfessorDAO implements Repository<Professor> {
    private final Connection connection;

    public ProfessorDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO professores (nome, cpf, departamento, email) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getDepartamento());
            stmt.setString(4, professor.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar professor: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Professor professor) {
        String sql = "UPDATE professores SET nome = ?, cpf = ?, departamento = ?, email = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getDepartamento());
            stmt.setString(4, professor.getEmail());
            stmt.setLong(5, professor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Professor professor) {
        String sql = "DELETE FROM professores WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, professor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir professor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Professor> findById(Long id) {
        String sql = "SELECT * FROM professores WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(createProfessorFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Professor> findAll() {
        String sql = "SELECT * FROM professores";
        List<Professor> professores = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                professores.add(createProfessorFromResultSet(rs));
            }
            return professores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os professores: " + e.getMessage(), e);
        }
    }

    public Optional<Professor> findByCpf(String cpf) {
        String sql = "SELECT * FROM professores WHERE cpf = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(createProfessorFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por CPF: " + e.getMessage(), e);
        }
    }

    private Professor createProfessorFromResultSet(ResultSet rs) throws SQLException {
        Professor professor = new Professor(
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("departamento"),
            rs.getString("email")
        );
        professor.setId(rs.getLong("id"));
        return professor;
    }
}
