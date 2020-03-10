package mestint;

import java.util.ArrayList;
import java.util.List;

public class Szélességi {

	public static void main(String[] args) {
		search();
	}
	
	public static void search() {
		// DATABASE
		
		class Node {
			State state;
			Node parent;
			Operator creator;
			boolean open; //státusz (nyílt vagy zárt)
			int level; //mélység 
			
			public Node(State state, Node parent, Operator creator) {
				this.state = state;
				this.parent = parent;
				this.creator = creator;
				this.open = true;
				this.level = parent == null ? 0 : parent.level+1;
                                //mélység rekurzív definíciója 
                                //(ha nincs szülő akkor a mélység 0, hiszen ekkor a startcsúcsról van szó
                                //egyébként a mélység = szülő mélysége + 1 
			}
			
		}
		//csúcsok listája 
		List<Node> nodes = new ArrayList<>(); //üres
		
		// CONTROLLER 
		nodes.add( new Node(State.start(), null, null)); //kezdőadatbázis 
                //a startcsúcsot nyíltként beleteszem a csúcsok listájába
		
		while (true) {
			// SELECTION
			Node select = null;
                        //kiválasztás lépése: kiválasztja a nyílt csúcsok közül a legkisebb mélységűt (min)
			for( Node node: nodes )
				if ( node.open && ( select == null || select.level < node.level ) )
					select = node;
			
                        //ha nincs nyílt csúcs akkor vége a keresésnek
			if ( select == null ) {
				System.out.println("The problem is not solvable!");
				break;
			}
			//ha az adott csúcs terminális csúcs akkor vége, és előállítjuk a megoldást.
                        //egy operátorsorozatot adunk vissza
			if ( select.state.isGoal() ) {
				System.out.println("Solution found! Goal: "+ select.state);
				
				List<Operator> solution = new ArrayList<>();
				for (Node node = select; node.parent != null; node = node.parent )
					solution.add(0, node.creator);				
				break;
			}
			
                        //ha nem terminális a csúcs akkor kiterjesztem
			// EXPAND:
                        //alkalmazom az összes alkalmazható operátort. 
			for ( Operator op: Operator.OPERATORS ) 
				if ( op.isApplicable(select.state)) {
					State newState = op.apply(select.state); //mindig előáll egy új állapot
					
                                        //megnézi, hogy a csúcsok között szerepelt-e már ilyen állapotú csúcs
					Node similar = null;
                                        //teljes keresés
					for (Node node : nodes)
						if ( node.state.equals(newState) ) {
							similar = node;
							break;
						}
					//ha ez az objektum null azaz még nem volt ilyen állapotú csúcs, akkor
                                        //nyíltként felfűzöm a csúcsok listájába. 
					if ( similar == null ) {
						nodes.add( new Node(newState, select, op));
					} else {
					}
				}
			select.open = false; //a kiterjesztett csúcsot pedig zárttá teszem.
		}
		
		
	}
	
}
