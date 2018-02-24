package gces;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

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
        label.setText("Insert your barcode...");
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
                    User user = new User();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Long barCode = postSnapshot.child("barCode").getValue(Long.class);
                        if (barCode.toString().equals(barcode)) {
                            key = postSnapshot.getKey();
                            user = postSnapshot.getValue(User.class);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        final String myKey = key;
                        final User myUser = user;
                        DatabaseReference ref = FirebaseEngine.database.getReference("votes");

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                boolean found = false;
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                        System.out.println(ds1.getValue(String.class));
                                        if (ds1.getValue(String.class).equals(myKey)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if(found){
                                        break;
                                    }
                                }

                                if(!found){
                                    listener.onUserFound(myUser, myKey);
                                    App.screenController.activate("voting_view");
                                    waitAndReset(0);
                                }
                                else {
                                    label.setText("User has already voted.");
                                    waitAndReset(2000);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        };
                        ref.addListenerForSingleValueEvent(eventListener);


                    } else {
                        label.setText("Barcode with assigned user not found.");
                        waitAndReset(2000);

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
            label.setText("Please check your internet connectivity.");
            waitAndReset(2000);
        }

    }

    public static void setUserFoundListener(UserFoundListener listener){
        TextViewController.listener = listener;
    }

    public void waitAndReset(long delay){
        new Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        label.setText("Insert your barcode...");
                    }
                },
                delay
        );
    }

}
