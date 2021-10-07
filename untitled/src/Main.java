import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        Dictionary dict = new Dictionary("dico.txt");
        long nanoTime = System.nanoTime();
        ArrayList<String> corr = new ArrayList<>();
        try {
            File file = new File("fautes.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                corr.add(dict.correctOrthography(line).toString());
                System.out.println((System.nanoTime() - nanoTime)/1000000);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(corr.toString());
        System.out.println("finish");
    }


}
