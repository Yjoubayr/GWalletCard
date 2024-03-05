package com.yossefjm.gwalletcard;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Class to generate the pass data
 */
public class PassData {
    private String issuerEmail;
    private String issuerId;
    private String passClass;
    private String passId;

    /**
     * Constructor
     */
    public PassData() {
        this.issuerEmail = Constants.ISSUER_EMAIL;
        this.issuerId = Constants.ISSUER_ID;
        this.passClass = Constants.PASS_CLASS;
        // the passId will be the QR code value
        this.passId = UUID.randomUUID().toString();
    }

    /**
     * Generate the JSON for the pass
     * @return The JSON
     */
    public String generatePassJson() {
        return "{\n" +
                "  \"iss\": \"" + issuerEmail + "\",\n" +
                "  \"aud\": \"google\",\n" +
                "  \"typ\": \"savetowallet\",\n" +
                "  \"iat\": " + (new Date().getTime() / 1000L) + ",\n" +
                "  \"origins\": [],\n" +
                "  \"payload\": {\n" +
                "    \"genericObjects\": [\n" +
                "      {\n" +
                "        \"id\": \"" + issuerId + "." + passId + "\",\n" +
                "        \"classId\": \"" + passClass + "\",\n" +
                "        \"genericType\": \"GENERIC_TYPE_UNSPECIFIED\",\n" +
                "        \"hexBackgroundColor\": \"#4285f4\",\n" +
                "        \"logo\": {\n" +
                "          \"sourceUri\": {\n" +
                "            \"uri\": \"https://storage.googleapis.com/wallet-lab-tools-codelab-artifacts-public/pass_google_logo.jpg\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"cardTitle\": {\n" +
                "          \"defaultValue\": {\n" +
                "            \"language\": \"en\",\n" +
                "            \"value\": \"Google I/O '22  [DEMO ONLY]\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"subheader\": {\n" +
                "          \"defaultValue\": {\n" +
                "            \"language\": \"en\",\n" +
                "            \"value\": \"Attendee\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"header\": {\n" +
                "          \"defaultValue\": {\n" +
                "            \"language\": \"en\",\n" +
                "            \"value\": \"Alex McJacobs\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"barcode\": {\n" +
                "          \"type\": \"QR_CODE\",\n" +
                "          \"value\": \"" + passId + "\"\n" +
                "        },\n" +
                "        \"heroImage\": {\n" +
                "          \"sourceUri\": {\n" +
                "            \"uri\": \"https://storage.googleapis.com/wallet-lab-tools-codelab-artifacts-public/google-io-hero-demo-only.jpg\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"textModulesData\": [\n" +
                "          {\n" +
                "            \"header\": \"POINTS\",\n" +
                "            \"body\": \"" + new Random().nextInt(9999) + "\",\n" +
                "            \"id\": \"points\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"header\": \"CONTACTS\",\n" +
                "            \"body\": \"" + new Random().nextInt(99) + "\",\n" +
                "            \"id\": \"contacts\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }
}
