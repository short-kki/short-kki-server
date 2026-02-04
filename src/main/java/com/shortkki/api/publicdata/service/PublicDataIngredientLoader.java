package com.shortkki.api.publicdata.service;

import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.publicdata.config.PublicDataApiProperties;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublicDataIngredientLoader implements ApplicationRunner {

    private final PublicDataApiProperties props;
    private final PublicDataIngredientPersistService persistService;
    private final RestClient restClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!props.enabled()) {
            log.info("[IngredientLoader] disabled");
            return;
        }

        String url = String.format(
                props.urlTemplate(),
                props.apiKey(),
                props.type(),
                props.apiUrl(),
                props.startIndex(),
                props.endIndex()
        );

        log.info("[IngredientLoader] API 호출 시작: {}", url.replaceAll(props.apiKey(), "***"));

        String xml = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        if (xml == null || xml.isBlank()) {
            log.warn("[IngredientLoader] API 응답이 비어있음");
            return;
        }

        Set<String> names = extractIngredientNames(xml);

        if (names.isEmpty()) {
            log.warn("[IngredientLoader] IRDNT_NM을 찾을 수 없음");
            return;
        }
        int inserted = persistService.saveNewIngredients(names);
        log.info("[IngredientLoader] {}개 재료 저장 완료 (총 {}개 중)", inserted, names.size());

    }

    private Set<String> extractIngredientNames(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // XXE 방어 설정
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        Document doc = factory.newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        NodeList nodes = doc.getElementsByTagName("IRDNT_NM");

        Set<String> set = new HashSet<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            String value = nodes.item(i).getTextContent();
            if (value == null) {
                continue;
            }
            value = value.trim();
            if (value.isBlank()) {
                continue;
            }
            set.add(value);
        }
        return set;
    }
}
