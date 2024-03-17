package com.benavivi.violetchatapp.utilities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_FCM_TOKEN;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.NOTIFICATION_EXTRA_SENTFROMID;

import android.util.Log;

import androidx.annotation.NonNull;

import com.benavivi.violetchatapp.dataModels.Group;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PushNotificationHelper {


public static void sendPushNotification( String message, Group group ) {
	String currentUserUid = FirebaseManager.getCurrentUserUid( );
	ArrayList<String> groupMembersList = group.getMembersList( );
	groupMembersList.remove(currentUserUid);

	for ( String memberID : groupMembersList ) {
		Log.i("Push Notification", memberID);
		FirebaseManager.getUserData(memberID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>( ) {
			@Override
			public void onSuccess( DocumentSnapshot documentSnapshot ) {
				String otherUserFCMToken = documentSnapshot.getData( ).get(KEY_USER_FCM_TOKEN).toString( );
				try {
					JSONObject messageJsonObject = CreateJsonObject(otherUserFCMToken, group, message);
					callAPI(messageJsonObject);
				} catch ( JSONException e ) {
					Log.e("Push Notification", "Json object" + e.getMessage( ));
				}
			}
		});

	}

	//currentUsername , Chat ID , Message, otherUserToken
}

private static JSONObject CreateJsonObject( String otherUserFCMToken, Group group, String message ) throws JSONException {
	JSONObject jsonObject = new JSONObject( );

	JSONObject notificationObject = new JSONObject( );
	notificationObject.put("title", group.getName( ));
	notificationObject.put("body", message);

	JSONObject extraDataObject = new JSONObject( );
	extraDataObject.put(NOTIFICATION_EXTRA_SENTFROMID, group.getChatID( ));

	jsonObject.put("notification", notificationObject);
	jsonObject.put("data", extraDataObject);
	jsonObject.put("to", otherUserFCMToken);

	return jsonObject;
}

/*private String post ( String url, String json ) throws IOException {
	RequestBody body = RequestBody.create(json, JSON);
	Request request = new Request.Builder()
		                  .url(url)
		                  .post(body)
		                  .build();
	try ( Response response = client.newCall(request).execute() ) {
		return response.body().string();
	}
}*/

private static void callAPI( JSONObject jsonObject ) {
	MediaType JSON = MediaType.get("application/json");
	OkHttpClient client = new OkHttpClient( );
	String url = Constants.FirebaseConstants.NOTIFICATION_PUSH_URL;
	RequestBody body = RequestBody.create(jsonObject.toString( ), JSON);
	Request request = new Request.Builder( )
		.url(url)
		.post(body)
		.header("Authorization", "Bearer " + Constants.FirebaseConstants.NOTIFICATION_API_KEY)
		.build( );

	client.newCall(request).enqueue(new Callback( ) {
		@Override
		public void onFailure( @NonNull Call call, @NonNull IOException e ) {
			//Failed to send Message
		}

		@Override
		public void onResponse( @NonNull Call call, @NonNull Response response ) throws IOException {
			//Sent Message
		}
	});
}
}
