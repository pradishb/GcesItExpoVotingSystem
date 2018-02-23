package gces;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author pradish
 */
public class FXMLDocumentController implements Initializable, BarcodeListener{

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
            App.screenController.activate("voting_view");

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
//                    text.setText("Barcode found.\nKey : " + key + "\nName : " + name);
                        ;
                    } else {
//                    text.setText("Barcode not found.");
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
        }

    }

}
