package ubay.id;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created              : Rahman on 9/6/2016.
 * Date Created         : 9/6/2016 / 3:39 PM.
 * ===================================================
 * Package              : ubay.id.
 * Project Name         : Graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class FirebaseUtil {
    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid ();
        }
        return null;
    }

    public static String getCurrentUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getDisplayName ();
        }
        return null;
    }
}
