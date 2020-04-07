package encryptdecrypt;

import java.io.*;
import java.util.*;


public class Main {
    public static boolean isEncrypt = true;
    public static boolean fromFile = false;
    public static boolean isUnicode = false;
    public static String DATA = "";
    public static String FILE_IN = "";
    public static String FILE_OUT = "";
    public static String fileText = "";
    public static int KEY = 0;
    static String line = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ! ";

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                if(args[i + 1].equals("dec")) isEncrypt = false;
            }
            if (args[i].equals("-key")) {
                KEY = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-data")) {
                DATA = args[i + 1];
            }
            if (args[i].equals("-in")) {
                FILE_IN = args[i + 1];
                fromFile = true;
                System.out.println("Name file in is "+ args[i + 1]);
            }
            if (args[i].equals("-out")) {
                FILE_OUT = args[i + 1];
                System.out.println("Name file out is "+ args[i + 1]);
            }
            if(args[i].equals("-alg")) {
                if(args[i + 1].equals("unicode")) isUnicode = true;
            }
        }
        if(isEncrypt)
            if(!fromFile) System.out.println(encrypt(DATA, KEY));
            else encryptFile(FILE_IN);
        else
            if(!fromFile) System.out.println(decrypt(DATA, KEY));
            else encryptFile(FILE_IN);
        System.out.println("isEncrypt is " + isEncrypt);
        System.out.println("fromFile is " + fromFile);
    }
    public static void encryptFile(String file) throws IOException {
        try {
            final DataInputStream dis = new DataInputStream(new FileInputStream(FILE_IN));
            final byte[] bytes = new byte[dis.available()];
            dis.readFully(bytes);
            final String fileContents = new String(bytes, 0, bytes.length);
            System.out.println("Coдepжиmoe фaйлa:");
            System.out.println(fileContents);

            System.out.println("Starting encrypt");
            OutputStream file_out = new FileOutputStream(FILE_OUT);
            System.out.println(FILE_OUT + " has been created");
            System.out.println(FILE_IN + " has been opened");
            dis.close();
            System.out.println();
            System.out.println(FILE_IN + " has been closed");
            if(isEncrypt) fileText  = encrypt(fileContents,KEY);
            else fileText  = decrypt(fileContents,KEY);
            System.out.println(fileText + " has been encrypted");
            file_out.write(fileText.getBytes());
            System.out.println(fileText + " has been written");
            file_out.close();
            System.out.println(FILE_OUT + " has been closed");
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }
    public static String encrypt(String data, int key) {
        char[] ch = data.toCharArray();
        if(isUnicode) {
            int[] a = new int[ch.length];
            for (int i = 0; i < ch.length; i++) {
                a[i] = (int) ch[i];
                a[i] += key;
            }
            for (int i = 0; i < ch.length; i++) {
                ch[i] = (char) a[i];
            }
            return String.valueOf(ch);
        }
        else {
            char[] ch1 = new char[ch.length];
            int[] a = new int[ch.length];
            for (int i = 0; i < ch.length; i++) {
                a[i] = line.indexOf(ch[i]);
            }
            for(int i = 0; i < a.length; i++) {
                if(a[i] > 25 && a[i] < 53) {
                    for (int j = 0; j < key; j++) {
                        if (a[i] == 52) {
                            a[i] = 26;
                        }
                        a[i]++;
                    }
                }
                else {
                    if(a[i] != 53) {
                        for (int j = 0; j < key; j++) {
                            if (a[i] != 0) {
                                if (a[i] == 27) {
                                    a[i] = 0;
                                    a[i]++;
                                }
                                a[i]++;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < a.length; i++) {
                ch1[i] = line.toCharArray()[a[i]];
                System.out.print(line.toCharArray()[a[i]]);
            }
            return String.valueOf(ch1);
        }
    }
    public static String decrypt(String data, int key) {
        char[] ch = data.toCharArray();
        if(isUnicode) {
            int[] a = new int[ch.length];
            for (int i = 0; i < ch.length; i++) {
                a[i] = (int) ch[i];
                a[i] -= key;
            }
            for (int i = 0; i < ch.length; i++) {
                ch[i] = (char) a[i];
            }
            return String.valueOf(ch);
        }
        else {
            char[] ch1 = new char[ch.length];
            int[] a = new int[ch.length];
            for (int i = 0; i < ch.length; i++) {
                a[i] = line.indexOf(ch[i]);
            }
            for (int i = 0; i < a.length; i++) {
                if (a[i] < 53 && a[i] > 25) {
                    for (int j = 0; j < key; j++) {
                        if (a[i] == 26) {
                            a[i] = 52;
                        }
                        a[i]--;
                    }
                } else {
                    if (a[i] != 53) {
                        for (int j = 0; j < key; j++) {
                            if (a[i] != 0) {
                                if (a[i] == 1) {
                                    a[i] = 28;
                                    a[i]--;
                                }
                                /*else if(a[i] == 0) {
                                    a[i] = 27;
                                    a[i] -= 2;
                                }*/
                                a[i]--;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < a.length; i++) {
                System.out.print(line.toCharArray()[a[i]]);
                ch1[i] = line.toCharArray()[a[i]];
            }
            return String.valueOf(ch1);
        }
    }
}