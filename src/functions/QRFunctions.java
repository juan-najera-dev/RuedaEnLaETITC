package functions;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class QRFunctions {

    public static void generarQRCode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        //the BitMatrix class represents the 2D matrix of bits  
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static void guardarQR(String dataToQR) throws WriterException, IOException {
        //String[] data = dataToQR.split(":");
        //data[1] Numero Inventario Bicicleta
        //data[3] ID Estudiante
        //data[5] Nombre Estudiante
        //path where we want to get QR Code
        //String path = "resources/" + data[3] + ".png";
        String path = "resources/Quote.png";
        //Encoding charset to be used  
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        //generates QR code with Low level(L) error correction capability  
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //invoking the user-defined method that creates the QR code  
        generarQRCode(dataToQR, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
    }
    
    // Function to read the QR file
    public static String readQR(String path, String charset, Map hashMap) throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    // Driver code
    public static String devolverInfoQR() throws WriterException, IOException, NotFoundException {
        // Path where the QR code is saved
        String path = "resources/Quote.png";
        // Encoding charset
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return (readQR(path, charset, hintMap));
    }
}

