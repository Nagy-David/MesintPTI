package korso;

import java.util.ArrayList;
import java.util.Random;

public class Hegymaszo {
	
	public static void main(String args[]) {
		for (int i=1; i<=100; i++)
			search();
	} 
	
	public static void search() {
		// DATABASE
		State current; //a kereső minden lépésben egy állapotot tárol
		// CONTROLLER
		current = State.start(); //inicializálja a kezdő adatbázist. (kezdő állapot)
		int counter = 0; 
		while (true) {
                        //célfeltétel vizsgálata
			if ( current.isGoal() ) {
				System.out.println(current);
				break;
			}	
                        //O' halmaz reprezentálása. Itt egy listaként reprezentáljuk
			ArrayList<Operator> applicables = new ArrayList<>(); //üres lista (operátorok listája)
                        //végigmegy az összes operátoron, majd ha az adott 
                        //operátor alkalmazható, akkor hozzáadja a listához
                        for (Operator o: Operator.OPERATORS) {
                                if(o.isApplicable(current)){
                                    applicables.add(o);
                                }				
			}
                        //ha a lista üres, akkor vége, mert nincs több alk. op. 
			if ( applicables.isEmpty() ) {
				System.out.println(":-("); 
				break;
			}	
                        //ha van alk. op 
                        //akkor kiválaszt egyet véletlenszerűen. 
                        final State tmp = current;
                        
                        Operator min = applicables.stream().
                    min((a, b) -> (int)a.apply(tmp).heurisztika()-(int)b.apply(tmp).heurisztika()  ).get();
                                            
			current = min.apply(current); //alkalmazza, és az új állapottal felülírjuk a régit.
			counter++;
		}
		System.out.println(counter);
	}

}
