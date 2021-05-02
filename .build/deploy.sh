#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

function cleanup {
    echo "🧹 Cleanup..."
    rm -f gradle.properties richard-capraro-sign.asc
}

trap cleanup SIGINT SIGTERM ERR EXIT

echo "🚀 Preparing to deploy..."

echo "🔑 Decrypting files..."

gpg --quiet --batch --yes --decrypt --passphrase="${GPG_SECRET}" \
--output richard-capraro-sign.asc .build/richard-capraro-sign.asc.gpg

gpg --quiet --batch --yes --decrypt --passphrase="${GPG_SECRET}" \
    --output gradle.properties .build/gradle.properties.gpg

echo "💾 Importing 'richard-capraro-sign.asc'..."

gpg --fast-import --no-tty --batch --yes richard-capraro-sign.asc

echo "📦 Publishing..."

./gradlew publish

echo "✅ Done!"