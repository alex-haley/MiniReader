@echo off

IF "%1"=="-compile" goto compile

pushd build
echo "debug build"
javac -cp "../lib/pdfbox.jar;../src/Main.java;../src/PDFViewer.java;" -d ./ ../src/*.java
java -cp "../lib/pdfbox.jar;../src/Main.java;../src/PDFViewer.java;" Main ../books/%1
popd

goto :eof

:compile

pushd build
echo "compiling"
javac -cp "../lib/pdfbox.jar;../src/Main.java;../src/PDFViewer.java;" -d ./ ../src/*.java
popd