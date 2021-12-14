public class Generator implements UsGenerator {
    UsIsin usIsin = new UsIsin();
    UsCusip usCusip = new UsCusip();

    private String cusip;
    private String isin;

    @Override
    public String generate(String lastIsin) {
        String cusipFromIsin = usCusip.getCusipFromIsin(lastIsin);
        cusip = usCusip.generate(cusipFromIsin);
        isin = usIsin.generate(cusip);
        return isin;
    }

}
