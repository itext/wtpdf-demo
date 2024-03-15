package com.itextpdf.wtpdfsample;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.tags.HTagWorker;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 */
public class App {

    private static final String SOURCE_FOLDER = "./src/main/resources/";
    private static final Set<String> H_TAGS = new HashSet<>(Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6", "h7"));

    public static void main(String[] args) throws IOException, XMPException {
        String outFile = "wtpdf.pdf";
        PdfOutputIntent outputIntent = new PdfOutputIntent(
                "Custom",
                "",
                "http://www.color.org",
                "sRGB IEC61964-2.1",
                Files.newInputStream(Paths.get(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));


        WriterProperties writerProperties = new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0);
        PdfADocument pdfDocument = new PdfADocument(new PdfWriter(outFile, writerProperties), PdfAConformanceLevel.PDF_A_4, outputIntent);

        DefaultTagWorkerFactory factory = new DefaultTagWorkerFactory() {
            @Override
            public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
                if (H_TAGS.contains(tag.name())) {
                    return new HTagWorker(tag, context) {
                        @Override
                        public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
                            return super.processTagChild(childTagWorker, context);
                        }

                        @Override
                        public IPropertyContainer getElementResult() {
                            IPropertyContainer elementResult = super.getElementResult();
                            if (elementResult instanceof Div) {
                                for (IElement child : ((Div) elementResult).getChildren()) {
                                    if (child instanceof Paragraph) {
                                        ((Paragraph) child).setNeutralRole();
                                    }
                                }
                            }
                            return elementResult;
                        }
                    };
                }
                return super.getCustomTagWorker(tag, context);
            }
        };

        // setup the general requirements for a wtpdf document
        byte[] bytes = Files.readAllBytes(Paths.get(SOURCE_FOLDER + "simplePdfUA2.xmp"));
        XMPMeta xmpMeta = XMPMetaFactory.parse(new ByteArrayInputStream(bytes));
        pdfDocument.setXmpMetadata(xmpMeta);
        pdfDocument.setTagged();
        pdfDocument.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        pdfDocument.getCatalog().setLang(new PdfString("en-US"));
        PdfDocumentInfo info = pdfDocument.getDocumentInfo();
        info.setTitle("Well tagged PDF document");

        // Use custom font provider as we only want embedded fonts
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(SOURCE_FOLDER + "NotoSans-Regular.ttf");
        fontProvider.addFont(SOURCE_FOLDER + "NotoEmoji-Regular.ttf");

        ConverterProperties converterProperties = new ConverterProperties()
                .setBaseUri(SOURCE_FOLDER)
                .setTagWorkerFactory(factory)
                .setFontProvider(fontProvider);


        File file = new File(SOURCE_FOLDER + "article.html");
        try (FileInputStream str = new FileInputStream(file)) {
            HtmlConverter.convertToPdf(str, pdfDocument, converterProperties);
        }
        pdfDocument.close();
        System.out.println("WTPDF created");
    }
}