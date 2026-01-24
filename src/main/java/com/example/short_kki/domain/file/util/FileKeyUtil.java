package com.example.short_kki.domain.file.util;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileKeyUtil {

    private static final int MAX_EXT_LENGTH = 10;
    private static final Pattern SAFE_EXT = Pattern.compile("^[a-z0-9]{1," + MAX_EXT_LENGTH + "}$");
    private static final Pattern EDGE_SLASHES = Pattern.compile("^/+|/+$");
    private static final Pattern UUID_HYPHEN = Pattern.compile("-");

    /**
     * 입력 값의 앞뒤 슬래시의 위치에 상관 없이 온전한 prefix로 조합
     */
    public static String joinPrefix(String... parts) {
        String joined = Arrays.stream(parts)
                .filter(p -> p != null && !p.isBlank())
                .map(p -> EDGE_SLASHES.matcher(p.trim()).replaceAll(""))
                .filter(p -> !p.isEmpty())
                .reduce((a, b) -> a + "/" + b)
                .orElse("");

        return joined.isEmpty() ? "" : joined + "/";
    }

    public static String generateKey(String prefix, String originalFilename) {
        String uuid = UUID_HYPHEN.matcher(UUID.randomUUID().toString()).replaceAll("");
        String ext = extractSafeExtension(originalFilename);
        String base = prefix.isEmpty() ? uuid : prefix + uuid;
        return (ext == null) ? base : base + "." + ext;
    }

    public static String extractSafeExtension(String originalFilename) {
        if (originalFilename == null) {
            return null;
        }

        String name = originalFilename.trim();
        if (name.isEmpty()) {
            return null;
        }

        int dot = name.lastIndexOf('.');
        if (dot <= 0 || dot == name.length() - 1) {
            return null;
        }

        String ext = name.substring(dot + 1).toLowerCase();
        return SAFE_EXT.matcher(ext).matches() ? ext : null;
    }
}