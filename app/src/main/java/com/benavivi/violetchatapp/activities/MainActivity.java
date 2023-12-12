package com.benavivi.violetchatapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.benavivi.violetchatapp.R;
import com.benavivi.violetchatapp.adapters.MyViewPagerAdapter;
import com.benavivi.violetchatapp.databinding.ActivityMainBinding;
import com.benavivi.violetchatapp.fragments.ChatsFragment;
import com.benavivi.violetchatapp.fragments.SettingsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
private MyViewPagerAdapter myAdapter;

@Override
protected void onStart ( ) {
	super.onStart();
}


@Override
protected void onCreate ( Bundle savedInstanceState ) {
	super.onCreate(savedInstanceState);
	binding = ActivityMainBinding.inflate(getLayoutInflater());
	setContentView(binding.getRoot());

	CreateViewPagerAdapter();
	BindViewPager();
	CreateTabLayoutMediator();
	setListeners();
}

private void CreateTabLayoutMediator ( ) {
	new TabLayoutMediator(
		binding.tabLayout,
		binding.viewPager,
		new TabLayoutMediator.TabConfigurationStrategy() {
			@Override
			public void onConfigureTab ( @NonNull TabLayout.Tab tab, int position ) {
				switch ( position ) {
					case 0:
						tab.setText("Chats");
						break;
					case 1:
						tab.setText("Settings");
						break;
				}
			}
		}
	).attach();
}

private void BindViewPager ( ) {
	binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
	binding.viewPager.setAdapter(myAdapter);

}

private void CreateViewPagerAdapter ( ) {
	myAdapter = new MyViewPagerAdapter(
		getSupportFragmentManager(),
		getLifecycle());


	myAdapter.addFragment(new ChatsFragment());
	myAdapter.addFragment(new SettingsFragment());
}


private void showShortToast ( String message ) {
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
}

private void setListeners ( ) {
	binding.mainActivityMenu.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick ( View view ) {
			PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
			popupMenu.setOnMenuItemClickListener(MainActivity.this::onMenuItemClick);
			popupMenu.inflate(R.menu.main_activity_menu);
			popupMenu.show();
		}
	});

}

public boolean onMenuItemClick ( MenuItem item ) {
	if ( item.getItemId() == R.id.createNewGroupOption ) {
		showShortToast("New Group");
		return true;
	}
	if ( item.getItemId() == R.id.aboutOption ) {
		showShortToast("About");
		return true;
	}
	return false;
}

}