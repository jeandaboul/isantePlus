@ECHO OFF
IF NOT "%~f0" == "~f0" GOTO :WinNT
@"jruby-complete-1.7.10.jar" "C:/Users/jmaxy/workspace/openmrs-module-htmlformentryui/omod/.rubygems/bin/bundle" %1 %2 %3 %4 %5 %6 %7 %8 %9
GOTO :EOF
:WinNT
@"jruby-complete-1.7.10.jar" "%~dpn0" %*
