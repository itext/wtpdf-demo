# itext-wtpdf-pdfademo

## Introduction

This repository contains the code and resources used to create the Well-Tagged PDF (WTPDF) example posted on https://pdfa.org/wtpdf.
It uses the [pdfHTML](https://github.com/itext/itext-pdfhtml-java) add-on (html2pdf) for the [iText Core](https://github.com/itext/itext-java) PDF library.



## Validation with veraPDF
In addition to WTPDF conformance, the example also conforms to the PDF/A-4 (ISO 19005-1) and PDF/UA-2 (ISO 14289-2) standards. To validate conformance with PDF/A-4 and PDF/UA-2, we used veraPDF.

The veraPDF tool is an open-source, industry-supported PDF/A and PDF/UA validator, and is available as a command-line tool and as a Java library. The tool can be downloaded from https://verapdf.org/software/.

### PDF/A-4 veraPDF report
```xml
<?xml version="1.0" encoding="utf-8"?>
<report>
    <buildInformation>
        <releaseDetails id="core" version="1.26.1" buildDate="2024-05-16T16:30:00+02:00"></releaseDetails>
        <releaseDetails id="validation-model" version="1.26.1" buildDate="2024-05-16T18:13:00+02:00"></releaseDetails>
        <releaseDetails id="gui" version="1.26.2" buildDate="2024-05-19T13:33:00+02:00"></releaseDetails>
    </buildInformation>
    <jobs>
        <job>
            <item size="107852">
                <name>D:\prototypes\itext-wtpdf-pdfademo\wtpdf.pdf</name>
            </item>
            <validationReport jobEndStatus="normal" profileName="PDF/A-4 validation profile" statement="PDF file is compliant with Validation Profile requirements." isCompliant="true">
                <details passedRules="108" failedRules="0" passedChecks="14462" failedChecks="0"></details>
            </validationReport>
            <duration start="1721198489436" finish="1721198489720">00:00:00.284</duration>
        </job>
    </jobs>
    <batchSummary totalJobs="1" failedToParse="0" encrypted="0" outOfMemory="0" veraExceptions="0">
        <validationReports compliant="1" nonCompliant="0" failedJobs="0">1</validationReports>
        <featureReports failedJobs="0">0</featureReports>
        <repairReports failedJobs="0">0</repairReports>
        <duration start="1721198489430" finish="1721198489727">00:00:00.297</duration>
    </batchSummary>
</report>

```
PDF/UA-2 veraPDF report

```xml
<?xml version="1.0" encoding="utf-8"?>
<report>
    <buildInformation>
        <releaseDetails id="core" version="1.26.1" buildDate="2024-05-16T16:30:00+02:00"></releaseDetails>
        <releaseDetails id="validation-model" version="1.26.1" buildDate="2024-05-16T18:13:00+02:00"></releaseDetails>
        <releaseDetails id="gui" version="1.26.2" buildDate="2024-05-19T13:33:00+02:00"></releaseDetails>
    </buildInformation>
    <jobs>
        <job>
            <item size="107852">
                <name>D:\prototypes\itext-wtpdf-pdfademo\wtpdf.pdf</name>
            </item>
            <validationReport jobEndStatus="normal" profileName="PDF/UA-2 + Tagged PDF validation profile" statement="PDF file is compliant with Validation Profile requirements." isCompliant="true">
                <details passedRules="1744" failedRules="0" passedChecks="24382" failedChecks="0"></details>
            </validationReport>
            <duration start="1721198712059" finish="1721198712345">00:00:00.286</duration>
        </job>
    </jobs>
    <batchSummary totalJobs="1" failedToParse="0" encrypted="0" outOfMemory="0" veraExceptions="0">
        <validationReports compliant="1" nonCompliant="0" failedJobs="0">1</validationReports>
        <featureReports failedJobs="0">0</featureReports>
        <repairReports failedJobs="0">0</repairReports>
        <duration start="1721198712053" finish="1721198712354">00:00:00.301</duration>
    </batchSummary>
</report>

```