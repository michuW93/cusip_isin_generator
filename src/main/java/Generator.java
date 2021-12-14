import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator implements UsGenerator {
    private static final String US_PREFIX = "US";

    private String cusip;
    private String usIsin;


    @Override
    public String generate(String lastIsin) {
        String cusipFromIsin = getCusipFromIsin(lastIsin);
        cusip = generateCusip(cusipFromIsin);
        usIsin = generateNextIsinFromCusip(cusip);
        return usIsin;
    }

    String generateCusip(String previousCusip){
        String cusipWithoutCheckDigit = removeCheckDigit(previousCusip);
        String nextCusip = generateCusipWithoutCheckDigit(cusipWithoutCheckDigit);
        nextCusip = nextCusip + generateCusipCheckDigit(nextCusip);
        return nextCusip;
    }

    String getCusipFromIsin(String isin) {
        String cusip = isin.substring(2, 11);
        return cusip;
    }

    private String generateNextIsinFromCusip(String cusip) {
        String isin = US_PREFIX + cusip;
        isin = isin + generateIsinCheckDigit(isin);
        return isin;
    }

    String generateCusipCheckDigit(String cusip) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*@#";
        int sum = 0;
        int value = 0;
        char[] cusipChars = cusip.toUpperCase().toCharArray();

        for (int i = 0; i < cusipChars.length; i++) {
            char actualChar = cusipChars[i];
            if (Character.isDigit(actualChar)) {
                value = Integer.parseInt(String.valueOf(actualChar));
            } else if (Character.isAlphabetic(actualChar)){
                value = alphabet.indexOf(actualChar) + 10;
            } else if (cusipChars[i] == '*'){
                value = 36;
            } else if (cusipChars[i] == '@'){
                value = 37;
            } else if (cusipChars[i] == '#'){
                value = 38;
            }

            if ((i % 2) != 0){
                value *= 2;
            }
            value = (value % 10) + (value / 10);
            sum += value;
        }
        int check = (10 - (sum % 10)) % 10;

        return String.valueOf(check);
    }

    String generateIsinCheckDigit(String isin) {
        int sum = 0;
        int value = 0;
        char[] isinChars = isin.toUpperCase().toCharArray();
        String isinAsNumericalCharacters = "";

        for (int i = 0; i < isinChars.length; i++) {
            char actualChar = isinChars[i];
            if (Character.isDigit(actualChar)) {
                isinAsNumericalCharacters += Integer.parseInt(String.valueOf(actualChar));
            } else if (Character.isAlphabetic(actualChar)){
                isinAsNumericalCharacters += Integer.parseInt(String.valueOf(actualChar - 55));
            }
        }
        for (int i = 0; i < isinAsNumericalCharacters.length(); i++) {
            value = Integer.parseInt(String.valueOf(isinAsNumericalCharacters.charAt(i)));
            if (isinAsNumericalCharacters.length() % 2 == 0) {
                if ((i % 2) != 0){
                    value *= 2;
                }
            } else {
                if ((i % 2) == 0){
                    value *= 2;
                }
            }

            value = (value % 10) + (value / 10);
            sum += value;
        }
        int check = (10 - (sum % 10)) % 10;

        return String.valueOf(check);
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

    private String removeCheckDigit(String cusip) {
        return cusip.substring(0, cusip.length() - 1);
    }

    public String getCusip() {
        return cusip;
    }

    public String getUsIsin() {
        return usIsin;
    }
}
