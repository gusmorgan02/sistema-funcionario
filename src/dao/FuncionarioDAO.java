package dao;

import conexao.Conexao;
import entidade.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public boolean inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, cargo, departamento, salario, carga_horaria, data_admissao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getDepartamento());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.setInt(5, funcionario.getCargaHoraria());
            stmt.setDate(6, funcionario.getDataAdmissao());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCargo(rs.getString("cargo"));
                f.setDepartamento(rs.getString("departamento"));
                f.setSalario(rs.getDouble("salario"));
                f.setCargaHoraria(rs.getInt("carga_horaria"));
                f.setDataAdmissao(rs.getDate("data_admissao"));

                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, cargo = ?, departamento = ?, salario = ?, carga_horaria = ?, data_admissao = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getDepartamento());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.setInt(5, funcionario.getCargaHoraria());
            stmt.setDate(6, funcionario.getDataAdmissao());
            stmt.setInt(7, funcionario.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }
}