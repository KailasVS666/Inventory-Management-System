Write-Host "Compiling JavaFX Inventory Management System..." -ForegroundColor Green
Write-Host ""

# Set JavaFX path
$JAVAFX_PATH = "C:\Users\sharj\OneDrive\Documents\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib"

# Create sources.txt if it doesn't exist
if (-not (Test-Path "sources.txt")) {
    Write-Host "Creating sources.txt..." -ForegroundColor Yellow
    Get-ChildItem -Recurse -Filter "*.java" src | ForEach-Object { $_.FullName } > sources.txt
}

# Compile with JavaFX modules
Write-Host "Compiling with JavaFX modules..." -ForegroundColor Yellow
javac --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp src -d out @(Get-Content sources.txt)

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Compilation successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Running JavaFX application..." -ForegroundColor Green
    java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp out com.inventory.gui.InventoryManagementApp
} else {
    Write-Host ""
    Write-Host "Compilation failed! Please check the error messages above." -ForegroundColor Red
    Read-Host "Press Enter to continue"
}
