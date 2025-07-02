@echo off
echo Running Lazurite build...
lazurite build ./materials

:: Define source and destination folders
set "SRC="
set "DEST=compiled"

:: Create destination folder if it doesn't exist
if not exist "%DEST%" (
    mkdir "%DEST%"
)

:: Move .material.bin files
echo Moving .material.bin files from %SRC% to %DEST%...
move "%SRC%\*.material.bin" "%DEST%"

echo Done!
pause
