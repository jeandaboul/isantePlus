@ECHO OFF
IF NOT "%~f0" == "~f0" GOTO :WinNT
@"jruby-complete-1.7.10.jar" "C:/Users/ITECH/Documents/GitHub/openmrs-module-uicommons/scss/.rubygems/bin/sass-convert" %1 %2 %3 %4 %5 %6 %7 %8 %9
GOTO :EOF
:WinNT
@"jruby-complete-1.7.10.jar" "%~dpn0" %*
