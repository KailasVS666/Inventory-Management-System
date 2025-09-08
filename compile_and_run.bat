@echo off
echo Compiling JavaFX Inventory Management System...
echo.

REM Set your JavaFX lib path
set JAVAFX_LIB="C:\Users\sharj\OneDrive\Documents\openjfx-24.0.2_windows-x64_bin-sdk\javafx-sdk-24.0.2\lib"

REM Compile all necessary Java source files
echo Compiling all source packages...

REM FIXED: Added 'src\com\inventory\*.java' to include the DataStore class
javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -d bin src\com\inventory\*.java src\com\inventory\gui\*.java src\com\inventory\managers\*.java src\com\inventory\models\*.java

REM Check if compilation was successful
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo.
    echo Running JavaFX application...

    REM FIXED: Changed 'Main' to 'InventoryManagementApp' to match your project's main class
    java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -cp bin com.inventory.gui.InventoryManagementApp

) else (
    echo.
    echo Compilation failed! Please check the error messages above.
    pause
)