Write-Host "Compiling JavaFX Inventory Management System..." -ForegroundColor Green
Write-Host ""

# Set JavaFX SDK path
$javafxLibPath = "C:/Users/sharj/OneDrive/Documents/openjfx-24.0.2_windows-x64_bin-sdk/javafx-sdk-24.0.2/lib"

# Create sources.txt if it doesn't exist
if (-not (Test-Path "sources.txt")) {
    Write-Host "Creating sources.txt..." -ForegroundColor Yellow
    Get-ChildItem -Recurse -Filter "*.java" src | ForEach-Object { $_.FullName } | Set-Content sources.txt
}

# Compile all Java files with JavaFX modules
Write-Host "Compiling with JavaFX modules..." -ForegroundColor Yellow
$sourceFiles = Get-ChildItem -Recurse -Filter "*.java" src | ForEach-Object { $_.FullName }

# Updated classpath to include JavaFX SDK
$classpath = ".;src;$javafxLibPath/*"
javac --module-path $javafxLibPath --add-modules javafx.controls,javafx.fxml -cp $classpath -d out $sourceFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Compilation successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Running JavaFX application..." -ForegroundColor Green
    $runClasspath = "out;$javafxLibPath\*"
    java --module-path $javafxLibPath --add-modules javafx.controls,javafx.fxml -cp $runClasspath com.inventory.gui.InventoryManagementApp
} else {
    Write-Host ""
    Write-Host "Compilation failed! Please check the error messages above." -ForegroundColor Red
    Read-Host "Press Enter to continue"
}
