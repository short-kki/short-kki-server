package com.shortkki.api.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shortkki.api.notification.application.port.PushNotificationPort;
import com.shortkki.api.notification.infra.fcm.FcmPushNotificationAdapter;
import com.shortkki.api.notification.infra.fcm.NoOpPushNotificationAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FcmProperties.class)
public class FcmConfig {

    private final FcmProperties fcmProperties;

    @Bean
    public PushNotificationPort pushNotificationPort() throws IOException {
        if (!fcmProperties.enabled()) {
            log.info("FCM is disabled. Using NoOpPushNotificationAdapter.");
            return new NoOpPushNotificationAdapter();
        }

        FirebaseMessaging firebaseMessaging = initializeFirebaseMessaging();
        log.info("FCM is enabled. Using FcmPushNotificationAdapter.");
        return new FcmPushNotificationAdapter(firebaseMessaging);
    }

    private FirebaseMessaging initializeFirebaseMessaging() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream credentialsStream = getCredentialsStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                    .build();
            FirebaseApp.initializeApp(options);
            log.info("FirebaseApp initialized successfully.");
        }
        return FirebaseMessaging.getInstance();
    }

    private InputStream getCredentialsStream() throws IOException {
        String path = fcmProperties.credentialsPath();
        if (path == null || path.isBlank()) {
            throw new IllegalStateException("FCM credentials path is not configured.");
        }

        if (path.startsWith("classpath:")) {
            String resourcePath = path.substring("classpath:".length());
            return new ClassPathResource(resourcePath).getInputStream();
        }

        return new FileInputStream(path);
    }
}
