package com.benavivi.violetchatapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.benavivi.violetchatapp.databinding.ActivityMainBinding;
import com.benavivi.violetchatapp.Fragments.ChatsFragment;
import com.benavivi.violetchatapp.Fragments.SettingsFragment;
import com.benavivi.violetchatapp.Utilities.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private MyViewPagerAdapter myAdapter;
	@Override
	protected void onStart () {
		super.onStart();
	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		/*setListeners();*/
		CreateViewPagerAdapter();
		BindViewPager();
		CreateTabLayoutMediator();
	}

	private void CreateTabLayoutMediator () {
		new TabLayoutMediator(
			binding.tabLayout,
			binding.viewPager,
			new TabLayoutMediator.TabConfigurationStrategy() {
				@Override
				public void onConfigureTab (@NonNull TabLayout.Tab tab, int position) {
					switch (position){
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

	private void BindViewPager () {
		binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
		binding.viewPager.setAdapter(myAdapter);

	}

	private void CreateViewPagerAdapter () {
		myAdapter = new MyViewPagerAdapter(
			getSupportFragmentManager(),
			getLifecycle());


		myAdapter.addFragment(new ChatsFragment());
		myAdapter.addFragment(new SettingsFragment());
	}

	/*private void setListeners () {
		binding.mainBottomNavigationView.setOnItemSelectedListener(item ->{return bottomNavigationViewHandler(item);});

	}

	private boolean bottomNavigationViewHandler(MenuItem item){
		//Switch requires constant values, therefore I have to use if else.
		if(item.getItemId() == R.id.chatsNavbarMenuItem)
			replaceFragments(new ChatsFragment());

		if(item.getItemId() == R.id.groupsNavbarMenuItem)
			replaceFragments(new GroupsFragment());

		if(item.getItemId() == R.id.contactsNavbarMenuItem)
			replaceFragments(new ContactsFragment());

		if(item.getItemId() == R.id.requestsNavbarMenuItem)
			replaceFragments(new RequestsFragment());

		if(item.getItemId() == R.id.settingsNavbarMenuItem)
			replaceFragments(new SettingsFragment());

		return true;
	}

	private void replaceFragments(Fragment fragment){
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.mainFragmentViewLayout,fragment);
		fragmentTransaction.commit();
	}
*/
}