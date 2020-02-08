package dynamicProgramming.topDown;

import java.util.Arrays;

public class Fibonacci {
	private int fib[];

	Fibonacci(int len) {
		fib = new int[len];
		Arrays.fill(fib, -1);

		F(len-1);
		print();
	}

	private int F(int len) {
		if (fib[len] == -1) {
			if (len <= 1)
				 return fib[len] = len;			
			fib[len] = F(len - 1) + F(len - 2);
		}
		return fib[len];

		// Memoization
	}

	private void print() {
		for (int i : fib)
			System.out.print("\t " + i);
	}

	public static void main(String args[]) {
		Fibonacci f = new Fibonacci(10);

	}
}
