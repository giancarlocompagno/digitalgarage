set SOURCE_COMMONS_BE=%~dp0\..\..\java
cd %SOURCE_COMMONS_BE%
xjc -d .\ 	..\resources\xsd\mail-template-schema_01.01.xsd
pause
