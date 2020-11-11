package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
//        System.out.println("The secret code is prepared: ****.\n" +
//                "\n" +
//                "Turn 1. Answer:\n" +
//                "1234\n" +
//                "Grade: 1 cow.\n" +
//                "\n" +
//                "Turn 2. Answer:\n" +
//                "5678\n" +
//                "Grade: 1 cow.\n" +
//                "Turn 3. Answer:\n" +
//                "9012\n" +
//                "Grade: 1 bull and 1 cow.\n" +
//                "\n" +
//                "Turn 4. Answer:\n" +
//                "9087\n" +
//                "Grade: 1 bull and 1 cow.\n" +
//                "\n" +
//                "Turn 5. Answer:\n" +
//                "1087\n" +
//                "Grade: 1 cow.\n" +
//                "\n" +
//                "Turn 6. Answer:\n" +
//                "9205\n" +
//                "Grade: 3 bulls.\n" +
//                "\n" +
//                "Turn 7. Answer:\n" +
//                "9305\n" +
//                "Grade: 4 bulls.\n" +
//                "Congrats! The secret code is 9305."
//        );
        int turn = 1;


        Scanner sc = new Scanner(System.in);


        System.out.println("Please, enter the secret code's length:");
        //while (true) {
           String codeLengthString = sc.nextLine();
           String regex = "\\D";
           Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(codeLengthString);
            if (m.find()) {
                System.out.println("Error: " + codeLengthString + " isn't a valid number");
                System.exit(0);
            }
           int codeLength = Integer.parseInt(codeLengthString);

            if (codeLength < 1 || codeLength > 36) {
                System.out.println("error");
                System.exit(1);
            }
           // }

        System.out.println("Input the number of possible symbols in the code:");
                int numberOfSymbols = sc.nextInt();
                if (numberOfSymbols > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    System.exit(0);
                }

                if (numberOfSymbols < codeLength) {
                    System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + numberOfSymbols + " unique symbols.");
                    System.exit(1);
                }

        String code = improvedCodeGenerator(codeLength, numberOfSymbols);
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < codeLength; i++) {
            System.out.print('*');
        }
        if (numberOfSymbols <= 10) {
            System.out.print(" (0-" + (numberOfSymbols - 1) + ").");
        }
        if (numberOfSymbols == 11) {
            System.out.print(" (0-" + numberOfSymbols + ", a).");
        }
        if (numberOfSymbols > 11) {
            System.out.print(" (0-" + numberOfSymbols + ", a-" + ((char)((numberOfSymbols - 10) + 96)) + ").");
        }
        System.out.println("Okay, let's start a game!");

        while (true) {
            System.out.println("Turn: " + turn);
            turn++;

            boolean guess = guessCheck(code);
            if (guess) {
                break;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");




    }

//    public static String generateCode() {
//
//        Scanner sc = new Scanner(System.in);
//        int codeLength;
//
//        System.out.println("Please, enter the secret code's length:");
//        while (true) {
//            codeLength = sc.nextInt();
//            if (codeLength > 10) {
//                System.out.println("Error");
//            } else if (codeLength <= 10) {
//                break;
//            }
//        }
//        long pseudoRandomNumber = System.nanoTime();
//        //System.out.println(pseudoRandomNumber);
//
//        StringBuilder sb = new StringBuilder(String.valueOf(pseudoRandomNumber));
//        String reversedNum = sb.reverse().toString();
//        String code = "";
//        for (int i = 0; i < pseudoRandomNumber; i++) {
//            if (code.length() == codeLength) {
//                break;
//            }
//            if (code.length() == 0) {
//                if (!String.valueOf(reversedNum.charAt(i)).equals("0")) {
//                    code += String.valueOf(reversedNum.charAt(i));
//                } else {
//                    //
//                }
//            } else {
//                if (!code.contains(String.valueOf(reversedNum.charAt(i)))) {
//                    code += String.valueOf(reversedNum.charAt(i));
//                }
//            }
//        }
//        String finalCode = code.substring(0, codeLength);
//        //System.out.println("The random secret number is " + finalCode + ".");
//        return finalCode;
//
//
//    }


    public static boolean guessCheck(String secretCode) {
        Scanner sc = new Scanner(System.in);


        List<String> digits = new ArrayList<>();
        String guess = sc.nextLine();
        int bullCount = 0;
        int cowCount = 0;

        if (secretCode.length() == 1) {
            if (guess.equals(secretCode)) {
                System.out.println("Grade: 1 bull(s).");
                return true;
            } else {
                System.out.println("Grade: None.");
                return false;
            }
        }

        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bullCount++;
                digits.add(String.valueOf(secretCode.charAt(i)));
            }
        }
//        if (bullCount == 4) {
//            System.out.println("you win!");
//        }
        String codeEdit = secretCode;
        String guessEdit = guess;
        for (String s : digits) {
            codeEdit = codeEdit.replace(s, "");
            guessEdit = guessEdit.replace(s, "");
        }

        if (guessEdit.length() > 0) {
            for (char s : guessEdit.toCharArray()) {
                if (codeEdit.contains(String.valueOf(s))) {
                    cowCount++;
                    codeEdit = codeEdit.replace(String.valueOf(s), "");
                }
            }
        }

        if (cowCount > 0 && bullCount > 0) {
            System.out.println("Grade: " + bullCount + " bull(s) and " + cowCount + " cow(s).");
        } else if (cowCount > 0) {
            System.out.println("Grade: " + cowCount + " cow(s).");
        } else if (bullCount > 0) {
            System.out.println("Grade: " + bullCount + " bull(s).");
        } else {
            System.out.println("Grade: None.");
        }

        return bullCount == secretCode.length();

    }

    public static String improvedCodeGenerator(int digits, int possibleCharacters) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String code = "";
        int randomPicker;

        List<Character> chars = new ArrayList<>();
        for (int i = 48; i < 58; i++) {
            chars.add((char) i);
        }
        for (int i = 97; i < 123; i++) {
            chars.add((char) i);
        }

        while (code.length() < digits) {
//            //sb.append(String.valueOf(rand.nextInt(10)));
//
            String randomDigit = String.valueOf(chars.get(rand.nextInt(possibleCharacters)));
            if (!code.contains(randomDigit)) {
                code += randomDigit;
            }
        }

        //System.out.println(sb.toString());
        String s = sb.toString();
        return code;
    }

}
