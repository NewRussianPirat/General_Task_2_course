package UIs;

import Expression.Expression;
import FileReaders.*;
import FileWriters.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Scanner;

public class CUI {

     private static CUI cui;
     Expression expression = new Expression();
     FileReaders fileReaders;
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
             char cinEncrypted = '0';
             char coutArchEncr = '0';
             ArrayList<String> expressions = new ArrayList<>();
             ArrayList<Double> result = new ArrayList<>();
             SecretKeySpec key = null;
             SecretKey outputKey = null;
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
                         """
                 );
                 cin = scanner.nextLine().charAt(0);
                 while (cin != '1' && cin != '2' && cin != '3' && cin != '4' && cin != '5') {
                     System.out.println("""
                             Wrong command, please, try again:
                             """);
                     cin = scanner.nextLine().charAt(0);
                 }

                 if (cin == '2' || cin == '3' || cin == '4' || cin == '5') {
                     System.out.println("""
                                              
                             Is file encrypted?
                             1. Yes
                             2. No
                             """
                     );
                     cinEncrypted = scanner.nextLine().charAt(0);
                     while (cinEncrypted != '1' && cinEncrypted != '2') {
                         System.out.println("""
                                 Wrong command, please, try again:
                                 """);
                         cinEncrypted = scanner.nextLine().charAt(0);
                     }
                     if (cinEncrypted == '1') {
                         System.out.println("""
                                                              
                                 Please, write the key:
                                 """
                         );
                         String string = scanner.nextLine();
                         key = new SecretKeySpec(string.getBytes(), "AES");
                     }
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

                 if (cout == '2' || cout == '3' || cout == '4') {
                     System.out.println("""
                             Do you want to archive or encrypt file?
                             1. Archive
                             2. Encrypt
                             3. Both
                             4. No
                             """);
                     coutArchEncr = scanner.nextLine().charAt(0);
                     while (coutArchEncr != '1' && coutArchEncr != '2' && coutArchEncr != '3' && coutArchEncr != '4') {
                         System.out.println("""
                                 Wrong command, please, try again:
                                 """);
                         coutArchEncr = scanner.nextLine().charAt(0);
                     }
                 }

                 switch (cin) {
                     case '1' -> {
                         System.out.print("\nWrite your expression: ");
                         String string = scanner.nextLine();
                         expressions.add(string);
                     }
                     case '2' -> {
                         fileReaders = new FileReadersTXT();
                         if (cinEncrypted == '1') {
                             fileReaders.setEncrypted(true);
                             fileReaders.setKey(key);
                         }
                         expressions = fileReaders.readFile("inputFiles/input.txt");
                     }
                     case '3' -> {
                         fileReaders = new FileReadersXML();
                         if (cinEncrypted == '1') {
                             fileReaders.setEncrypted(true);
                             fileReaders.setKey(key);
                         }
                         expressions = fileReaders.readFile("inputFiles/input.xml");
                     }
                     case '4' -> {
                         fileReaders = new FileReadersJSON();
                         if (cinEncrypted == '1') {
                             fileReaders.setEncrypted(true);
                             fileReaders.setKey(key);
                         }
                         expressions = fileReaders.readFile("inputFiles/input.json");
                     }
                     case '5' -> {
                         fileReaders = new FileReadersZIP();
                         if (cinEncrypted == '1') {
                             fileReaders.setEncrypted(true);
                             fileReaders.setKey(key);
                         }
                         expressions = fileReaders.readFile("inputFiles/input.zip");
                     }
                 }
                 for (var s : expressions) {
                     result.add(expression.calculate(s));
                 }
                 if (cout == '1') {
                     for (var s : result) {
                         System.out.println(s);
                     }
                 } else {
                     String filename = null;
                     switch (cout) {
                         case '2' -> {
                             filename = "outputFiles/output.txt";
                             fileWriters = new FileWritersTXT(filename);
                         }
                         case '3' -> {
                             filename = "outputFiles/output.xml";
                             fileWriters = new FileWritersXML(filename);
                         }
                         case '4' -> {
                             filename = "outputFiles/output.json";
                             fileWriters = new FileWritersJSON(filename);
                         }
                     }
                     for (var s : result) {
                         fileWriters.writeFile(s.toString() + '\n');
                     }
                     fileWriters.close();
                     switch (coutArchEncr) {
                         case '1' -> {
                             fileWriters = new FileWritersZIP("outputFiles/output.zip");
                             fileWriters.writeFile(filename);
                         }
                         case '2' -> {
                             Encryption encryption = new Encryption();
                             outputKey = encryption.encrypt(fileWriters);
                         }
                         case '3' -> {
                             Encryption encryption = new Encryption();
                             outputKey = encryption.encrypt(fileWriters);
                             fileWriters = new FileWritersZIP("outputFiles/output.zip");
                             fileWriters.writeFile(filename);
                         }
                     }
                 }
                 if (cout != '1') {
                     fileWriters.close();
                 }
                 System.out.println("\nDone");
                 if (outputKey != null) {
                     System.out.println("\nKey of encrypted file: " + outputKey);
                 }
                 System.out.println("""
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
