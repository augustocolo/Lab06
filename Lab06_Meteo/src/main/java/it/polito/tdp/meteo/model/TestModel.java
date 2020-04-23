package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		System.out.println("\nGennaio\n");
		System.out.println(m.trovaSequenza(1));
		System.out.println("\nFebbraio\n");
		System.out.println(m.trovaSequenza(2));
		System.out.println("\nMarzo\n");
		System.out.println(m.trovaSequenza(3));
		System.out.println("\nAprile\n");
		System.out.println(m.trovaSequenza(4));
		System.out.println("\nMaggio\n");
		System.out.println(m.trovaSequenza(5));
		System.out.println("\nGiugno\n");
		System.out.println(m.trovaSequenza(6));
		System.out.println("\nLuglio\n");
		System.out.println(m.trovaSequenza(7));
		System.out.println("\nAgosto\n");
		System.out.println(m.trovaSequenza(8));
		System.out.println("\nSettembre\n");
		System.out.println(m.trovaSequenza(9));
		System.out.println("\nOttobre\n");
		System.out.println(m.trovaSequenza(10));
		System.out.println("\nNovembre\n");
		System.out.println(m.trovaSequenza(11));
		System.out.println("\nDicembre\n");
		System.out.println(m.trovaSequenza(12));
		
		

	}

}
