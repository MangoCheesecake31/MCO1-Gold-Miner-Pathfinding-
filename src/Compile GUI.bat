:: BATCH SCRIPT FOR COMPILING & RUNNING CODE
@ECHO OFF

:: Set FX Library Path
set PATH_TO_FX="C:\Program Files\Java\javafx-sdk-11.0.2\lib"

:: Change directory
cd..

:: Copy resources
xcopy src\resources out\resources /i 
xcopy src\view out\view /i

:: Change directory
cd src

:: Compile all contorller classes with javafx
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -d ../out controller/*.java

:: Compile DriverGUI with javafx
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -d ../out driver/DriverGUI.java

:: Run DriverGUI
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp ../out driver.DriverGUI
:: End	