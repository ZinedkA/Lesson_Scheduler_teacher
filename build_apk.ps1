$ErrorActionPreference = "Stop"
$ProgressPreference = 'SilentlyContinue'
$WorkingDir = "C:\Users\ysfmr\.gemini\antigravity\scratch\OzelDersTakip"
$SdkDir = "$WorkingDir\AndroidSDK"
$CmdLineToolsDir = "$SdkDir\cmdline-tools\latest"

Write-Host "Creating directories..."
New-Item -ItemType Directory -Force -Path $CmdLineToolsDir | Out-Null

Write-Host "Downloading Command Line Tools (This will take a minute)..."
$ToolsZip = "$WorkingDir\cmdline-tools.zip"
# Use WebClient to bypass Invoke-WebRequest UI hangs
$WebClient = New-Object System.Net.WebClient
$WebClient.DownloadFile("https://dl.google.com/android/repository/commandlinetools-win-11479570_latest.zip", $ToolsZip)

Write-Host "Extracting Command Line Tools..."
Expand-Archive -Path $ToolsZip -DestinationPath "$SdkDir\cmdline-tools" -Force
Move-Item -Path "$SdkDir\cmdline-tools\cmdline-tools\*" -Destination $CmdLineToolsDir -Force
Remove-Item -Path "$SdkDir\cmdline-tools\cmdline-tools" -Recurse -Force
Remove-Item -Path $ToolsZip -Force

$SdkManager = "$CmdLineToolsDir\bin\sdkmanager.bat"

Write-Host "Accepting licenses and installing SDK packages..."
# Using yes | sdkmanager to auto-accept licenses
cmd.exe /c "yes | `"$SdkManager`" --licenses"
cmd.exe /c "`"$SdkManager`" `"platforms;android-34`" `"build-tools;34.0.0`""

Write-Host "Setting local.properties..."
"sdk.dir=$($SdkDir.Replace('\', '\\'))" | Out-File -FilePath "$WorkingDir\local.properties" -Encoding utf8

Write-Host "Downloading Gradle..."
$GradleZip = "$WorkingDir\gradle.zip"
$WebClient.DownloadFile("https://downloads.gradle.org/distributions/gradle-8.4-bin.zip", $GradleZip)
Write-Host "Extracting Gradle..."
Expand-Archive -Path $GradleZip -DestinationPath $WorkingDir -Force
Remove-Item -Path $GradleZip -Force

$GradleExe = "$WorkingDir\gradle-8.4\bin\gradle.bat"

Write-Host "Building APK..."
Set-Location -Path $WorkingDir
cmd.exe /c "`"$GradleExe`" assembleDebug"

Write-Host "Build finished!"
