mkdir .\bin
mkdir .\out
javac.exe -d ./bin -cp src src/org/spooner/java/BunnyClick/*.java
if %errorlevel% NEQ 0 (goto done)
jar.exe cvfm ./out/BunnyClick.jar ./build-files/game-manifest.MF -C bin org buildings achievements sounds sprites upgrades *.xml *png font.ttf
echo f | xcopy /i /y .\build-files\run.bat .\out\run.bat
:done
PAUSE
