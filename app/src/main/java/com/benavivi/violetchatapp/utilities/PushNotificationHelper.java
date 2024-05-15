package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_FCM_TOKEN;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.NOTIFICATION_SCOPES;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.benavivi.violetchatapp.dataModels.Group;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.annotations.concurrent.Background;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PushNotificationHelper {

// Current Error:
// com.google.auth.oauth2.GoogleAuthException: Error getting access token for service account: 400 Bad Request
// {"error":"invalid_scope","error_description":"Invalid OAuth scope or ID token audience provided."}, iss: firebase-adminsdk-r2mz9@voilet-chatapp.iam.gserviceaccount.com


@Background
public static void sendPushNotification( String message, Group group, Context currentContext ) {

	String currentUserUid = FirebaseManager.getCurrentUserUid( );
	ArrayList<String> groupMembersList = group.getMembersList( );
	groupMembersList.remove(currentUserUid);


	AssetManager assetManager = currentContext.getAssets( );

	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder( ).permitNetwork( ).build( );
	StrictMode.setThreadPolicy(policy);
	new Thread(new Runnable( ) {
		@Override
		public void run( ) {
			try {
				this.sendPushNotification(message, group);
			} catch ( Exception ex ) {
				ex.printStackTrace( );
			}
			//Thread.currentThread( ).interrupt( );
		}

		public void sendPushNotification( String message, Group group ) {


			for ( String memberID : groupMembersList ) {
				FirebaseManager.getUserData(memberID).addOnSuccessListener(documentSnapshot -> {
					String otherUserFCMToken = documentSnapshot.getData( ).get(KEY_USER_FCM_TOKEN).toString( );
					try {
						JSONObject messageJsonObject = CreateJsonObject(otherUserFCMToken, group, message);
						callAPI(messageJsonObject);
					} catch ( JSONException e ) {
						Log.e("Push Notification", "Json object" + e.getMessage( ));
					}
				});

			}
		}

		private JSONObject CreateJsonObject( String otherUserFCMToken, Group group, String message ) throws JSONException {
			JSONObject jsonMessageObject = new JSONObject( );
			JSONObject notificationObject = new JSONObject( );

			notificationObject.put("token", otherUserFCMToken);
			notificationObject.put("notification", new JSONObject( )
				.put("title", group.getName( ))
				.put("body", message));

			notificationObject.put("data", new JSONObject( ).put(NOTIFICATION_EXTRA_SENTFROMID, group.getChatID( )));
			jsonMessageObject.put("message", notificationObject);

			return jsonMessageObject;
		}


		private void callAPI( JSONObject jsonObject ) {
			String authorizationToken, url;
			MediaType JSON = MediaType.get("application/json");
			OkHttpClient client = new OkHttpClient( );

			url = Constants.FirebaseConstants.NOTIFICATION_PUSH_URL;
			RequestBody body = RequestBody.create(jsonObject.toString( ), JSON);

			authorizationToken = getAccessToken( );
			if ( authorizationToken.isEmpty( ) ) {
				return;
			}

			Request request = new Request.Builder( )
				.url(url)
				.post(body)
				.header("Authorization", "Bearer " + authorizationToken)
				.build( );

			client.newCall(request).enqueue(new Callback( ) {
				@Override
				public void onFailure( @NonNull Call call, @NonNull IOException e ) {
					//Failed to send Message
					e.printStackTrace( );
				}

				@Override
				public void onResponse( @NonNull Call call, @NonNull Response response ) throws IOException {
					//Sent Message
					//Log.i("Notification", "onResponse: " + response.code( ) + "\n " + response.message( ) + "\n" + response.body( ).string( ));
				}
			});
		}

		private String getAccessToken( ) {
			try {
				InputStream serviceAccountInputStream = assetManager.open("service_account_key.json");
				GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountInputStream)
					//.createScoped("https://www.googleapis.com/auth/firebase.messaging");
					.createScoped(Arrays.asList(NOTIFICATION_SCOPES));

				credentials.refresh( );
				return credentials.getAccessToken( ).getTokenValue( );


			} catch ( IOException e ) {
				e.printStackTrace( );
				return "";
			}
		}
	}).start( );

}


}
