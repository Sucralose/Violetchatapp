package com.benavivi.violetchatapp.activities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_DISPLAY_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_EMAIL_ADDRESS;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_PROFILE_IMAGE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.ActivityChatSettingsBinding;
import com.benavivi.violetchatapp.databinding.LayoutGroupItemBinding;
import com.benavivi.violetchatapp.utilities.Constants;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentFactory;
import com.squareup.picasso.Picasso;

public class ChatSettingsActivity extends AppCompatActivity {
        ActivityChatSettingsBinding binding;
        Group currentGroup;
        private Uri newChatImageUri;

        @Override
        protected void onCreate( Bundle savedInstanceState ) {
                super.onCreate(savedInstanceState);
                binding = ActivityChatSettingsBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                currentGroup = IntentFactory.IntentToGroup(getIntent());

                setupPanel();
                setListeners();

        }

        private void setListeners( ) {
                binding.chatSettingsBackButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick ( View view ) {
                                Intent intent = IntentFactory.GroupToIntent(ChatSettingsActivity.this,ConversationActivity.class,currentGroup);
                                startActivity(intent);
                                finish();
                        }
                });

                binding.settingsChatLeaveGroup.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick( View v ) {
                                FirebaseManager.removeUserFromGroup(currentGroup,FirebaseManager.getCurrentUserUid());
                                Intent intent = new Intent(ChatSettingsActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                        }
                });

                binding.settingsAddUser.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick( View v ) {
                                ChatSettingsActivity.this.openAddUserAlertDialog();
                        }
                });

                binding.settingsRemoveUser.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick( View v ) {
                                removeUserAlertDialog();

                        }
                });

                binding.settingsChangeChatName.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick( View v ) {
                                openChangeChatNameAlertDialog();
                        }
                });

                binding.settingsChangeChatIcon.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick( View v ) {
                                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                pickImage.launch(pickImageIntent);
                        }
                });
        }

        private void openChangeChatNameAlertDialog( ) {
                AlertDialog.Builder ChangeNameDialogBuilder = new AlertDialog.Builder(this);
                ChangeNameDialogBuilder.setTitle("Change Chat Name");

                final EditText changeNameEditText = new EditText(this);
                changeNameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                ChangeNameDialogBuilder.setView(changeNameEditText);
                ChangeNameDialogBuilder.setCancelable(true);
                ChangeNameDialogBuilder.setNegativeButton("Cancel", ( dialog, which ) -> dialog.dismiss());
                ChangeNameDialogBuilder.setPositiveButton("Change", ( dialog, which ) -> {
                        if(changeNameEditText.getText().length() > Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH){
                                String newName = changeNameEditText.getText().toString();
                                FirebaseManager.setGroupName(currentGroup,newName);
                                binding.settingsChangeChatName.setText(newName);
                                showShortToast("Groupchat name Changed");
                        }
                        else
                                showShortToast("groupchat name must be at least"+Constants.UserConstants.MIN_DISPLAY_NAME_LENGTH+ " characters long");
                });

                ChangeNameDialogBuilder.show();
        }

        private void openAddUserAlertDialog( ) {
                AlertDialog.Builder addUserDialogBuilder = new AlertDialog.Builder(this);
                addUserDialogBuilder.setTitle("Who would you like to add?");

                final EditText addUserEditText = new EditText(this);
                addUserEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                addUserDialogBuilder.setView(addUserEditText);
                addUserDialogBuilder.setCancelable(true);
                addUserDialogBuilder.setNegativeButton("Cancel", ( dialog, which ) -> dialog.dismiss());
                addUserDialogBuilder.setPositiveButton("Add", ( dialog, which ) -> {
                       ChatSettingsActivity.this.callAddUser(addUserEditText.getText().toString());

                });

                addUserDialogBuilder.show();
        }

        private void removeUserAlertDialog(){
                AlertDialog.Builder removeUserDialogBuilder = new AlertDialog.Builder(this);
                removeUserDialogBuilder.setTitle("Who would you like to remove?");

                final EditText removeUserEditText = new EditText(this);
                removeUserEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                removeUserDialogBuilder.setView(removeUserEditText);
                removeUserDialogBuilder.setCancelable(true);
                removeUserDialogBuilder.setNegativeButton("Cancel", ( dialog, which ) -> dialog.dismiss());
                removeUserDialogBuilder.setPositiveButton("Remove", ( dialog, which ) -> {
                        String userToRemove = removeUserEditText.getText( ).toString( );
                        ChatSettingsActivity.this.callRemoveUser(userToRemove);
                });

                removeUserDialogBuilder.show();
        }

        private void callAddUser( String userEmail ) {
                FirebaseManager.doesUserExist(userEmail)
                        .addOnSuccessListener(documentSnapshot -> {
                                if ( documentSnapshot.isEmpty() || currentGroup.getMembersList().contains(documentSnapshot.getDocuments().get(0).getId()) )
                                        showShortToast("User does not exist or is already in the groupchat.");
                                else {
                                        String toAddUserID = documentSnapshot.getDocuments().get(0).getId();
                                        FirebaseManager.addUserToGroup(currentGroup, toAddUserID);
                                        showShortToast("User Added");
                                        updateMembersListScrollView();
                                }
                        });
        }

        private void callRemoveUser( String userEmail ) {
                if( userEmail.equals( FirebaseManager.getUserEmail() ) ){
                        showShortToast("You cannot remove yourself from the group.");
                        return;
                }

                FirebaseManager.doesUserExist(userEmail)
                        .addOnSuccessListener(documentSnapshot -> {

                                if ( documentSnapshot.isEmpty() || !currentGroup.getMembersList().contains(documentSnapshot.getDocuments().get(0).getId()) )
                                        showShortToast("User does not exist or is not in the groupchat.");
                                else {
                                        String toRemoveUserID = documentSnapshot.getDocuments().get(0).getId();
                                        FirebaseManager.removeUserFromGroup(currentGroup, toRemoveUserID);
                                        showShortToast("User Removed");
                                        updateMembersListScrollView();
                                }
                        });
        }



        private void setupPanel( ) {
                binding.conversationTitle.setText(currentGroup.getName());

                if ( currentGroup.getImageURL() != null && !currentGroup.getImageURL().isEmpty() )
                        Picasso.get().load(currentGroup.getImageURL())
                                .into(binding.SettingsChatImage);

                if( FirebaseManager.getCurrentUserUid().equals( currentGroup.getAdminID()) )
                        binding.chatSettingAdminPanel.setVisibility(View.VISIBLE);
                else
                        binding.chatSettingAdminPanel.setVisibility(View.GONE);

                updateMembersListScrollView();
        }

        private void updateMembersListScrollView( ) {
                binding.chatSettingsMembersListScrollView.scrollTo(0, 0);
                binding.chatSettingsMembersListContainer.removeAllViews();

                for ( String member : currentGroup.getMembersList() )
                        addMemberToScrollView(member);
        }

        private void addMemberToScrollView( String member ) {
                LayoutGroupItemBinding memberViewBinding = LayoutGroupItemBinding.inflate(getLayoutInflater());
                FirebaseManager.getUserData(member)
                                .addOnSuccessListener(documentSnapshot -> {
                                        memberViewBinding.layoutgroupGroupName.setText(documentSnapshot.get(KEY_USER_DISPLAY_NAME).toString());
                                        memberViewBinding.layoutgroupLastMessage.setText(documentSnapshot.get(KEY_USER_EMAIL_ADDRESS).toString());
                                        memberViewBinding.layoutgroupLastMessageTime.setText("");
                                        String imageURL = documentSnapshot.get(KEY_USER_PROFILE_IMAGE).toString();
                                        if ( !imageURL.isEmpty() )
                                                Picasso.get().load(imageURL).into(memberViewBinding.layoutgroupGroupImage);

                                        binding.chatSettingsMembersListContainer.addView(memberViewBinding.getRoot());
                                });
        }

        private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                        if ( result.getData() != null ) {
                                newChatImageUri = result.getData().getData();
                                binding.SettingsChatImage.setImageURI(newChatImageUri);
                                FirebaseManager.changeGroupImage(currentGroup, newChatImageUri);
                        }
                }
        );

        private void showShortToast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

}
