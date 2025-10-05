#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# Name of the Java file (without extension)
CLASS_NAME="jaikin"

# Compile the Java file
echo "Compiling ${CLASS_NAME}.java..."
javac "${CLASS_NAME}.java"

# Run the compiled Java class
echo "Running ${CLASS_NAME}..."
java "${CLASS_NAME}"
