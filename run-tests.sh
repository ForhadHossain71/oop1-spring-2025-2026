#!/usr/bin/env bash
# Compiles and runs all JUnit 5 tests.
# Requires: JDK 17+, lib/junit-platform-console-standalone-1.10.2.jar

set -e

BASEDIR="$(cd "$(dirname "$0")" && pwd)"
JUNIT_JAR="$BASEDIR/lib/junit-platform-console-standalone-1.10.2.jar"
OUT="$BASEDIR/build/classes"

rm -rf "$OUT"
mkdir -p "$OUT"

echo "=== Compiling BookShopTask sources ==="
javac -d "$OUT" "$BASEDIR"/BookShopTask/*.java

echo "=== Compiling BookShopTask tests ==="
javac -d "$OUT" -cp "$OUT:$JUNIT_JAR" "$BASEDIR"/tests/BookShopTask/*.java

echo "=== Compiling parking-management-system sources ==="
javac -d "$OUT" -sourcepath "$BASEDIR/parking-management-system" \
    "$BASEDIR"/parking-management-system/parkingmanagementsystem/entity/Vehicle.java \
    "$BASEDIR"/parking-management-system/parkingmanagementsystem/fileio/VehicleFileIO.java

echo "=== Compiling parking-management-system tests ==="
javac -d "$OUT" -cp "$OUT:$JUNIT_JAR" \
    "$BASEDIR"/tests/parkingmanagementsystem/entity/VehicleTest.java \
    "$BASEDIR"/tests/parkingmanagementsystem/fileio/VehicleFileIOTest.java

# Run tests from a temporary working directory so that VehicleFileIO's relative
# paths ("parkingmanagementsystem/fileio/vehicles.txt") resolve inside an
# isolated folder instead of polluting the repo.
WORKDIR=$(mktemp -d)
trap "rm -rf $WORKDIR" EXIT

echo ""
echo "=== Running all tests ==="
cd "$WORKDIR"
java -jar "$JUNIT_JAR" \
    --class-path "$OUT" \
    --scan-class-path "$OUT" \
    --details verbose
