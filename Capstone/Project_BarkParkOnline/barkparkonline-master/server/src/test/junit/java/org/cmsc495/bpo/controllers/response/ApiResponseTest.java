package org.cmsc495.bpo.controllers.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bson.Document;
import org.cmsc495.bpo.controllers.response.ApiResponse.ApiResponseBody;
import org.junit.Before;
import org.junit.Test;

public class ApiResponseTest {

    private ApiResponse ok;

    private ApiResponse error;

    private final String OK_MESSAGE = "Everything is A-Ok!";

    private final String ERROR_MESSAGE = "Houston, we got a problem";

    private final int ERROR_STATUS_CODE = 400;
    
    @Before
    public void init() {
        this.ok = ApiResponse.ok(OK_MESSAGE);
        this.error = ApiResponse.error(ERROR_STATUS_CODE, ERROR_MESSAGE);
    }

    @Test
    public void testGetOkStatusCodes() {
        assertEquals(200, this.ok.getStatusCodeValue());
        assertEquals(200, this.ok.getBody().getStatusCode());
    }

    @Test
    public void testGetOkMessage() {
        assertEquals(OK_MESSAGE, this.ok.getBody().getMessage());
    }

    @Test
    public void testGetOkBody() {
        assertTrue(this.ok.getBody() != null);
    }

    @Test
    public void testGetErrorStatusCodes() {
        assertEquals(200, this.error.getStatusCodeValue());
        assertEquals(ERROR_STATUS_CODE, this.error.getBody().getStatusCode());
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals(ERROR_MESSAGE, this.error.getBody().getMessage());
    }

    @Test
    public void testSettersAndGettersForApiResponseBody() {
        ApiResponseBody body = new ApiResponseBody();
        body.setStatusCode(ERROR_STATUS_CODE);
        body.setMessage(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, body.getMessage());
        assertEquals(ERROR_STATUS_CODE, body.getStatusCode());
    }

    @Test
    public void testGetMessageAs() throws Exception {
        Document document = new Document();
        document.put("hello", "world");
        this.ok = this.ok.withMessage(document);

        assertEquals(document, this.ok.getBody().getMessageAs(Document.class));
    }
}
