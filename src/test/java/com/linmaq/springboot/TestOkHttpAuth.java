package com.linmaq.springboot;

//import okhttp3.*;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.io.IOException;
//import java.nio.charset.Charset;

/**
 * @Class : TestOkHttpAuth
 * @author: marin
 * @date : 9:46 PM 7/19/2021
 */
public class TestOkHttpAuth {
    //private final OkHttpClient client;
    //
    //public TestOkHttpAuth() {
    //    client = new OkHttpClient();
    //}
    //
    //@Value("${endpoint}")
    //private String endpoint;
    //
    //
    //@Value("${username}")
    //private String username;
    //
    //
    //@Value("${password}")
    //private String password;
    //
    ///**
    // * HTTPRequest.POST
    // * <p>
    // * Basic authentication involves sending a verified username and password with your request
    // * <p>
    // * In the request Headers, you will see that the Authorization header is going to pass the API a Base64 encoded string representing your username and password values, appended to the text "Basic "
    // * <p>
    // * reference: https://learning.postman.com/docs/sending-requests/authorization/#basic-auth
    // *
    // * @return 返回结果
    // */
    //public String doAccess() {
    //    String basic = username + ":" + password;
    //    RequestBody rb = RequestBody.create("{}".getBytes(Charset.forName("UTF-8")));
    //    String encode = new Base64().encodeToString(basic.getBytes(Charset.forName("UTF-8")));
    //    Request request = new Request.Builder()
    //            .url(endpoint)
    //            .addHeader("Content-Type", "application/x-www-form-urlencoded")
    //            .addHeader("Authorization", "Basic " + encode)
    //            .post(rb)
    //            .build();
    //    try {
    //        Response response = client.newCall(request).execute();
    //        if (response.isSuccessful() && response.code() == 200) {
    //            return response.body().string();
    //        }
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //
    //    return null;
    //}
    //
    ///**
    // * error example
    // *
    // * @return
    // */
    //public String doAccessErrorExample() {
    //    RequestBody rb = RequestBody.create("{}".getBytes(Charset.forName("UTF-8")));
    //    Request request = new Request.Builder()
    //            .url(endpoint)
    //            .addHeader("Content-Type", "application/x-www-form-urlencoded")
    //            .post(rb)
    //            .build();
    //    OkHttpClient client = new OkHttpClient().newBuilder().authenticator(new Authenticator() {
    //        @Nullable
    //        @Override
    //        public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
    //            String credential = Credentials.basic(username, password);
    //            return request.newBuilder().addHeader("Authorization", credential).build();
    //        }
    //    }).build();
    //    try {
    //        Response response = client.newCall(request).execute();
    //        if (response.isSuccessful() && response.code() == 200) {
    //            return response.body().string();
    //        }
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //
    //    return null;
    //}
}
