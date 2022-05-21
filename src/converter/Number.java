package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Number<T> {

    private final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final String alphabetString = "abcdefghijklmnopqrstuvwxyz";
    private final T number;

    Number(T number) {
        this.number = number;
    }

//    converts to RADIX BASE from DECIMAL
    String convertFrom(BigInteger radix) {
        String binary = "";
        String binaryReversed = "";
        BigInteger quotient = new BigInteger((String) number);
        if (quotient.equals(BigInteger.ZERO)) {
            return "0";
        }
        while (true) {
            BigInteger remainder = quotient.remainder(radix);
            if (quotient.equals(BigInteger.ZERO)) {
                break;
            } else if (quotient.equals(BigInteger.ONE)) {
                binary += remainder;
                break;
            }
            if (remainder.compareTo(BigInteger.TEN) > -1) {
                binary += alphabet[remainder.subtract(BigInteger.TEN).intValueExact()];
            } else {
                binary += remainder;
            }
            quotient = quotient.divide(radix);
        }

        for (int i = binary.length() - 1; i >= 0; i--) {
            binaryReversed += binary.charAt(i);
        }
        return binaryReversed;
    }

    //    converts DECIMAL PART to RADIX BASE
    String convertFrom(BigDecimal radix) {
        String binary = "";
        BigDecimal quotient = new BigDecimal((String) number);
        for (int i = 0; i < 5; i++) {
            BigInteger integerPart = quotient.multiply(radix).toBigInteger();
            BigDecimal decimalPart = quotient.multiply(radix).subtract(new BigDecimal(integerPart));

            if (decimalPart.equals(BigDecimal.ZERO)) {
                break;
            }
            if (integerPart.compareTo(BigInteger.TEN) > -1) {
                binary += alphabet[integerPart.subtract(BigInteger.TEN).intValueExact()];
            } else {
                binary += integerPart;
            }
            quotient = decimalPart;
        }
        return binary;
    }

//    converts to DECIMAL from BASE
    BigInteger convertTo(BigInteger base) {
        BigInteger result = BigInteger.ZERO;
        String numberString = number.toString().toLowerCase();
        int exp = 0;
        for (int i = numberString.length() - 1; i >= 0; i--) {
            if (alphabetString.contains(String.valueOf(numberString.charAt(i)))) {
                result = result.add(BigInteger.valueOf(alphabetString.indexOf(numberString.charAt(i)) + 10).multiply(base.pow(exp)));
            } else {
                result = result.add(BigInteger.valueOf(Character.getNumericValue(numberString.charAt(i))).multiply(base.pow(exp)));
            }
            exp++;
        }
        return result;
    }

    BigDecimal convertToDecimalPart(BigDecimal base) {
        BigDecimal result = BigDecimal.ZERO;
        String numberString = number.toString().toLowerCase();
        int exp = 1;
        for (int i = 0; i < numberString.length(); i++) {
            if (alphabetString.contains(String.valueOf(numberString.charAt(i)))) {
                result = result.add(BigDecimal.valueOf(alphabetString.indexOf(numberString.charAt(i)) + 10).multiply(BigDecimal.ONE.divide(base.pow(exp), 10, RoundingMode.HALF_DOWN)));
            } else {
                result = result.add(BigDecimal.valueOf(Character.getNumericValue(numberString.charAt(i))).multiply(BigDecimal.ONE.divide(base.pow(exp), 10, RoundingMode.HALF_DOWN)));
            }
            exp++;
        }
        return result;
    }
}
