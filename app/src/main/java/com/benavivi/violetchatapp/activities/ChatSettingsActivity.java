package com.benavivi.violetchatapp.activities;

import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_DISPLAY_NAME;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_EMAIL_ADDRESS;
import static com.benavivi.violetchatapp.utilities.Constants.FirebaseConstants.KEY_USER_PROFILE_IMAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.benavivi.violetchatapp.dataModels.Group;
import com.benavivi.violetchatapp.databinding.ActivityChatSettingsBinding;
import com.benavivi.violetchatapp.databinding.LayoutGroupItemBinding;
import com.benavivi.violetchatapp.utilities.FirebaseManager;
import com.benavivi.violetchatapp.utilities.IntentFactory;
import com.squareup.picasso.Picasso;

public class ChatSettingsActivity extends AppCompatActivity {
        ActivityChatSettingsBinding binding;
        Group currentGroup;
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
                        String userToAdd = addUserEditText.getText( ).toString( );
                        FirebaseManager.doesUserExist(userToAdd)
                                        .addOnSuccessListener(documentSnapshot -> {

                                                if ( documentSnapshot.isEmpty() || currentGroup.getMembersList().contains(documentSnapshot.getDocuments().get(0).getId()) )
                                                        showShortToast("User does not exist or is already in the groupchat.");
                                                else {
                                                        String toAddUserID = documentSnapshot.getDocuments().get(0).getId();
                                                        FirebaseManager.addUserToGroup(currentGroup, toAddUserID);
                                                        showShortToast("User Added");
                                                }
                                        });

                });

                addUserDialogBuilder.show();
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

        private void showShortToast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

}
