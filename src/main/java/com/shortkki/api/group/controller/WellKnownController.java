package com.shortkki.api.group.controller;

import com.shortkki.global.config.DeepLinkProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class WellKnownController {

    private final DeepLinkProperties deepLinkProperties;

    @GetMapping(value = "/apple-app-site-association")
    public ResponseEntity<Map<String, Object>> appleAppSiteAssociation() {
        String appId = deepLinkProperties.ios().teamId() + "." + deepLinkProperties.ios().bundleId();

        Map<String, Object> detail = Map.of(
                "appIDs", List.of(appId),
                "components", List.of(
                        Map.of("/", "/group/invite/*")
                )
        );

        Map<String, Object> body = Map.of(
                "applinks", Map.of(
                        "details", List.of(detail)
                )
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping(value = "/assetlinks.json")
    public ResponseEntity<List<Map<String, Object>>> assetLinks() {
        Map<String, Object> target = Map.of(
                "namespace", "android_app",
                "package_name", deepLinkProperties.android().packageName(),
                "sha256_cert_fingerprints", deepLinkProperties.android().sha256CertFingerprints()
        );

        Map<String, Object> entry = Map.of(
                "relation", List.of("delegate_permission/common.handle_all_urls"),
                "target", target
        );

        return ResponseEntity.ok(List.of(entry));
    }
}
