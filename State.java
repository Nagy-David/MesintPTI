package mestint;

import java.util.Arrays;

public class State {
	
	int a[] = new int[4];
	
        //kezdőállapot 
	public static State start() {
		State state = new State(); //"üres" állapot
		state.a[1] = 0; //kezdetben az 5l-es korsó üres 
		state.a[2] = 3; //a 3l-es tele van
		state.a[3] = 2; //és a 2l-es is tele van
		return state;
	}
	
        //célfeltétel
	public boolean isGoal() {
		return a[1] == 4; //egy állapot célállapot, ha az első korsóban 4l van.
                /*
                if(a[1] == 4){
                    return true;
                }
                else{
                    return false;
                }
                */     
	}	          
        //két állapotot mikor tekintünk egyenlőnek
	@Override
	public boolean equals(Object obj) {
		if ( obj == null || ! (obj instanceof State))
			return false;

                //ha a korsók tartalma páronként egyenlő akkor a két állapotot egyenlőnek tekintjük
		State arg = (State)obj;
		return this.a[1] == arg.a[1] && this.a[2] == arg.a[2] && this.a[3] == arg.a[3];
	}	
    /*
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Arrays.equals(this.a, other.a)) {
            return false;
        }
        return true;      
    }
    */
	@Override
	public int hashCode() {
		return a[1]*100 + a[2]*10 +a[3];
	}
	
	@Override
	public String toString() {
		return new StringBuffer()
			.append('(').append(a[1]).append(',').append(a[2]).append(',')
                        .append(a[3]).append(')').toString();
	}

        
        public double heurisztika(){
            return 1;
        }
	
}
