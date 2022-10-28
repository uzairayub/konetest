package com.tsl.elevator.network;

import android.provider.SyncStateContract;

import com.tsl.elevator.utils.GsonUtil;
import com.tsl.elevator.utils.SPConstants;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // base url for the API Client
    private static final String BASE_URL = SPConstants.BASE_URL;
    // Retrofit client instance
    private static Retrofit authRetrofit = null;
    private static Retrofit retrofit = null;

    // Singleton initialization of Retorfit client instance
    public static Retrofit getAuthTokenClient() {
        if (authRetrofit == null) {
            // OkHTTPClient Client initialization

            OkHttpClient httpClient;
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                // OkHTTPClient Client initialization
                httpClient = new OkHttpClient.Builder()
                        .addInterceptor(new BasicAuthInterceptor("e9083038-0ac3-43f4-a90f-c17d155fa6b7", "258bf2bbadc602defe36b6ffd3e580481124134d17f9f5b9a30093c60563aeb0"))
                        // Connection timeout 40 seconds
                        .connectTimeout(40, TimeUnit.SECONDS)
                        // Socket Read timeout 40 seconds
                        .readTimeout(40, TimeUnit.SECONDS)
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })
                        // Add HTTP logging interceptor at body level
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            authRetrofit = new Retrofit.Builder()
                    // Add GSON parser
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.Companion.ins().getGson()))
                    // Set baseurl
                    .baseUrl(BASE_URL)
                    // set httpClient OkHTTPClient.
                    .client(httpClient)
                    .build();
        }
        return authRetrofit;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            // OkHTTPClient Client initialization

            OkHttpClient httpClient;
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                // OkHTTPClient Client initialization
                httpClient = new OkHttpClient.Builder()
                        // Connection timeout 40 seconds
                        .connectTimeout(40, TimeUnit.SECONDS)
                        // Socket Read timeout 40 seconds
                        .readTimeout(40, TimeUnit.SECONDS)
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })
                        // Add HTTP logging interceptor at body level
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            retrofit = new Retrofit.Builder()
                    // Add GSON parser
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.Companion.ins().getGson()))
                    // Set baseurl
                    .baseUrl(BASE_URL)
                    // set httpClient OkHTTPClient.
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
