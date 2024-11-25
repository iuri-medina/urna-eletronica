package urna_eletronica.dao;

import urna_eletronica.model.Candidato;
import urna_eletronica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAO {

    public void cadastrarCandidato(Candidato candidato) throws SQLException {
        String sql = "INSERT INTO candidato (nome, partido_id, numero) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, candidato.getNome());
            stmt.setInt(2, candidato.getPartidoId());
            stmt.setInt(3, candidato.getNumero());
            stmt.executeUpdate();
        }
    }

    public List<Candidato> listarCandidatos() throws SQLException {
        String sql = "SELECT * FROM candidato";
        List<Candidato> candidatos = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Candidato candidato = new Candidato(
                    rs.getString("nome"),
                    rs.getInt("partido_id"),
                    rs.getInt("numero")
                );
                candidato.setId(rs.getInt("id"));
                candidatos.add(candidato);
            }
        }
        return candidatos;
    }
}
