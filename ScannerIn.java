import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerIn {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);

        try {
            byte age = Scan.nextByte();
            System.out.println("Age: "+age);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number for age.");
            Scan.nextLine();
        }

        String Name = Scan.nextLine().trim().toLowerCase();
        System.out.println("Name: "+ Name);

        Scan.close();
    }
    
} 
