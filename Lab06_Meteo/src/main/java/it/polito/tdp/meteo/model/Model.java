package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO mdao = new MeteoDAO();
	private int costMin = Integer.MAX_VALUE;

	public Model() {

	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		String out = "";
		
		List<Rilevamento> milano = mdao.getAllRilevamentiLocalitaMese(mese, "Milano");
		List<Rilevamento> torino = mdao.getAllRilevamentiLocalitaMese(mese, "Torino");
		List<Rilevamento> genova = mdao.getAllRilevamentiLocalitaMese(mese, "Genova");
		
		int sum = 0;
		for (Rilevamento r: milano) {
			sum += r.getUmidita();
		}
		double media = ((double) sum )/ milano.size();
		out += "Milano " + media + "\n";
		
		sum = 0;
		for (Rilevamento r: torino) {
			sum += r.getUmidita();
		}
		media = ((double) sum )/ milano.size();
		out += "Torino " + media + "\n";
		
		sum = 0;
		for (Rilevamento r: genova) {
			sum += r.getUmidita();
		}
		media = ((double) sum )/ milano.size();
		out += "Genova " + media;
		
		return out;
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		String out = "";
		this.costMin = Integer.MAX_VALUE;
		List<Rilevamento> res = this.recursive(new ArrayList<Rilevamento>(), mese);
		if (res == null) {
			return "Non è stata trovata una combinazione";
		} else {
			for (Rilevamento r: res) {
				out += r + "\n";
			}
			out += "Costo totale: " + this.costMin;
		}
		return out;
	}
	
	private List<Rilevamento> recursive(List<Rilevamento> in, int mese) {
		int level = in.size();
		if (level == NUMERO_GIORNI_TOTALI) {
			costMin = Model.costoSequenza(in);
			return in;
		}
		List<Rilevamento> out = null;
		List<Rilevamento> rilevamentiOggi = mdao.getAllRilevamentiGiornoMese(level + 1, mese);
		for (Rilevamento r: rilevamentiOggi) {
			in.add(r);
			
			if (Model.costoSequenza(in) < costMin && Model.sequenzaValida(in)) {
				List<Rilevamento> rec = recursive(in, mese);
				if (rec != null) {
					out = new ArrayList<Rilevamento>(rec);
				}
			}
			
			in.remove(level);
		}
		
		return out;
	}
	
	private static boolean sequenzaValida(List<Rilevamento> seq) {
		int countMilano = 0;
		int countTorino = 0;
		int countGenova = 0;
		int countSeq = 0;

		for (int i = 0; i < seq.size(); i++) {
			Rilevamento r = seq.get(i);
			char citta = r.getLocalita().charAt(0);
			

			if (i > 0) {
				char vecchiaCitta = seq.get(i-1).getLocalita().charAt(0);
				if (citta != vecchiaCitta) {
					if (countSeq < NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
						return false;
					} else {
						countSeq = 0;
					}
				}
			}
			countSeq++;

			switch (citta) {
			case 'M':
				countMilano++;
				break;
			case 'T':
				countTorino++;
				break;
			case 'G':
				countGenova++;
				break;
			default:
				return false;
			}

			if (countMilano > NUMERO_GIORNI_CITTA_MAX || countTorino > NUMERO_GIORNI_CITTA_MAX || countGenova > NUMERO_GIORNI_CITTA_MAX) {
				return false;
			}
		}
		return true;
	}
	

	private static int costoSequenza(List<Rilevamento> seq) {
		int costo = 0;
		for (int i = 0; i < seq.size() - 1; i++) {
			costo += seq.get(i).getUmidita();
			if (!seq.get(i).getLocalita().contentEquals(seq.get(i+1).getLocalita())) {
				costo += COST;
			}
		}
		costo += seq.get(seq.size()-1).getUmidita();
		return costo;
	}

}
