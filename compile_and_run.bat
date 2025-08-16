@echo off
echo Compiling JavaFX Inventory Management System...
echo.

REM Set JavaFX path
set JAVAFX_PATH=C:\Users\sharj\OneDrive\Documents\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib

REM Create sources.txt if it doesn't exist
if not exist sources.txt (
    echo Creating sources.txt...
    dir /S /B src\com\inventory\*.java > sources.txt
)

REM Compile with JavaFX modules
echo Compiling with JavaFX modules...
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp src -d out @sources.txt

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo.
    echo Running JavaFX application...
    java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp out com.inventory.gui.InventoryManagementApp
) else (
    echo.
    echo Compilation failed! Please check the error messages above.
    pause
)
