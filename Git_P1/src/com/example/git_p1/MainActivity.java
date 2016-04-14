package com.example.git_p1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	private static final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		getSupportActionBar().setTitle("Hello");
		Log.d(tag, "Hello Hi");
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(clickListener);
		button2.setOnClickListener(clickListener);

	}

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			switch (v.getId()) {
			case R.id.button1:
				transaction.replace(R.id.frag_container, new FragmentOne());
				break;
			case R.id.button2:
				transaction.replace(R.id.frag_container, new FragmentTwo());
				break;

			}
			transaction.commit();
		}
	};
}
