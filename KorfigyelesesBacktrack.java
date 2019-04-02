package korso;

import java.util.ArrayList;
import java.util.List;

public class KorfigyelesesBacktrack {
	
	public static void main(String args[]) {
			search();
	} 

	public static void search() {
		// DATABASE
		class Node {
			State state; //állapot
			Node parent; //szülőre mutató mutató
			Operator creator; //az az operátor mely a state nevű állapotot előállította
			List<Operator> untried; //a még ki nem próbált operátorok halmaza (itt lista)
			
			public Node(State state, Node parent, Operator creator) {
				this.state = state;
				this.parent = parent;
				this.creator = creator;
				untried = new ArrayList<>(); //üres lista
				for (Operator o : Operator.OPERATORS) //az untried lista tartalmazza az alkalmazható operátorokat
					if (o.isApplicable(state))
						untried.add(o);
			}
		}
		Node current;
		
		// CONTROLLER
		current = new Node(State.start(), null, null); //kezdőadatbázis inicializálása (2-4 sor)
		//final int limit = 4;
		//int length = 1;
		
		while (true) {
			//ha van még alkalmazható és ki nem próbált operátor akkor kiválasztom
                        //a "legbaloldalibbat"
			if ( ! current.untried.isEmpty() /* && length < limit */ ) {
				Operator o = current.untried.remove(0); //a remove(0) visszaadja a 0. elemét és kitörli a listából
				State newState = o.apply(current.state); //előállítja az új csúcsot (17-20 sor)
				
                                //körfigyelés.
                                //Megnézi, hogy volt-e már "state" állapotú csúcs valahol                   
				boolean found = false;
                                //akutális csúcstól visszafelé halad a startcsúcsig
                                //megnézi, hogy volt-e már olyan csúcs, mely egyenlőnek tekinthető az
                                //aktuális csúccsal. Két csúcs egyenlő ha az állapotuk egyenlő (equals)
				for (Node n = current; n != null; n=n.parent)
					if ( n.state.equals(newState))
						found = true;
				
                                //ha nem volt még ilyen állapotú csúcs, akkor hozzáadom az úthoz és megyek tovább.
				if ( ! found ) {
					current = new Node(newState, current, o);
					//length++;
					if (current.state.isGoal()) {
						System.out.println(current.state);
						break;
					}
				}
				
			} else {
				
				if ( current.parent != null ) {
					current = current.parent;
					//length--;
				} else {
					System.out.println("Not solvable.");
					break;
				}
			}
			
		}
		
		if (current.state.isGoal()) {
			ArrayList<Operator> solution = new ArrayList<>();
			for ( ; current.parent != null; current = current.parent  )
				solution.add(0,current.creator);
			for ( Operator o : solution )
				System.out.println(o);
		}
	}
	
	
}
