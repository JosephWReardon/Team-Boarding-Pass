import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class MainTest {

    ArrayList<String> words;

    @BeforeEach
    void setup(){
        words = new ArrayList<>();
    }

    @Test
    void normalizeTabLengthTest() {

        words.add("test");
        words.add("testtestte");

        words = Main.normalizeTabLength(words);

        assertEquals("test\t",words.get(0),"Normalize Tab Length did not add correct amount of tabs");

    }

    @Test
    void compileColumnsTest(){

        ArrayList<String> column1 = new ArrayList<>();
        ArrayList<String> column2 = new ArrayList<>();

        column1.add("test");
        column1.add("test");
        column1.add("test");

        column2.add("test");
        column2.add("test");

        ArrayList<ArrayList<String>> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);

        System.out.println(Main.compileColumns(columns));

        assertEquals("test\t\ttest\ntest\t\ttest\ntest",Main.compileColumns(columns),"Compile Columns did not properly format return string");

    }

    @Test
    void formatFileStringTest(){

        Scanner s;
        String localDir = System.getProperty("user.dir");
        File test = new File(localDir + "\\src\\test\\resource\\TestText");
        try {
            s = new Scanner(test);

            assertEquals("one\t\t\tsix\t\ntwo\t\t\tseven\nthree\t\teight\nfour\t\tnine\nfive\t\tten\t",Main.formatFileString(s,2),"Format sting did to properly format the contents of the file");
            s.close();
            s = new Scanner(test);
            assertEquals("one\t\t\tfive\t\tnine\ntwo\t\t\tsix\t\t\tten\t\nthree\t\tseven\nfour\t\teight",Main.formatFileString(s,3),"Format sting did to properly format the contents of the file");
            s.close();
            s = new Scanner(test);
            assertEquals("one\t\ttwo\t\tthree\t\tfour\t\tfive\t\tsix\t\tseven\t\teight\t\tnine\t\tten",Main.formatFileString(s,20),"Format sting did to properly format the contents of the file");


            s.close();
        }catch(Exception e){
            System.out.println("File not found");
        }

    }

    @AfterEach
    void teardown(){

    }
}