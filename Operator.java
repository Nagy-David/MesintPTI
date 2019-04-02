package korso;

public class Operator {

	private final int i;
	private final int j;
	
	public Operator(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}
	
	public static final Operator[] OPERATORS = {
		new Operator(1, 2),
		new Operator(1, 3),
		new Operator(2, 1),
		new Operator(2, 3),
		new Operator(3, 1),
		new Operator(3, 2),
	};
	
	public static final int[] max = new int[4];
	
	static {
		max[1] = 5;
		max[2] = 3;
		max[3] = 2;
	}
	
        //alkalmazási előfeltétel
	public boolean isApplicable(State state) {
		return state.a[i] > 0 && state.a[j] < max[j]; 
	}
        //hatásdefiníció
	public State apply(State state) {
		State newState = new State();  
		int a[] = state.a;
		int b[] = newState.a;
		int m = Math.min(a[i], max[j]-a[j]);
		for (int k=1;k<=3;k++)
			if ( k == j )
				b[k] = a[k] + m;
			else if ( k == i )
				b[k] = a[k] - m;
			else
				b[k] = a[k];
		return newState;		
	}
	
        //i -> j
	@Override
	public String toString() {
		return new StringBuffer().append(i).append(" -> ").append(j).toString();
	}
	
	
}
