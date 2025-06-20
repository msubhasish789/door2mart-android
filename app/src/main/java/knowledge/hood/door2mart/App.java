package knowledge.hood.door2mart;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AppInit", "FirebaseApp initialized");
        FirebaseApp.initializeApp(this);
    }
}
