package gces;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

interface BarcodeListener{
    void onBarcodeRead(String barcode);
}

public class BarcodeReader{
    private static final long THRESHOLD = 1200;
    private static final int MIN_BARCODE_LENGTH = 8;

    private static final StringBuffer barcode = new StringBuffer();
    private static long lastTime;

    public static void addReader(BarcodeListener listener, Scene scene) {
        lastTime = System.currentTimeMillis();
        scene.setOnKeyReleased((new EventHandler<javafx.scene.input.KeyEvent>() {
            public void handle(javafx.scene.input.KeyEvent event) {
                long diff = System.currentTimeMillis() - lastTime;
                lastTime = System.currentTimeMillis();
                if (diff > THRESHOLD) {
                    barcode.delete(0, barcode.length());
                }

                if (event.getCode().equals(KeyCode.ENTER)){
                    if (barcode.length() >= MIN_BARCODE_LENGTH) {
                        listener.onBarcodeRead(barcode.toString());
                    }
                    barcode.delete(0, barcode.length());
                }
                else {
                    barcode.append(event.getText());
                }
            }
        }));
    }
}