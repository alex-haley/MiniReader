@echo off

pushd build
echo "debug compilation"
javac -cp "../lib/pdfbox.jar;../src/Main.java;../src/PDFViewer.java;" -d ./ ../src/*.java
java -cp "../lib/pdfbox.jar;../src/Main.java;../src/PDFViewer.java;" Main ../books/refactoring.pdf
popd
