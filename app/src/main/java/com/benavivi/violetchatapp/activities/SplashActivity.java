package com.benavivi.violetchatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

@Override
protected void onCreate( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_splash);

	if ( isValidNotificationIntent( ) )
		MoveUserToNotificationChat( );
	else {
		MoveUserDependingOnAuth( );
	}

}

void MoveUserDependingOnAuth( ) {
	new Handler( ).postDelayed(( ) -> {
		if ( FirebaseManager.isSignedIn( ) ) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, SignInActivity.class));
		}
		finish( );
	}, Constants.ApplicationConstants.SPLASH_DELAY_MILLISECONDS);
}

void MoveUserToNotificationChat( ) {
	String notificationGroupID = getIntent( ).getExtras( ).getString(Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID);
	FirebaseManager.getGroupDetailsData(notificationGroupID)
		.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>( ) {
			@Override
			public void onComplete( @NonNull Task<QuerySnapshot> task ) {
				if ( task.isSuccessful( ) ) {
					Group group = IntentFactory.IntentMapToGroup(Objects.requireNonNull(task.getResult( ).getDocuments( ).get(0).getData( )));
					Intent goToChatIntent = IntentFactory.GroupToIntent(SplashActivity.this, ConversationActivity.class, group);
					goToChatIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(goToChatIntent);
					finish( );
				}
			}
		});
}

boolean isValidNotificationIntent( ) {
	return getIntent( ).getExtras( ) != null
		&& getIntent( ).getExtras( ).getString(Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID) != null
		&& !getIntent( ).getExtras( ).getString(Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID).isEmpty( );
}

}