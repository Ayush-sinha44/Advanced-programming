import java.util.*;

public class BookSearch{
    public static void main(String[] args){
        ArrayList<String> books=new ArrayList<>();
        books.add("Data Structures");
        books.add("Operating Systems");
        books.add("Java Programming");
        books.add("Computer Networks");
        books.add("Database Management Systems");

        Scanner sc=new Scanner(System.in);
        System.out.print("Enter word to search: ");
        String word=sc.nextLine().toLowerCase();

        System.out.println("Matching books:");
        for(String book:books){
            if(book.toLowerCase().contains(word)){
                System.out.println(book);
            }
        }
        sc.close();
    }
}
