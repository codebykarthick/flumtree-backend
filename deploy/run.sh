# Run the build script first
sh build.sh

echo "Running the application..."

# Run the jar
# --add-opens is needed to allow java packages to be exposed to the spring classloader
java --add-opens=java.base/java.lang=ALL-UNNAMED -jar ./build/libs/flumtree-0.0.1-SNAPSHOT.jar