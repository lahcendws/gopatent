package com.wildcodeschool.patent.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Map;

/**
 * Service to get the access_token to consume the API
 */
@Service
public class OPSBearerToken {

    /**
     * get the keys on application.properties
     */
    @Value("${ops.consumer.key}")
    private String consummerKey;

    @Value("${ops.consumer.secret.key}")
    private String consummerSecretKey;

    /**
     * initiate bearerToken
     */
    private static String bearerToken = null;

    /**
     * first step: encode on Base64 the keys
     * @param consummerKey
     * @param consummerSecretKey
     * @return
     */
    public String getBase64(String consummerKey, String consummerSecretKey) {
        String credentials = consummerKey + ":" + consummerSecretKey;
        String basicAuth = Base64.getEncoder().encodeToString(credentials.getBytes());
        return basicAuth;
    }

    /**
     * get the bearer token
     * TODO: check response, if keys are KO.
     * @throws URISyntaxException
     * @throws IOException
     */
    public void getBearerToken() throws URISyntaxException, IOException {
        String key64 = getBase64(consummerKey, consummerSecretKey);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
        Request request = new Request.Builder()
                .url("https://ops.epo.org/3.2/auth/accesstoken")
                .method("POST", body)
                .addHeader("Authorization", "Basic " + key64)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jObject = new JSONObject(jsonData);
        bearerToken = jObject.getString("access_token");
    }

    public Response executeRequest(String url, String contentType) throws URISyntaxException, IOException {
        return executeRequest(url, contentType, null);
    }

    /**
     * TODO: add code 500 return
     * Service to request API, being sure of the bearer token
     * @param url
     * @param contentType
     * @return Response
     * @throws URISyntaxException
     * @throws IOException
     */
    public Response executeRequest(String url, String contentType, Map<String, String> optionnalHeaders)
            throws URISyntaxException, IOException {
        if (bearerToken == null) {
            getBearerToken();
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Accept", contentType)
                .addHeader("Authorization", "Bearer " + bearerToken);

        if (optionnalHeaders != null) {
            for (Map.Entry<String, String> entry : optionnalHeaders.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();

        Response response = client.newCall(request).execute();

        if (response.code() == 400) {
            getBearerToken();
            executeRequest(url, contentType, optionnalHeaders);
        }
        return response;
    }
}
