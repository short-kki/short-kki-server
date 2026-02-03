#!/bin/bash

ES_URL="http://localhost:9200"
INDEX="recipes"
BASE_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "delete index"
curl -s -X DELETE "$ES_URL/$INDEX" || true

echo "create index"
tr -d '\r' < "$BASE_DIR/recipe-index.json" | curl -s -X PUT "$ES_URL/$INDEX" \
  -H "Content-Type: application/json" \
  -d @-

echo "bulk insert"
tr -d '\r' < "$BASE_DIR/recipe-docs.json" | curl -s -X POST "$ES_URL/$INDEX/_bulk" \
  -H "Content-Type: application/x-ndjson" \
  --data-binary @-

echo "done"
