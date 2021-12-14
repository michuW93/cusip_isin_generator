public class UsIsin implements UsIdentifier {
    private static final String US_PREFIX = "US";
    String cusip;

    @Override
    public String calculateCheckDigit(String isin) {
        int sum = 0;
        int value = 0;
        char[] isinChars = isin.toUpperCase().toCharArray();
        String isinAsNumericalCharacters = "";

        for (int i = 0; i < isinChars.length; i++) {
            char actualChar = isinChars[i];
            if (Character.isDigit(actualChar)) {
                isinAsNumericalCharacters += Integer.parseInt(String.valueOf(actualChar));
            } else if (Character.isAlphabetic(actualChar)) {
                isinAsNumericalCharacters += Integer.parseInt(String.valueOf(actualChar - 55));
            }
        }
        for (int i = 0; i < isinAsNumericalCharacters.length(); i++) {
            value = Integer.parseInt(String.valueOf(isinAsNumericalCharacters.charAt(i)));
            if (isinAsNumericalCharacters.length() % 2 == 0) {
                if ((i % 2) != 0) {
                    value *= 2;
                }
            } else {
                if ((i % 2) == 0) {
                    value *= 2;
                }
            }

            value = (value % 10) + (value / 10);
            sum += value;
        }
        int check = (10 - (sum % 10)) % 10;

        return String.valueOf(check);
    }

    @Override
    public String generate(String value) {
        String isin = US_PREFIX + value;
        isin = isin + calculateCheckDigit(isin);
        return isin;
    }
}
