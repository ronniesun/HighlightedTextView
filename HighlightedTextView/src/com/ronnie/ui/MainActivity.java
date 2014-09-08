package com.ronnie.ui;

import android.app.Activity;
import android.os.Bundle;

import com.ronnie.R;
import com.ronnie.view.HighlightedTextView;

public class MainActivity extends Activity {

	private HighlightedTextView text1;
	private HighlightedTextView text2;
	private HighlightedTextView text3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text1 = (HighlightedTextView) findViewById(R.id.text1);
		text2 = (HighlightedTextView) findViewById(R.id.text2);
		text3 = (HighlightedTextView) findViewById(R.id.text3);

		text1.setText("Behind this sentence with a line, automatic omitted", "with",
				getResources().getColor(R.color.yellow));
		text2.setText(
				"This is a very long sentence, this key will be displayed in the middle position, before and after the ellipsis, long long long long long long long",
				"middle", getResources().getColor(R.color.blue));
		text3.setText("This sentence keywords in the final, so only in front of ellipsis, behind is no ellipses", "no",
				getResources().getColor(R.color.green));
	}

}
