package gces;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseEngine {
    static FirebaseDatabase database;

    FirebaseEngine() {
    }

    public static void initialize(){
        try {
            FileInputStream serviceAccount = new FileInputStream("gcesitexpo-firebase-adminsdk-61f7n-c33a170f7c.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://gcesitexpo.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

            database = FirebaseDatabase.getInstance();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
