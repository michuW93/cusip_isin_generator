public class Generator implements UsGenerator {
    UsIsin usIsin = new UsIsin();
    UsCusip usCusip = new UsCusip();
    private String isin;

    @Override
    public String generateIsin(String lastIsin) {
        String cusipFromIsin = usCusip.getCusipFromIsin(lastIsin);
        isin = usIsin.generate(usCusip.generate(cusipFromIsin));
        return isin;
    }

}
