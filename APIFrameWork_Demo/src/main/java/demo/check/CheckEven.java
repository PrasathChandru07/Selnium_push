package demo.check;

import java.util.Scanner;

public class CheckEven {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = reader.nextInt();

        if(num % 2 == 0)
            System.out.println(num + " is even");
        else
            System.out.println(num + " is odd");

	}

}
