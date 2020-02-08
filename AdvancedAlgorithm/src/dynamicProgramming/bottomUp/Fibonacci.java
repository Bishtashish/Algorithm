package dynamicProgramming.bottomUp;

public class Fibonacci {
	private int[] fib;
	
Fibonacci(int len){
	if(len>2)
		fib = new int[len];
		fib[0]=0;
		fib[1]=1;
	
	F();
	print();
	
}

private void F(){
	if(fib.length==1||fib.length==2)
		fib[fib.length-1]=fib.length-1;

//	Tabulation Method
	for(int i=2;i<fib.length;i++)
		fib[i]=fib[i-1]+fib[i-2];
	
}
private void print(){
	for(int i: fib)
		System.out.print("\t "+i);
}


public static void main (String args[]){
	Fibonacci f = new Fibonacci(10);
	
}

}
