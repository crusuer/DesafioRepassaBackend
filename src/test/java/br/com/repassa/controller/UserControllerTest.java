package br.com.repassa.controller;

import br.com.repassa.model.Evaluation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class UserControllerTest {

    private static final String API_HOST = "https://desafio-repassa.herokuapp.com";
    private static final String API_ENDPOINT_EVALUATIONS = "/user/evaluations";
    private CredentialsProvider provider = new BasicCredentialsProvider();

    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {

        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    @BeforeEach
    void setUp() {
        //Given
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("josebonifacio", "josebonifacio");
        provider.setCredentials(AuthScope.ANY, credentials);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUnauthorizedUser() throws IOException {
        //Given
        HttpUriRequest request = new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    void testAuthorizedUserWithoutPermission() throws IOException {
        //Given
        CredentialsProvider providerLocal = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("repassa", "repassa");
        providerLocal.setCredentials(AuthScope.ANY, credentials);

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(providerLocal).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS));

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    void testAuthorizedUserWithPermission() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS));
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("application/json", mimeType);
    }

    @Test
    void findAssignedEvaluations() throws IOException {

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS));

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        List evaluations = retrieveResourceFromResponse(httpResponse, List.class);
        evaluations.forEach(Assert::assertNotNull);
    }

    @Test
    void sendFeedback() throws IOException {

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPut httpPut = new HttpPut(API_HOST + API_ENDPOINT_EVALUATIONS + "/50");
        String inputJson = "{" +
                "  \"creative\": 5," +
                "  \"focused\": 3," +
                "  \"helpful\": 4," +
                "  \"leadership\": 1," +
                "  \"responsible\": 5" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPut);

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        Evaluation evaluation = retrieveResourceFromResponse(httpResponse, Evaluation.class);
        assertNotNull(evaluation);
    }

}