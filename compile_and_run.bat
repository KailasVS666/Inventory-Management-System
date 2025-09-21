@echo off
echo Compiling JavaFX Inventory Management System...
echo.

REM Set your JavaFX lib path
set JAVAFX_LIB="C:\Users\sharj\OneDrive\Documents\openjfx-24.0.2_windows-x64_bin-sdk\javafx-sdk-24.0.2\lib"

REM Create the output directory if it doesn't exist
if not exist out (
    mkdir out
)

REM Compile all necessary Java source files into the 'out' directory
echo Compiling all source packages...
javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -d out src\com\inventory\*.java src\com\inventory\gui\*.java src\com\inventory\managers\*.java src\com\inventory\models\*.java

REM Check if compilation was successful
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo.
    echo Running JavaFX application...

    REM Run the application from the 'out' directory
    java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -cp out com.inventory.gui.InventoryManagementApp

) else (
    echo.
    echo Compilation failed! Please check the error messages above.
    pause
)