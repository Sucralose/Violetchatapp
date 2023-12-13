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

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

@Override
protected void onCreate ( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_splash);

	if ( getIntent().getExtras() == null )
		regularSplashBehaviour();
	else {
		//Has leftover intent?
		String st = getIntent().getExtras().getString(Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID);
		if ( st != null && !st.isEmpty() ) //Entered from notification
			enteredFromNotification();
		else //bogus weird intent behaviour
			regularSplashBehaviour();
	}
}

void regularSplashBehaviour ( ) {
	new Handler().postDelayed(( ) -> {
		if ( FirebaseManager.isSignedIn() ) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, SignInActivity.class));
		}
		finish();
	}, 1000);
}

void enteredFromNotification ( ) {
	String notificationGroupID = getIntent().getExtras().getString(Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID);
	FirebaseManager.getGroupDetailsData(notificationGroupID)
		.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete ( @NonNull Task<QuerySnapshot> task ) {
				if ( task.isSuccessful() ) {
					Map<String,Object> groupModelData = task.getResult().getDocuments().get(0).getData();
					assert groupModelData != null;
					Group group = new Group(groupModelData);
					Intent goToChatIntent = IntentFactory.GroupToIntent(SplashActivity.this, ConversationActivity.class, group);
					goToChatIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(goToChatIntent);
					finish();
				}
			}
		});
}

}