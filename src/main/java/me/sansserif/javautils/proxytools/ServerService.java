package me.sansserif.javautils.proxytools;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.Duration;
import java.util.Set;

public interface ServerService {
    Set<Server> getServers() throws ServerLoadException;
    static Call buildCall(Request request) {
        return new OkHttpClient.Builder()
                .callTimeout(Duration.ofMinutes(1))
                .connectTimeout(Duration.ofSeconds(15))
                .readTimeout(Duration.ofSeconds(15))
                .writeTimeout(Duration.ofSeconds(15))
                .build()
                .newCall(request);
    }

    static String getValidResponse(Call call) {
        String result = null;
        try (Response response = call.execute()) {
            if (!response.isSuccessful() || response.body() == null) throw new Exception();
            result = response.body().string();
        } catch (Exception ignored) {}
        return result;
    }
    class ServerLoadException extends Exception {}
}
