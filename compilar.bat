@echo off
echo ========================================
echo  Compilando Projeto P2SO
echo ========================================
echo.

call mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo  Compilacao concluida com sucesso!
    echo ========================================
    echo.
    echo O executavel foi gerado em:
    echo target\P2SO-0.0.1-SNAPSHOT.jar
    echo.
    echo Para executar, use: executar.bat
) else (
    echo.
    echo ========================================
    echo  Erro na compilacao!
    echo ========================================
)

echo.
pause
