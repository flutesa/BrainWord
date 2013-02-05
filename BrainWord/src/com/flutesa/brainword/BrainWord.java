/***
 * Created by Burkova Alexandra
 * m-tel.: +7 (916) 448 99 65
 * e-mail: flutesa@ya.ru
 * 2012, Moscow
 ***/

package com.flutesa.brainword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

public class BrainWord extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		View cardsButton = findViewById(R.id.GoToCards);
		cardsButton.setOnClickListener(this);
		View listButton = findViewById(R.id.GoToListAct);
		listButton.setOnClickListener(this);

		View listviewButton = findViewById(R.id.GoToListView);
		listviewButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.GoToCards:
			Intent i = new Intent(this, Cards.class);
			startActivity(i);
			break;
		case R.id.GoToListView:
			openListToastDialog();
			break;
		case R.id.GoToListAct:
			Intent j = new Intent(this, ListAct.class);
			startActivity(j);
			break;
		}
	}

	private void openListToastDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.title_dialog)
				.setItems(R.array.direction,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								startListToast(i);
							}
						}).show();
	}

	private void startListToast(int i) {
		Intent intent = new Intent(BrainWord.this, ListToast.class);
		intent.putExtra(ListToast.KEY_DIRECTION, i);
		startActivity(intent);
	}
}