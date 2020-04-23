package it.polito.tdp.meteo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	private ConnectDB conn;
	
	public MeteoDAO() {
		this.conn = new ConnectDB();
	}
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			PreparedStatement st = conn.getConnection().prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE MONTH(Data) = ? AND Localita = ?";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			PreparedStatement st = conn.getConnection().prepareStatement(sql);
			
			st.setInt(1, mese);
			st.setString(2, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Rilevamento> getAllRilevamentiGiornoMese(int giorno, int mese) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE DAY(Data) = ? AND MONTH(Data) = ?";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			PreparedStatement st = conn.getConnection().prepareStatement(sql);
			
			st.setInt(1, giorno);
			st.setInt(2, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
