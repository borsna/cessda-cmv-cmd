# cessda-cmv-cmd
CESSDA validator console application.
Using same validation checks as the online validator hosted by CESSDA: https://cmv.cessda.eu

## Build
`./mvnw package`

## Install & run
1. Download latest jar-file from https://github.com/borsna/cessda-cmv-cmd/releases/latest
2. Download a CESSDA DDI profile (xml) from https://cmv.cessda.eu/documentation/profiles.html
3. To validate run: `java -jar cmv-cmd.jar /path/to/ddi.xml /path/to/profile.xml`
4. No output = no validation errors
