package com.shortkki.api.group.controller;

import com.shortkki.api.group.application.service.GroupService;
import com.shortkki.api.group.dto.response.GroupPreviewResponse;
import com.shortkki.global.config.DeepLinkProperties;
import com.shortkki.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class GroupInviteLandingController {

    private final GroupService groupService;
    private final DeepLinkProperties deepLinkProperties;

    @GetMapping(value = "/group/invite/{code}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String inviteLanding(@PathVariable String code) {
        try {
            GroupPreviewResponse preview = groupService.getGroupPreviewByInviteCode(code);
            return buildLandingHtml(preview, code);
        } catch (BusinessException e) {
            return buildErrorHtml(e.getMessage());
        }
    }

    private String buildLandingHtml(GroupPreviewResponse preview, String code) {
        String deepLink = deepLinkProperties.webDomain() + "/group/invite/" + code;
        String appStoreUrl = deepLinkProperties.store().appStoreUrl();
        String playStoreUrl = deepLinkProperties.store().playStoreUrl();
        String groupName = escapeHtml(preview.name());
        String description = preview.description() != null ? escapeHtml(preview.description()) : "숏끼에서 함께 요리해요!";
        String thumbnail = preview.thumbnailImgUrl() != null ? escapeHtml(preview.thumbnailImgUrl()) : "";

        return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>%s - 숏끼 그룹 초대</title>
                    <meta property="og:title" content="%s 그룹에 초대되었어요!">
                    <meta property="og:description" content="%s">
                    <meta property="og:image" content="%s">
                    <meta property="og:url" content="%s">
                    <meta property="og:type" content="website">
                    <style>
                        * { margin: 0; padding: 0; box-sizing: border-box; }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                            background: #f5f5f5;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                            padding: 20px;
                        }
                        .card {
                            background: #fff;
                            border-radius: 20px;
                            padding: 40px 30px;
                            max-width: 400px;
                            width: 100%%;
                            text-align: center;
                            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                        }
                        .thumbnail {
                            width: 80px;
                            height: 80px;
                            border-radius: 20px;
                            object-fit: cover;
                            margin-bottom: 16px;
                        }
                        .placeholder-icon {
                            width: 80px;
                            height: 80px;
                            border-radius: 20px;
                            background: #FF6B35;
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            margin-bottom: 16px;
                            font-size: 36px;
                            color: #fff;
                        }
                        .group-name {
                            font-size: 22px;
                            font-weight: 700;
                            color: #222;
                            margin-bottom: 8px;
                        }
                        .description {
                            font-size: 14px;
                            color: #888;
                            margin-bottom: 6px;
                        }
                        .member-count {
                            font-size: 13px;
                            color: #aaa;
                            margin-bottom: 28px;
                        }
                        .btn {
                            display: block;
                            width: 100%%;
                            padding: 14px;
                            border-radius: 12px;
                            font-size: 16px;
                            font-weight: 600;
                            text-decoration: none;
                            margin-bottom: 10px;
                            cursor: pointer;
                            border: none;
                        }
                        .btn-primary {
                            background: #FF6B35;
                            color: #fff;
                        }
                        .btn-secondary {
                            background: #f0f0f0;
                            color: #333;
                        }
                    </style>
                </head>
                <body>
                    <div class="card">
                        %s
                        <div class="group-name">%s</div>
                        <div class="description">%s</div>
                        <div class="member-count">멤버 %d명</div>
                        <a id="storeBtn" class="btn btn-primary" href="#">앱에서 열기</a>
                        <a id="downloadBtn" class="btn btn-secondary" href="#">앱 다운로드</a>
                    </div>
                    <script>
                        (function() {
                            var ua = navigator.userAgent || '';
                            var isIOS = /iPhone|iPad|iPod/i.test(ua);
                            var isAndroid = /Android/i.test(ua);
                            var storeBtn = document.getElementById('storeBtn');
                            var downloadBtn = document.getElementById('downloadBtn');
                            var appStoreUrl = '%s';
                            var playStoreUrl = '%s';

                            if (isIOS) {
                                storeBtn.href = appStoreUrl;
                                downloadBtn.href = appStoreUrl;
                                downloadBtn.textContent = 'App Store에서 다운로드';
                            } else if (isAndroid) {
                                storeBtn.href = playStoreUrl;
                                downloadBtn.href = playStoreUrl;
                                downloadBtn.textContent = 'Google Play에서 다운로드';
                            } else {
                                storeBtn.href = appStoreUrl;
                                downloadBtn.href = playStoreUrl;
                                downloadBtn.textContent = 'Google Play에서 다운로드';
                            }
                        })();
                    </script>
                </body>
                </html>
                """.formatted(
                groupName,
                groupName,
                description,
                thumbnail,
                deepLink,
                thumbnail.isEmpty()
                        ? "<div class=\"placeholder-icon\">\uD83C\uDF73</div>"
                        : "<img class=\"thumbnail\" src=\"" + thumbnail + "\" alt=\"group\">",
                groupName,
                description,
                preview.memberCount(),
                appStoreUrl,
                playStoreUrl
        );
    }

    private String buildErrorHtml(String message) {
        String appStoreUrl = deepLinkProperties.store().appStoreUrl();
        String playStoreUrl = deepLinkProperties.store().playStoreUrl();

        return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>숏끼 - 초대 링크 오류</title>
                    <style>
                        * { margin: 0; padding: 0; box-sizing: border-box; }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                            background: #f5f5f5;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                            padding: 20px;
                        }
                        .card {
                            background: #fff;
                            border-radius: 20px;
                            padding: 40px 30px;
                            max-width: 400px;
                            width: 100%%;
                            text-align: center;
                            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                        }
                        .error-icon {
                            font-size: 48px;
                            margin-bottom: 16px;
                        }
                        .title {
                            font-size: 20px;
                            font-weight: 700;
                            color: #222;
                            margin-bottom: 10px;
                        }
                        .message {
                            font-size: 14px;
                            color: #888;
                            margin-bottom: 28px;
                            line-height: 1.5;
                        }
                        .btn {
                            display: block;
                            width: 100%%;
                            padding: 14px;
                            border-radius: 12px;
                            font-size: 16px;
                            font-weight: 600;
                            text-decoration: none;
                            margin-bottom: 10px;
                            cursor: pointer;
                            border: none;
                        }
                        .btn-secondary {
                            background: #f0f0f0;
                            color: #333;
                        }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <div class="error-icon">\u26A0\uFE0F</div>
                        <div class="title">초대 링크를 열 수 없어요</div>
                        <div class="message">%s</div>
                        <a id="downloadBtn" class="btn btn-secondary" href="#">앱 다운로드</a>
                    </div>
                    <script>
                        (function() {
                            var ua = navigator.userAgent || '';
                            var isAndroid = /Android/i.test(ua);
                            var btn = document.getElementById('downloadBtn');
                            btn.href = isAndroid ? '%s' : '%s';
                            btn.textContent = isAndroid ? 'Google Play에서 다운로드' : 'App Store에서 다운로드';
                        })();
                    </script>
                </body>
                </html>
                """.formatted(
                escapeHtml(message),
                playStoreUrl,
                appStoreUrl
        );
    }

    private static String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
