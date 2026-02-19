#!/bin/bash
set -euo pipefail

IMAGE="${REGISTRY}/${IMAGE_NAME}"
TAG="${IMAGE_TAG:-latest-dev}"

echo "=== Deploying image: ${IMAGE}:${TAG} ==="

# 현재 실행 중인 컨테이너의 이미지 태그 저장 (롤백용)
PREV_IMAGE=$(docker inspect --format='{{.Config.Image}}' shortkki-server 2>/dev/null || echo "")
echo "Previous image: ${PREV_IMAGE:-none}"

# GHCR 로그인 & 새 이미지 pull
echo "$GITHUB_TOKEN" | docker login ghcr.io -u "$GITHUB_ACTOR" --password-stdin
docker pull "${IMAGE}:${TAG}"

# 기존 컨테이너 중지
docker stop shortkki-server || true
docker rm shortkki-server || true

# 새 컨테이너 실행
docker run -d \
  --name shortkki-server \
  --restart unless-stopped \
  -p 80:8080 \
  -v ~/shortkki/env:/app/env \
  -v ~/shortkki/secrets:/app/secrets \
  -e SPRING_PROFILES_ACTIVE=dev \
  "${IMAGE}:${TAG}"

# 헬스체크 (최대 60초)
echo "Waiting for health check..."
HEALTHY=false
for i in $(seq 1 30); do
  if curl -sf http://localhost/actuator/health > /dev/null 2>&1; then
    echo "Health check passed"
    HEALTHY=true
    break
  fi
  sleep 2
done

if [ "$HEALTHY" = true ]; then
  docker image prune -f
  echo "=== Deploy succeeded ==="
  exit 0
fi

# 헬스체크 실패 → 롤백
echo "Health check failed. Container logs:"
docker logs --tail 50 shortkki-server

if [ -z "$PREV_IMAGE" ]; then
  echo "No previous image to rollback to. Exiting with error."
  exit 1
fi

echo "=== Rolling back to: ${PREV_IMAGE} ==="
docker stop shortkki-server || true
docker rm shortkki-server || true

docker run -d \
  --name shortkki-server \
  --restart unless-stopped \
  -p 80:8080 \
  -v ~/shortkki/env:/app/env \
  -v ~/shortkki/secrets:/app/secrets \
  -e SPRING_PROFILES_ACTIVE=dev \
  "${PREV_IMAGE}"

# 롤백 후 헬스체크 (최대 60초)
echo "Waiting for rollback health check..."
for i in $(seq 1 30); do
  if curl -sf http://localhost/actuator/health > /dev/null 2>&1; then
    echo "Rollback health check passed"
    break
  fi
  sleep 2
done

echo "=== Rollback completed. Deploy failed. ==="
exit 1
