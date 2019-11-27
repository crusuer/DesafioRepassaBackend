package br.com.repassa.controller;

import br.com.repassa.model.Employee;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;


class AdminControllerTest {

    private final static Logger LOGGER = LogManager.getLogger(AdminControllerTest.class);
    private static final String API_HOST = "https://desafio-repassa.herokuapp.com";
    private static final String API_ENDPOINT_EMPLOYEES = "/admin/employees";
    private static final String API_ENDPOINT_EVALUATIONS = "/admin/evaluations";
    private CredentialsProvider provider = new BasicCredentialsProvider();

    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {

        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    @BeforeEach
    void setUp() {
        //Given
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("repassa", "repassa");
        provider.setCredentials(AuthScope.ANY, credentials);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUnauthorizedUser() throws IOException {
        //Given
        HttpUriRequest request = new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertEquals(HttpStatus.SC_UNAUTHORIZED, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    void testAuthorizedUserWithoutPermission() throws IOException {
        //Given
        CredentialsProvider providerLocal = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("josebonifacio", "josebonifacio");
        providerLocal.setCredentials(AuthScope.ANY, credentials);

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(providerLocal).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES));

        // Then
        assertEquals(HttpStatus.SC_FORBIDDEN, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    void testAuthorizedUserWithPermission() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES));
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals("application/json", mimeType);
    }

    @Test
    void findAllEmployees() throws IOException {

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES));

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        List employees = retrieveResourceFromResponse(httpResponse, List.class);
        employees.forEach(Assert::assertNotNull);
    }

    @Test
    void findInexistentEmployee() throws IOException {

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES + "/0"));

        // Then
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
        Employee employee = retrieveResourceFromResponse(httpResponse, Employee.class);
        assertNull(employee.getId());
        assertNull(employee.getName());
        assertNull(employee.getUsername());
    }

    @Test
    void findExistentEmployee() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EMPLOYEES + "/46"));

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        Employee employee = retrieveResourceFromResponse(httpResponse, Employee.class);
        assertNotNull(employee.getId());
        assertNotNull(employee.getName());
        assertNotNull(employee.getUsername());
    }

    @Test
    void findAllEvaluations() throws IOException {

        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS));

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        List evaluations = retrieveResourceFromResponse(httpResponse, List.class);
        evaluations.forEach(Assert::assertNotNull);
    }

    @Test
    void findInexistentEvaluation() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS + "/0"));

        // Then
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());
        Evaluation evaluation = retrieveResourceFromResponse(httpResponse, Evaluation.class);
        assertNull(evaluation.getId());
    }

    @Test
    void findExistentEvaluation() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpResponse httpResponse = client.execute(new HttpGet(API_HOST + API_ENDPOINT_EVALUATIONS + "/50"));

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        Evaluation evaluation = retrieveResourceFromResponse(httpResponse, Evaluation.class);
        assertNotNull(evaluation.getId());
    }

    @Test
    void employeeUpdate() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPut httpPut = new HttpPut(API_HOST + API_ENDPOINT_EMPLOYEES + "/49");
        String inputJson = "{\n" +
                "\t\"name\": \"Almir Miranda\",\n" +
                "\t\"department\": \"Logistico\",\n" +
                "\t\"username\": \"almir\",\n" +
                "\t\"password\": \"123456\"\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPut);

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        Employee employee = retrieveResourceFromResponse(httpResponse, Employee.class);
        assertNotNull(employee);
    }

    @Test
    void evaluationUpdate() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPut httpPut = new HttpPut(API_HOST + API_ENDPOINT_EVALUATIONS + "/50");
        String inputJson = "{\n" +
                "\t\"creative\": 5,\n" +
                "\t\"focused\": 1,\n" +
                "\t\"helpful\": 1,\n" +
                "\t\"responsible\": 1,\n" +
                "\t\"leadership\": 1,\n" +
                "\t\"rated\": {\n" +
                "\t\t\"id\": 49\n" +
                "\t},\n" +
                "\t\"rater\": {\n" +
                "\t\t\"id\": 48\n" +
                "\t}\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPut);

        // Then
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        Employee employee = retrieveResourceFromResponse(httpResponse, Employee.class);
        assertNotNull(employee);
    }

    @Test
    void employeeSignUp() throws IOException {
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPost httpPost = new HttpPost(API_HOST + API_ENDPOINT_EMPLOYEES);
        String inputJson = "{\n" +
                "\t\"name\": \"Luis Santos\",\n" +
                "\t\"department\": \"Logistico\",\n" +
                "\t\"username\": \"luissantos\",\n" +
                "\t\"password\": \"123456\"\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPost);

        // Then
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
        Employee employee = retrieveResourceFromResponse(httpResponse, Employee.class);
        assertNotNull(employee);
    }

    @Test
    void evaluationSignUp() throws IOException{
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPost httpPost = new HttpPost(API_HOST + API_ENDPOINT_EVALUATIONS);
        String inputJson = "{\n" +
                "  \"creative\": 1,\n" +
                "  \"focused\": 1,\n" +
                "  \"helpful\": 1,\n" +
                "  \"responsible\": 0,\n" +
                "  \"leadership\": 1,\n" +
                "  \"id\": 0,\n" +
                "  \"rated\": {\n" +
                "    \"id\": 48\n" +
                "  },\n" +
                "  \"rater\": {\n" +
                "    \"id\": 49\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPost);

        // Then
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
        Evaluation evaluation = retrieveResourceFromResponse(httpResponse, Evaluation.class);
        assertNotNull(evaluation);
    }

    @Test
    void assignEvaluation() throws IOException{
        // When
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPost httpPost = new HttpPost(API_HOST + API_ENDPOINT_EVALUATIONS);
        String inputJson = "{\n" +
                "  \"rated\": 48,\n" +
                "  \"rater\": 49\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(stringEntity);

        HttpResponse httpResponse = client.execute(httpPost);

        // Then
        assertEquals(HttpStatus.SC_CREATED, httpResponse.getStatusLine().getStatusCode());
        Evaluation evaluation = retrieveResourceFromResponse(httpResponse, Evaluation.class);
        assertNotNull(evaluation);
    }
}