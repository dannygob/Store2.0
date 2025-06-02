@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@rem ##########################################################################
@rem ##
@rem ##  Gradle startup script for Windows
@rem ##
@rem ##########################################################################

@if "%DEBUG%" == "" @echo off
@rem set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
set CLASSPATH=%~dp0gradle\wrapper\gradle-wrapper.jar

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
echo.
goto fail

:findJavaFromJavaHome
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
echo.
goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:winNT_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:winNT_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set _ARG=%~1
if not "%_ARG:~0,1%" == """" set _ARG="%_ARG%"
if not exist %_ARG% set _ARG=%~1

set CMD_LINE_ARGS=%CMD_LINE_ARGS% %_ARG%
shift
goto win9xME_args_slurp

:execute
@rem Setup the command line

set JAVA_OPTS=%DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS%
set JAVA_OPTS=%JAVA_OPTS% -Dorg.gradle.appname="%~n0"
set JAVA_OPTS=%JAVA_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain

@rem Start Gradle
"%JAVA_EXE%" %JAVA_OPTS% %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if "%GRADLE_EXIT_CONSOLE%" == "" exit /b 1
exit 1

:mainEnd
if "%OS%" == "Windows_NT" endlocal

:omega
