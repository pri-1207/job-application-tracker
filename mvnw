#!/bin/sh
# Maven Wrapper script for Unix systems
# Downloads Maven if not already present and runs it

set -e

MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    DIST_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

DIST_URL=${DIST_URL:-"https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"}

MAVEN_HOME="${HOME}/.m2/wrapper/dists/apache-maven-3.9.6"

if [ ! -d "$MAVEN_HOME" ]; then
    echo "Downloading Maven from $DIST_URL..."
    mkdir -p "$MAVEN_HOME"
    TEMP_FILE=$(mktemp)
    curl -fsSL "$DIST_URL" -o "$TEMP_FILE"
    unzip -qo "$TEMP_FILE" -d "$MAVEN_HOME/.."
    rm "$TEMP_FILE"
fi

MVN_CMD=$(find "$MAVEN_HOME" -name "mvn" -type f 2>/dev/null | head -1)
if [ -z "$MVN_CMD" ]; then
    MVN_CMD="$MAVEN_HOME/bin/mvn"
fi

exec "$MVN_CMD" "$@"
