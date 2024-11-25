package urna_eletronica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import urna_eletronica.util.Conexao;

public class VisualizarVotosDAO {
	public List<Object[]> listarVotosCandidatos() throws SQLException {
		List<Object[]> candidatos = new ArrayList<>(); 
		
		final String SQL_BUSCAR_VOTOS_CANDIDATOS = "SELECT numero, nome_candidato, nome_partido, votos FROM view_candidatos_votos;";
		
		try(Connection conn = Conexao.conectar();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(SQL_BUSCAR_VOTOS_CANDIDATOS)) {
				while(rs.next()) {
					int numeroCandidato = rs.getInt("numero");
					String nomeCandidato = rs.getString("nome_candidato");
					String nomePartido = rs.getString("nome_partido");
					int votos = rs.getInt("votos");
					
					candidatos.add(new Object[] {numeroCandidato, nomeCandidato, nomePartido, votos});
				}
			}
	
	return candidatos;
	}
}
	
