package chucknorris;


import javax.print.DocFlavor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            switch (command) {
                case "encode" -> encoder();
                case "decode" -> decoder();
                case "exit" -> {
                    System.out.println("Bye");
                    System.exit(0);
                }
                default -> System.out.println("There is no '" + command + "' operation");
            }
        }
    }

    private static String getBytes(String string) {
        StringBuilder bytes = new StringBuilder("");
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            bytes.append(String.format("%7s", Integer.toBinaryString(character)).replaceAll(" ", "0"));

        }
        return bytes.toString();
    }

    private static char charFromStringWithBytes(String bytes) {
        char result = 0;
        int digit = 1;
        for (int i = 0; i < 7; i++) {
            if (bytes.charAt(6 - i) == '1') {
                result += digit;
            }
            digit *= 2;
        }
        return result;
    }

    private static void decoder() {
        System.out.println("Input encoded string:");
        String newInput = new Scanner(System.in).nextLine();
        if (!newInput.replaceAll(" ", "").replaceAll("0", "").equals("")) {
            System.out.println("Encoded string is not valid.");
            return;
        }
        StringBuilder bites = new StringBuilder("");
        String[] input;
        do {
            input = newInput.split(" ", 3);
            if (input[0].equals("0")) {
                bites.append("1".repeat(input[1].length()));
            } else if (input[0].equals("00")){
                bites.append("0".repeat(input[1].length()));
            } else {
                System.out.println("Encoded string is not valid.");
                return;
            }
            if (input.length > 2) {
                newInput = input[2];
            } else {
                break;
            }
        } while (input[2] != null && !input[2].equals(""));
        if (bites.length() % 7 != 0) {
            System.out.println("Encoded string is not valid.");
            return;
        }
        int size = bites.length() / 7;
        String[] bytes = new String[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = bites.substring(i * 7, i * 7 + 7);
        }
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = charFromStringWithBytes(bytes[i]);
        }
        System.out.println("Decoded string:");
        for (char c : chars) {
            System.out.print(c);
        }
        System.out.println();

    }

    private static void encoder() {
        System.out.println("Input string:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("Encoded string:");
        String bites = getBytes(input);
        int size = bites.length();
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < size; ) {
            if (bites.charAt(i) == '0') {
                output.append("00 ");
                while (i < size && bites.charAt(i) == '0') {
                    i++;
                    output.append("0");
                }
                output.append(" ");
            } else {
                output.append("0 ");
                while (i < size && bites.charAt(i) == '1') {
                    i++;
                    output.append("0");
                }
                output.append(" ");
            }
        }
        System.out.println(output.toString().trim());
    }
}
