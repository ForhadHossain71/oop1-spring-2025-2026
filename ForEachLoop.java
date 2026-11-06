public class ForEachLoop  {
    public static void main(String[] args) {
        int[] number = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        for (int i = 0; i < number.length; i++) {
            System.out.println(i);
        }
        for(int numbers : number){
            System.out.println(numbers);
        }
        for(int j = number.length; j > 0; j--){
            System.out.println(j);

        }

        //start reverse from 9 to 1
        /*
            for(int j = number.length - 1; j > 0; j--){
            System.out.println(j);

        }
        */
    }
}
