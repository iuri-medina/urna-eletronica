package urna_eletronica.dao;

import urna_eletronica.model.Partido;
import urna_eletronica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    public void cadastrarPartido(Partido partido) throws SQLException {
        String sql = "INSERT INTO partido (nome, sigla) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, partido.getNome());
            stmt.setString(2, partido.getSigla());
            stmt.executeUpdate();
        }
    }

    public List<Partido> listarPartidos() throws SQLException {
        String sql = "SELECT * FROM partido WHERE id <> 1";
        List<Partido> partidos = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Partido partido = new Partido(
                    rs.getString("nome"),
                    rs.getString("sigla")
                );
                partido.setId(rs.getInt("id"));
                partidos.add(partido);
            }
        }
        return partidos;
    }
}
