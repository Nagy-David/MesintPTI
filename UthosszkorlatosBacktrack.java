package korso;

import java.util.ArrayList;
import java.util.List;

public class UthosszkorlatosBacktrack {

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
            int length;

            public Node(State state, Node parent, Operator creator) {
                this.state = state;
                this.parent = parent;
                this.creator = creator;
                untried = new ArrayList<>(); //üres lista
                for (Operator o : Operator.OPERATORS) //az untried lista tartalmazza az alkalmazható operátorokat
                {
                    if (o.isApplicable(state)) {
                        untried.add(o);
                    }
                }
                this.length = parent == null?0:parent.length+1; //mélység kiszámítása
                
            }
        }
        Node current;

        // CONTROLLER
        current = new Node(State.start(), null, null); //kezdőadatbázis inicializálása (2-4 sor)

        //korlát megadása (itt 4 mélység megfelelő, mert van megoldás, melynek 3 a mélysége)
        final int limit = 4;
        
        while (true) {

            System.out.println("Aktuális: "+current.state);
            
            if (current.state.isGoal()) {
                System.out.println(current.state);
                break;
            }

            //ha van még alkalmazható és ki nem próbált operátor és a mélység < korlát akkor kiválasztom
            //a "legbaloldalibbat"
            if (!current.untried.isEmpty() && current.length < limit ) {
                Operator o = current.untried.remove(0); //a remove(0) visszaadja a 0. elemét és kitörli a listából
                State newState = o.apply(current.state); //előállítja az új állapotot (17.sor)
                current = new Node(newState, current, o); // (18-21. sor)
            } else { //ha nincs alkalmazható operátor (az O' HALMAZ üres)

                //visszalépés művelete. Ha a szülő nem null (azaz nem a startcsúcsban vagyok) akkor visszalép.
                if (current.parent != null) {
                    current = current.parent;
                } else { //ha a startcsúcsból vissza kellene lépni akkor az azt jelenti, hogy már bejárta az
                         //egész gráfot és nem talált megoldást. 
                    System.out.println("Not solvable.");
                    break;
                }
            }

        }

        if (current.state.isGoal()) {
            //megoldás előállítása. A megoldás egy operátorsorozat lesz 
            ArrayList<Operator> solution = new ArrayList<>(); //üres lista
            //a terminál csúcstól a startcsúcsig halad visszafelé.
            //a listába belepakolja az adott csúcs creator nevű operátorát. 
            //fontos: mindig a lista elejére szúrjuk be az operátort! (visszafelé haladunk!)
            while(current.parent!=null){
                solution.add(0,current.creator);
                current = current.parent;
            }
            //kiírja a megtalált megoldást.
            for (Operator o : solution) {
                System.out.println(o);
            }
        }
    }

}
