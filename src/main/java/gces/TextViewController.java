package gces;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author pradish
 */

interface UserFoundListener{
    public void onUserFound(User user, String userKey);
}
public class TextViewController implements Initializable, BarcodeListener{
    private static UserFoundListener listener;

    @FXML
    private Text label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BarcodeReader.addReader(this, App.scene);
        label.setText("Insert your barcode");
    }

    @Override
    public void onBarcodeRead(String barcode) {
        System.out.println("Barcode read : " + barcode);
        label.setText("Your barcode is : " + barcode + "\nConnecting to database...");

        if(InternetChecker.internetAvailable()) {
            DatabaseReference ref = FirebaseEngine.database.getReference("users");

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean found = false;
                    String key = "";
                    String name = "";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Long barCode = postSnapshot.child("barCode").getValue(Long.class);
                        if (barCode.toString().equals(barcode)) {
                            key = postSnapshot.getKey();
                            name = postSnapshot.child("name").getValue(String.class);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        App.screenController.activate("voting_view");
                    } else {
                        label.setText("Barcode not found.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
//                    text.setText("Read failed.");
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ref.addListenerForSingleValueEvent(postListener);
        }
        else{
            App.screenController.activate("voting_view");
            listener.onUserFound(new User(100, "Pradish", "9814133236"), "xxxx");
            label.setText("Please check your internet connectivity.");
        }

    }

    public static void setUserFoundListener(UserFoundListener listener){
        TextViewController.listener = listener;
    }

}