public class Generator implements UsGenerator {
    UsIsin usIsin = new UsIsin();
    UsCusip usCusip = new UsCusip();

    @Override
    public String generateIsin(String lastIsin) {
        String cusipFromIsin = usCusip.getCusipFromIsin(lastIsin);
        String cusip = usCusip.generate(cusipFromIsin);
        return usIsin.generate(cusip);
    }
}
