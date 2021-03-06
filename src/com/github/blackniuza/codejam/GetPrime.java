package com.github.blackniuza.codejam;

import java.util.BitSet;
import java.util.Scanner;

/*
 * @author blackniuza
 */

public class GetPrime {
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		while(true){
			String input = scanner.nextLine();
			if(input.equals("Bye")) break;
			String[] numbers = input.split(" ");
			int n = Integer.valueOf(numbers[0]);
			int c = Integer.valueOf(numbers[1]);
			calcAndPrint(n, c);
		}
	}
	
	static void calcAndPrint(int range, int count){
//		long time1 = System.currentTimeMillis();
		Primes ps = new Primes(range);
//		long time2 = System.currentTimeMillis();
//		System.out.println("Time use: "+(time2-time1));
		ps.print(count);
	}
	
	static class Primes{
		
		BitSet isNotPrime;
		int range;
		int countPrime;
		int countNotPrime;
		
		Primes(int range){
			this.range = range;
			isNotPrime = new BitSet(range+1);
			countPrime = 0;
			countNotPrime = 0;
			calcPrimes();	
		}
		
		void calcPrimes(){
			setNotPrime(0);
			setNotPrime(1);
			countNotPrime++;
			boolean flag = false;
			for(int i=2;i<=range;i++){
				if(isPrime(i)) countPrime++;
				for(int cnt=0,j=nextPrime(0);cnt<countPrime;cnt++,j=nextPrime(j)){
					int tmp = j*i;
					if(tmp>range) {
						if(cnt==0) flag=true;
						break;
					}
					setNotPrime(tmp);
					countNotPrime++;
					if(i%j==0) break;
				}
				if(flag) break;
			}
//			System.out.println("cp: "+countPrime);
//			System.out.println("cnp: "+countNotPrime);
			countPrime = range-countNotPrime;
//			System.out.println("new cp: "+countPrime);
		}
		boolean isPrime(int index){
			return !isNotPrime.get(index);
		}
		
		int nextPrime(int index){
			return isNotPrime.nextClearBit(index+1);
		}
		
		void setNotPrime(int index){
			isNotPrime.set(index);
		}
		
		void print(int count){
			int size = countPrime;
			int mid = size/2;
			int begin = Math.max(0, mid-count);
			int len = 2*count + (size&1);
			len = Math.min(len, size);
			
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%d %d: ", range, count));
			
			int p = nextPrime(0);
			for(int i=0;i<begin;i++){
				p=nextPrime(p);
			}
			
			for(int i=begin;i<begin+len;i++){
				sb.append(p).append(" ");
				p=nextPrime(p);
			}
			System.out.println(sb.toString());
		}		
	}
}
