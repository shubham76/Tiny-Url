package controller;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UrlEncoderDecoder {

    public static String encodeUrl(String url) {
        String encodedUrl = Hashing.sha256()
                .hashString(url, StandardCharsets.UTF_8)
                .toString();

        return encodedUrl;
    }
}
