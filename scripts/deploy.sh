#!/bin/bash
set -euo pipefail

echo "$GITHUB_TOKEN" | docker login ghcr.io -u "$GITHUB_ACTOR" --password-stdin

docker pull "$REGISTRY/$IMAGE_NAME:latest"

docker stop shortkki-server || true
docker rm shortkki-server || true

# TODO: ELB 붙이고 매칭 포트 변경 (80 -> 8080)
docker run -d \
  --name shortkki-server \
  --restart unless-stopped \
  -p 80:8080 \
  --env-file ~/shortkki/.env \
  -e SPRING_PROFILES_ACTIVE=dev \
  "$REGISTRY/$IMAGE_NAME:latest"

docker image prune -f

echo "Waiting for health check..."
for i in $(seq 1 30); do
  if curl -sf http://localhost/actuator/health > /dev/null 2>&1; then
    echo "Health check passed"
    exit 0
  fi
  sleep 2
done
echo "Health check failed"
docker logs shortkki-server
exit 1
