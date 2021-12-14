public interface UsIdentifier {
    String calculateCheckDigit(String value);
    String generate(String value);
}
