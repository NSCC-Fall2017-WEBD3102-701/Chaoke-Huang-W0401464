package com.webd3102;

import okhttp3.*;

import java.io.IOException;

public class Requests {
    private static OkHttpClient client = new OkHttpClient();
    private static GetRequest getRequest = new GetRequest();
    private static PostRequest postRequest = new PostRequest();
    private static PutRequest putRequest = new PutRequest();
    private static DeleteRequest deleteRequest = new DeleteRequest();

    public static String Get(String url) throws IOException {
        return getRequest.get(url);
    }

    public static String Post(String url, String json) throws IOException {
        return postRequest.post(url, json);
    }

    public static String Put(String url, String json) throws IOException {
        return putRequest.put(url, json);
    }

    public static String Delete(String url) throws IOException {
        return deleteRequest.delete(url);
    }

    private static class GetRequest {
        String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

    private static class PostRequest {
        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

    private static class PutRequest {
        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        String put(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }

    private static class DeleteRequest {
        String delete(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }
}