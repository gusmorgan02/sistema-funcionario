package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    private static final String URL = "jdbc:sqlite:empresa.db";

    public static Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(URL);
        criarTabela(conexao);
        return conexao;
    }

    private static void criarTabela(Connection conexao) {
        String sql = "CREATE TABLE IF NOT EXISTS funcionario (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "nome TEXT NOT NULL, " +
                     "cargo TEXT NOT NULL, " +
                     "departamento TEXT NOT NULL, " +
                     "salario REAL NOT NULL, " +
                     "carga_horaria INTEGER NOT NULL, " +
                     "data_admissao DATE NOT NULL" +
                     ");";
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}