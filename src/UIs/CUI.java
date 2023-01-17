package UIs;

import Expression.Expression;
import FileReaders.*;
import FileWriters.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CUI {

     private static CUI cui;
     FileWriters fileWriters;

     public static CUI getInstance() {
         if (cui == null) {
            cui = new CUI();
         }
         return cui;
     }

     private CUI() {}

     public void createCUI() {
         try {
             char c = 'y';
             char cin;
             char cout;
             ArrayList<String> expressions = new ArrayList<>();
             ArrayList<Double> result = new ArrayList<>();
             Scanner scanner = new Scanner(System.in);

             while (c != 'n') {

                 System.out.println("""
                                          
                         Welcome to my super calculator
                         Please, choose how to load an expression:
                         1. Write yourself
                         2. TXT file
                         3. XML file
                         4. JSON file
                         5. ZIP file
                         6. ENC file
                         """
                 );
                 cin = scanner.nextLine().charAt(0);
                 while (cin != '1' && cin != '2' && cin != '3' && cin != '4' && cin != '5' && cin != '6') {
                     System.out.println("""
                             
                             Wrong command, please, try again:
                             """);
                     cin = scanner.nextLine().charAt(0);
                 }

                 System.out.println("""
                                          
                         Please, choose where to display the result:
                         1. Console
                         2. TXT file
                         3. XML file
                         4. JSON file
                         """
                 );
                 cout = scanner.nextLine().charAt(0);
                 while (cout != '1' && cout != '2' && cout != '3' && cout != '4') {
                     System.out.println("""
                             
                             Wrong command, please, try again:
                             """);
                     cout = scanner.nextLine().charAt(0);
                 }

                 switch (cin) {
                     case '1' -> {
                         System.out.print("\nWrite your expression: ");
                         String string = scanner.nextLine();
                         expressions.add(string);
                     }
                     case '2' -> expressions = FileReaders.readFile("inputFiles\\input.txt");
                     case '3' -> expressions = FileReaders.readFile("inputFiles\\input.xml");
                     case '4' -> expressions = FileReaders.readFile("inputFiles\\input.json");
                     case '5' -> expressions = FileReaders.readFile("inputFiles\\input.zip");
                     case '6' -> expressions = FileReaders.readFile("inputFiles\\input.txt.enc");
                 }
                 for (String s : expressions) {
                     result.add(Expression.calculate(s));
                 }
                 if (cout == '1') {
                     for (var s : result) {
                         System.out.println(s);
                     }
                 } else {
                     String filename = null;
                     switch (cout) {
                         case '2' -> {
                             filename = "outputFiles\\output.txt";
                             fileWriters = new FileWritersTXT(filename);
                         }
                         case '3' -> {
                             filename = "outputFiles\\output.xml";
                             fileWriters = new FileWritersXML(filename);
                         }
                         case '4' -> {
                             filename = "outputFiles\\output.json";
                             fileWriters = new FileWritersJSON(filename);
                         }
                     }
                     for (Double s : result) {
                         fileWriters.writeFile(s.toString() + '\n');
                     }
                     fileWriters.close();
                     char archiveOrEncrypt= '0';
                     while (archiveOrEncrypt != '3') {
                         System.out.println("""
                                                              
                                 Do you want to do something with your output file?
                                 1. Archive
                                 2. Encrypt
                                 3. Nothing
                                 """
                         );
                         archiveOrEncrypt = scanner.nextLine().charAt(0);
                         while (archiveOrEncrypt != '1' && archiveOrEncrypt != '2' && archiveOrEncrypt != '3') {
                             System.out.println("""
                                                                  
                                     Wrong command, please, try again:
                                     """
                             );
                             archiveOrEncrypt = scanner.nextLine().charAt(0);
                         }
                         switch (archiveOrEncrypt) {
                             case '1' -> {
                                 fileWriters = new FileWritersZIP(filename + ".zip");
                                 fileWriters.writeFile(filename);
                                 filename += ".zip";
                                 fileWriters.close();
                             }
                             case '2' -> {
                                 fileWriters = new FileWritersEnc(filename + ".enc");
                                 fileWriters.writeFile(filename);
                                 filename += ".enc";
                             }
                         }
                     }
                 }
                 System.out.println("""

                         Done
                         Do you want to continue?
                         y - yes
                         n - no
                         """
                 );
                 c = scanner.nextLine().charAt(0);
                 while (c != 'y' && c != 'n') {
                     System.out.println("""
                             
                             Wrong command, please, try again:
                             """
                     );
                     c = scanner.nextLine().charAt(0);
                 }
             }
         }
         catch (Exception e) {
             throw new RuntimeException(e);
         }
     }
}
