
import java.util.Scanner;

public class WhileLoop {
    public static void main(String[] args) {
        int i = 1;
        while (i <= 10) {
            System.out.println(i);
            i++;
        }
        Scanner scan = new Scanner(System.in);
        String input = "";
        
        while(!input.equals("exit")){
            System.out.print("Type 'exit': ");
            input = scan.nextLine().trim().toLowerCase();

        }
        scan.close();
    }    
}
