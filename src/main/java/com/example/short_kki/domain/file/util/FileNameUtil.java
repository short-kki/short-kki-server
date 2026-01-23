package com.example.short_kki.domain.file.util;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileNameUtil {

    public static String generate(String prefix, String originalFilename) {
        String normalizedPrefix = trimSlashes(prefix);
        String ext = extractSafeExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (ext == null) {
            return normalizedPrefix + "/" + uuid;
        }
        return normalizedPrefix + "/" + uuid + "." + ext;
    }

    public static String extractSafeExtension(String originalFilename) {
        if (originalFilename == null) {
            return null;
        }
        String name = originalFilename.trim();
        int idx = name.lastIndexOf('.');
        if (idx <= 0 || idx == name.length() - 1) {
            return null;
        }

        String ext = name.substring(idx + 1).toLowerCase();
        if (ext.length() < 1 || ext.length() > 10) {
            return null;
        }

        for (int i = 0; i < ext.length(); i++) {
            char c = ext.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return null;
            }
        }
        return ext;
    }

    private static String trimSlashes(String s) {
        if (s == null) {
            return "";
        }
        String t = s.trim();
        while (t.startsWith("/")) {
            t = t.substring(1);
        }
        while (t.endsWith("/")) {
            t = t.substring(0, t.length() - 1);
        }
        return t;
    }
}
