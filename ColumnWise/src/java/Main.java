import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner s = new Scanner(System.in);
        int columns;
        System.out.println("\nPlease provide the file path to the file you want displayed or type \"close\" to exit:\n");

        mainLoop:
        while(true){


            try{

                String filePath = s.next();

                //Break loop and close program if the user types "close"
                if(filePath.contentEquals("close")){
                    break;
                }else {

                    Scanner file = new Scanner(new File(filePath));

                    System.out.println("\nWhat is the maximum number of columns you want?\n");

                    //Read how many columns the file should have
                    while(true){

                        try{
                            columns = s.nextInt();

                            //Check to see if the user input is usable
                            if(columns <= 0){
                                System.out.println("\nPlease provide a number greater than zero:\n");
                            } else {
                                break;
                            }

                        }catch(Exception e){
                            if(e.getClass() == InputMismatchException.class){
                                System.out.println("\nPlease provide a number:\n");
                            } else{
                                System.out.println("\nAn error has occurred.");
                                break mainLoop;
                            }
                            s.next();
                        }
                    }

                    System.out.println(formatFileString(file,columns));

                    file.close();

                }

            }catch(Exception e){
                if(e.getClass() == FileNotFoundException.class){
                    System.out.println("\nFile was not found. Please check your file path.\n");
                } else{
                    System.out.println("\nFile was found but could not be accessed.\n");
                }
            }

            System.out.println("\nEnter another file path or type \"close\" to exit:\n");

        }

    }

    //Method that reads all the lines in a scanner and returns a string of them arranged column wise with at most the number of columns give
    public static String formatFileString(Scanner s, int columns){

        ArrayList<String> lines = new ArrayList<>();

        while(s.hasNextLine()){
            lines.add(s.nextLine());
        }

        lines.removeIf(n -> n.contentEquals(""));

        if(lines.size() < columns){
            columns = lines.size();
        }

        int rows = lines.size()/columns;
        if(lines.size()%columns > 0){ rows++;}

        ArrayList<String> column;
        ArrayList<ArrayList<String>> formattedArray = new ArrayList<>();
        int index;
        int base = 0;

        for(int x = 1; x <= columns; x++){

            column = new ArrayList<>();
            index = 0;

            while(base + index < lines.size() && index != rows){
                column.add(lines.get(base + index));

                index++;
            }

            base += rows;

            column = normalizeTabLength(column);

            formattedArray.add(column);

        }

        return compileColumns(formattedArray);

    }

    //Method that takes an array list of strings and adds tabs to each string, so they are all the same length when printed
    static ArrayList<String> normalizeTabLength(ArrayList<String> words){

        String longest = words.stream().reduce("",(n,m)->{
            if(n.length() > m.length()){
                return n;
            }else{
                return m;
            }
        });

        int tabs = (longest.length()/4 + 1);
        //if(longest.length()%4 != 0){ tabs++;}

        int finalTabs = tabs;
        words = new ArrayList<>( words.stream().map(n -> {

            int t = (n.length()/4) + 1;

            //if(n.length()%4 != 0 ){ t++;}

            for(int x = t; x < finalTabs; x++){
                n += "\t";
            }

            return n;
        }).toList());

        return words;
    }

    //Method that takes an array list of string array lists that represent columns of words and returns a string of the columns
    static String compileColumns(ArrayList<ArrayList<String>> columns){

        String fileText = "";

        for(int y = 0; y < columns.get(0).size(); y++){

            for(int x = 0; x < columns.size(); x++){

                if(x != 0){
                    if(!(y > columns.get(x).size() -1)){
                        fileText += "\t\t" + columns.get(x).get(y);
                    }
                }else{
                    fileText += columns.get(x).get(y);
                }

            }

            if(y != columns.get(0).size() - 1) {
                fileText += "\n";
            }
        }

        return fileText;

    }

}