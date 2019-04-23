/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korso;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hallgato
 */
public class SzélességiMasik {

    static class Node {

        State state;
        Node parent;
        Operator creator;

        public Node(State state, Node parent, Operator creator) {
            this.state = state;
            this.parent = parent;
            this.creator = creator;
        }

    }
    
    public static List<Operator> megoldas(Node aktualis){
        System.out.println("Megoldás: "+aktualis.state);
        List<Operator> m = new LinkedList<>(); //operátorok listája (megoldás)
        while(aktualis.parent!=null){ //végigmegyek az aktuálistól a startcsúcsig
            m.add(0,aktualis.creator); //beleteszem az aktuális operátort a lista elejére
            aktualis = aktualis.parent; //léptetés
        }
        return m;
    }
    
    //megnézi, hogy az adott állapotú csúcs benne van-e már a csucsok listában.
    //ha igen akkor visszaadja az megtalált csúcsot
    //ha nem akkor null-t ad vissza
    public static Node voltMar(List<Node> csucsok, Node aktualis){
        for (Node cs : csucsok) {
            if(cs.state.equals(aktualis.state)){
                return cs;
            }
        }
        return null;
    }
    
    
    public static List<Operator> keres(){
        
        //két listát hozunk létre: nyíltak és zártak
        List<Node> nyiltak = new LinkedList<>();
        List<Node> zartak = new LinkedList<>();
        
        nyiltak.add( new Node(State.start(), null, null)  ); //kezdőadatbázis. Nyíltként fűzöm fel a startcsúcsot.
        
        while(true){
            
            //van nyílt?
            if(nyiltak.isEmpty()){
                return null;
            }            
            //kiválaszt (sor)
            Node aktualis = nyiltak.remove(0);
            
            //célteszt
            if(aktualis.state.isGoal()){
                return megoldas(aktualis);
            }
            
            //kiterjeszt
            for (Operator o: Operator.OPERATORS) {
                if(o.isApplicable(aktualis.state)){
                    State uj = o.apply(aktualis.state);
                    
                    if(voltMar(nyiltak,aktualis)==null && voltMar(zartak,aktualis)==null){
                        nyiltak.add(new Node(uj,aktualis,o)); //ha még nem volt ilyen állapotú csúcs akkor nyíltként hozzáfűzöm                      
                    }
                    
                }
            }
            zartak.add(aktualis); //hozzáteszem a kiterjesztett csúcsot a zárt csúcsokhoz
            
        }
        
    }
    
    public static void main(String[] args) {
        
        List<Operator> m = keres();
        for (Operator o : m) {
            System.out.println(o);
        }
        
    }
    

}
