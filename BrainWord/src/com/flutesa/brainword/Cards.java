/***
 * Created by Burkova Alexandra
 * m-tel.: +7 (916) 448 99 65
 * e-mail: flutesa@ya.ru
 * 2012, Moscow
 ***/

package com.flutesa.brainword;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Cards extends Activity {

	private Cursor mCursor;
	TextView tWord, tTranslate;
	View card;

	private static final String[] mContent = new String[] { WordDbHelper._ID,
			WordDbHelper.WORD, WordDbHelper.TRANSLATE };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.card);

		tWord = (TextView) findViewById(R.id.display_word);
		tTranslate = (TextView) findViewById(R.id.display_translate);

		mCursor = managedQuery(WordProvider.CONTENT_URI, mContent, null, null,
				null);
		mCursor.moveToFirst();
		tWord.setText(mCursor.getString(1));
		tTranslate.setText(mCursor.getString(2));

		tWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCursor.isFirst() == true) {
					mCursor.moveToLast();
					tWord.setText(mCursor.getString(1));
					tTranslate.setText(mCursor.getString(2));
				} else {
					mCursor.moveToPrevious();
					tWord.setText(mCursor.getString(1));
					tTranslate.setText(mCursor.getString(2));
				}
			}
		});

		tTranslate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCursor.moveToNext();
				if (mCursor.isAfterLast() == true) {
					mCursor.moveToFirst();
					tWord.setText(mCursor.getString(1));
					tTranslate.setText(mCursor.getString(2));
				} else {
					tWord.setText(mCursor.getString(1));
					tTranslate.setText(mCursor.getString(2));
				}
			}
		});
	}
}
