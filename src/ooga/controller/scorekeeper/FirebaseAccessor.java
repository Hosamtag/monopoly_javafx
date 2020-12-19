package ooga.controller.scorekeeper;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import ooga.controller.MyCallback;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidFirebaseException;

/**
 * this class creates a connection to a firebase realtime databse
 * https://firebase.google.com/docs/admin/setup#prerequisites
 * https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
 */
public class FirebaseAccessor {

  private static final String DATABASE_URL = "https://monopoly-5c899.firebaseio.com";
  private static final String DATABASE_INSTANCE = "https://monopoly-5c899.firebaseio.com/";
  private static final String AUTHORIZATION = "/monopoly-5c899-firebase-adminsdk-483zm-9017ce5db2.json";
  private final FirebaseDatabase firebaseDatabase;
  private DatabaseReference ref;
  private String name;

  /**
   * sets up the connections and credentials for a firebase app
   */
  public FirebaseAccessor() {

    InputStream serviceAccount = this.getClass().getResourceAsStream(AUTHORIZATION);
    try {
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl(DATABASE_URL)
          .build();
      if (FirebaseApp.getApps().size() == 0) {
        FirebaseApp.initializeApp(options);
      }
      firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_INSTANCE);
      ref = firebaseDatabase.getReference();
    } catch (Exception InvalidFirebaseException) {
      throw new InvalidFirebaseException(ResourceUtil.getResourceValue("FirebaseException"));
    }

  }

  /**
   * updates the reference of the root of the database to get a
   * new type of information
   * @param child
   */
  public void updateRefToChild(String child){
    if(!ref.toString().contains(child)) {
      ref = ref.child(child);
    }

  }

  /**
   * updates the value in the database to the value passed in for the key
   * @param value
   * @param key
   * @throws InvalidFirebaseException
   */
  public void update(Object value, String key) throws InvalidFirebaseException {
    try {
      DatabaseReference ref = firebaseDatabase.getReference(key);
      final CountDownLatch latch = new CountDownLatch(1);
      ref.setValue(value, (databaseError, databaseReference) -> latch.countDown());
      latch.await();
    } catch (Exception InvalidFirebaseException) {
      throw new InvalidFirebaseException(ResourceUtil.getResourceValue("FirebaseException"));
    }
  }

  /**
   * reads the value associated with the parent value key
   * @param myCallback
   * @param parentValue
   * @throws InvalidFirebaseException
   */
  public void readData(MyCallback myCallback,String parentValue) throws InvalidFirebaseException {
    ref.child(parentValue).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        name = dataSnapshot.getValue().toString();
        try {
          myCallback.onCallback(name);
        } catch (Exception InvalidFirebaseException) {
          throw new InvalidFirebaseException(ResourceUtil.getResourceValue("FirebaseException"));
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        throw new InvalidFirebaseException(ResourceUtil.getResourceValue("FirebaseException"));
      }
    });
  }
}