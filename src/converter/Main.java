package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] input = scanner.nextLine().split(" ");
            if (input.length == 1) {
                break;
            } else {
                BigInteger sourceBase = BigInteger.valueOf(Long.parseLong(input[0]));
                BigInteger targetBase = BigInteger.valueOf(Long.parseLong(input[1]));
                while (true) {
                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);
                    String input2 = scanner.nextLine();
                    if (input2.equals("/back")) {
                        break;
                    } else if (!input2.contains(".")) {
                        Number<String> numberToDecimal = new Number<>(input2);
                        BigInteger convertedToDecimal = numberToDecimal.convertTo(sourceBase);
                        Number<String> numberToBase = new Number<>(convertedToDecimal.toString());
                        System.out.println("Conversion result: " + numberToBase.convertFrom(targetBase));
                    } else {
                        String[] input2Splitted = input2.split("\\.");
                        String integerPart = input2Splitted[0];
                        String decimalPart = input2Splitted[1];

                        Number<String> integerPartToDecimal = new Number<>(integerPart);
                        BigInteger integerPartConvertedToDecimal = integerPartToDecimal.convertTo(sourceBase);
                        Number<String> numberToBase = new Number<>(integerPartConvertedToDecimal.toString());
                        String integerPartConvertedFromDecimal = numberToBase.convertFrom(targetBase);

                        Number<String> decimalPartToDecimal = new Number<>(decimalPart);
                        BigDecimal decimalPartConvertedToDecimal = decimalPartToDecimal.convertToDecimalPart(new BigDecimal(sourceBase));
                        Number<String> decimalToBase = new Number<>(decimalPartConvertedToDecimal.toString());
                        String decimalPartConvertedFromDecimal = decimalToBase.convertFrom(new BigDecimal(targetBase));

                        System.out.println("Conversion result: " + integerPartConvertedFromDecimal + "." + decimalPartConvertedFromDecimal);
                    }
                }
            }
        }
    }
}
