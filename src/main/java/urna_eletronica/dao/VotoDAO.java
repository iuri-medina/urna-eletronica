package urna_eletronica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import urna_eletronica.model.Partido;
import urna_eletronica.util.Conexao;

public class VotoDAO {
	
	private String nomeCandidato = "";
	private String nomePartido = "";
	
	public String getNomeCandidato() {
		return nomeCandidato;
	}

	public void setNomeCandidato(String nomeCandidato) {
		this.nomeCandidato = nomeCandidato;
	}

	public String getNomePartido() {
		return nomePartido;
	}

	public void setNomePartido(String nomePartido) {
		this.nomePartido = nomePartido;
	}

	
	public boolean confirmarCandidato(int numeroCandidato) {
		final String SQL_BUSCAR_CANDIDATO = "SELECT c.nome AS nomecandidato, p.nome AS nomepartido, COUNT(*) AS count FROM candidato c INNER JOIN partido p ON c.partido_id = p.id WHERE c.numero = " + numeroCandidato + " GROUP BY c.nome, p.nome";
		
		
		try (Connection conn = Conexao.conectar();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(SQL_BUSCAR_CANDIDATO)) {
	            while (rs.next()) {
	            		if(rs.getInt("count") > 0) {
	            			setNomeCandidato(rs.getString("nomecandidato"));
		                    setNomePartido(rs.getString("nomepartido")); 
		                    
		                    return true;
	            		}
	                      
	            }
		} catch (SQLException e) {
			System.out.println("Erro ao procurar candidato: " + e.getMessage());
			
		}
		return false;
	}
	
	
	public void votar(int numeroCandidato) {
		final String SQL_INSERIR_VOTO = "INSERT INTO voto (candidato_numero) VALUES ("+ numeroCandidato +");";
		try (Connection conn = Conexao.conectar();
	            PreparedStatement stmt = conn.prepareStatement(SQL_INSERIR_VOTO)) {
	            stmt.executeUpdate();
	        } catch (SQLException e) {
				System.out.println("Erro ao inserir voto no sistema: " + e.getMessage());
			}
	}
	
	
}
