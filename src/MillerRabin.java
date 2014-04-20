/* Kevin Lang
 * Evan Dorundo
 */

import java.math.*;
import java.util.*;

public class MillerRabin
{	
	private static final BigInteger TWO = new BigInteger("2");
	
	//returns true if (probably) prime
	public static boolean MillerRabin(BigInteger n, int k)
	{
		if(n.equals(TWO) || n.equals(BigInteger.valueOf(3)))
			return true;
		
		//make sure not even input
		if(n.mod(TWO).compareTo(BigInteger.ZERO) == 0)
			return false;
		
		//factor n-1 -> d * 2^s
		BigInteger d = n.subtract(BigInteger.ONE);
		int s = 0;
		while(d.mod(TWO).equals(BigInteger.ZERO))
		{
			d = d.divide(TWO);
			s++;
		}
		
		Random r = new Random();
		for(int i = 0; i < k; i++)
		{
			if(!witnessLoop(n, d, s, r))
				return false;
		}
		
		//probably prime
		return true;
	}
	
	//returns true if it should continue looping, otherwise false if composite
	private static boolean witnessLoop(BigInteger n, BigInteger d, int s, Random r)
	{
		//pick random a in range [2, n-2]
		BigInteger a;
		do{
			a = new BigInteger(n.bitLength(), r);
		}while(a.compareTo(TWO) < 0 || a.compareTo(n.subtract(TWO)) > 0);
	
		BigInteger x = a.modPow(d, n);
		
		//if true, do next witness loop
		if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0)
			return true;
		
		for(int i = 0; i < s-1; i++)
		{
			x = x.multiply(x).mod(n);
			
			if(x.compareTo(BigInteger.ONE) == 0)
				return false;
			
			if(x.compareTo(n.subtract(BigInteger.ONE)) == 0)
				return true;
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		while(true)
		{
			BigInteger prime = new BigInteger(in.next());
			System.out.println(MillerRabin(prime, 5));
		}
	}
}
