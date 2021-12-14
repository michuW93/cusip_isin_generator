import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsCusip implements UsIdentifier {
    @Override
    public String calculateCheckDigit(String cusip) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*@#";
        int sum = 0;
        int value = 0;
        char[] cusipChars = cusip.toUpperCase().toCharArray();

        for (int i = 0; i < cusipChars.length; i++) {
            char actualChar = cusipChars[i];
            if (Character.isDigit(actualChar)) {
                value = Integer.parseInt(String.valueOf(actualChar));
            } else if (Character.isAlphabetic(actualChar)) {
                value = alphabet.indexOf(actualChar) + 10;
            } else if (cusipChars[i] == '*') {
                value = 36;
            } else if (cusipChars[i] == '@') {
                value = 37;
            } else if (cusipChars[i] == '#') {
                value = 38;
            }

            if ((i % 2) != 0) {
                value *= 2;
            }
            value = (value % 10) + (value / 10);
            sum += value;
        }
        int check = (10 - (sum % 10)) % 10;

        return String.valueOf(check);
    }

    @Override
    public String generate(String value) {
        String cusipWithoutCheckDigit = removeCheckDigit(value);
        String nextCusip = generateCusipWithoutCheckDigit(cusipWithoutCheckDigit);
        nextCusip = nextCusip + calculateCheckDigit(nextCusip);
        return nextCusip;
    }

    private String removeCheckDigit(String cusip) {
        return cusip.substring(0, cusip.length() - 1);
    }

    private String generateCusipWithoutCheckDigit(String cusipWithoutCheckDigit) {
        String nextCusip;
        do {
            nextCusip = generateNextAlphanumericNumber(cusipWithoutCheckDigit);
        } while (nextCusip.contains("I") || nextCusip.contains("O"));
        return nextCusip;
    }

    /**
     * method generates next cusip (alphanumerical character)
     *
     * @param cusipWithoutCheckDigit
     * @return next cusip
     */
    String generateNextAlphanumericNumber(String cusipWithoutCheckDigit) {
        Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
        Matcher matcher = compile.matcher(cusipWithoutCheckDigit);
        String left = "";
        String right = "";
        if (matcher.matches()) {
            left = matcher.group(1);
            right = matcher.group(2);
        }
        cusipWithoutCheckDigit = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1, 36) : "";
        cusipWithoutCheckDigit += right.replace("Z", "A").replace("9", "0");
        return cusipWithoutCheckDigit.toUpperCase();
    }

    String getCusipFromIsin(String isin) {
        String cusip = isin.substring(2, 11);
        return cusip;
    }
}
