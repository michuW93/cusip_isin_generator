public class Generator implements UsGenerator {
    UsIsin usIsin = new UsIsin();
    UsCusip usCusip = new UsCusip();

    private String cusip;
    private String usIsin1;


    @Override
    public String generate(String lastIsin) {
        String cusipFromIsin = usCusip.getCusipFromIsin(lastIsin);
        cusip = usCusip.generate(cusipFromIsin);
        usIsin1 = usIsin.generate(cusip);
        return usIsin1;
    }

}
