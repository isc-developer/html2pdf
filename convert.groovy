import com.sap.gateway.ip.core.customdev.util.Message
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import com.itextpdf.html2pdf.ConverterProperties
import java.io.*
import MailComposerFont

def Message processData(Message message) {
    def inputStream = message.getBody(java.io.InputStream)
    def outputStream = new java.io.ByteArrayOutputStream()
    
    try {
        def properties = new ConverterProperties()
        def provider = new DefaultFontProvider(true, true, false)
        
        def font = getFont("Roboto-Regular.ttf")
        if (font != null) {
            provider.addFont(font)
        }
        
        font = getFont("Roboto-Bold.ttf")
        if (font != null) {
            provider.addFont(font)
        }
    
        font = getFont("arial.ttf")
        if (font != null) {
            provider.addFont(font)
        }
        
        font = getFont("arialbd.ttf")
        if (font != null) {
            provider.addFont(font)
        }
    
        properties.setFontProvider(provider)
        
        HtmlConverter.convertToPdf(inputStream, outputStream, properties)
        message.setBody(outputStream.toByteArray())
        return message 
    } finally {
        inputStream.close()
        outputStream.close()
    }
}

def byte[] getFont(java.lang.String fontName) {
    return MailComposerFont.get(fontName) 
}

def byte[] convertInputStreamToBytes(InputStream inputStream){
    ByteArrayOutputStream result = new ByteArrayOutputStream()
    byte[] buffer = new byte[1024]
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
        result.write(buffer, 0, length);
    }
    return result.toByteArray()
}

