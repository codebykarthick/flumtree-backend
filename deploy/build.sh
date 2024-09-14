echo "Build script triggered!"
echo "Detected JAVA_HOME at $JAVA_HOME"
cd ..
sh gradlew clean spotlessApply build
cd deploy || exit 1
echo "Build complete!"