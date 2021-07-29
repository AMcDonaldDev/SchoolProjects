package org.cmsc495.bpo.cucumber.webpages;

public interface WebPage {
    public static String BASE_URL = "http://localhost:8080";

    /**
     * Get the URL of this Web Page
     * 
     * @return
     */
    public String getUrl();

    public WebPage then();
}
