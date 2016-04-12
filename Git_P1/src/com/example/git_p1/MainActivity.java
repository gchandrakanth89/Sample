package com.example.git_p1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(tag, "Hello Hi");
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(clickListener);
		button2.setOnClickListener(clickListener);

	}

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			FragmentManager fragmentManager = getFragmentManager();
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
