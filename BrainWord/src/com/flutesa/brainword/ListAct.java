/***
 * Created by Burkova Alexandra
 * m-tel.: +7 (916) 448 99 65
 * e-mail: flutesa@ya.ru
 * 2012, Moscow
 ***/

package com.flutesa.brainword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListAct extends Activity {

	private Cursor mCursor;
	private ListAdapter mAdapter;

	private static final String[] mContent = new String[] { WordDbHelper._ID,
			WordDbHelper.WORD, WordDbHelper.TRANSLATE };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.listmain);

		Button btNew = (Button) findViewById(R.id.GoToNewWord);
		ListView lvLMain = (ListView) findViewById(R.id.listmain);

		mCursor = managedQuery(WordProvider.CONTENT_URI, mContent, null, null,
				null);

		mAdapter = new SimpleCursorAdapter(this, R.layout.list, mCursor,
				new String[] { WordDbHelper.WORD, WordDbHelper.TRANSLATE },
				new int[] { R.id.word, R.id.translate });
		lvLMain.setAdapter(mAdapter);

		btNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CallAddWordDialog();
			}
		});

		lvLMain.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, final long id) {
				CallDeleteWordDialog(id);
				return true;
			}
		});

		lvLMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View itemClicked,
					int position, final long id) {
				CallEditWordDialog(id, position);
			}
		});

	}

	private void CallAddWordDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View root = inflater.inflate(R.layout.dialog, null);

		final EditText textWord = (EditText) root.findViewById(R.id.word);
		final EditText textTranslate = (EditText) root
				.findViewById(R.id.translate);

		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setView(root);
		b.setIcon(R.drawable.d_add);
		b.setTitle(R.string.title_add);
		b.setPositiveButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ContentValues values = new ContentValues(2);

						values.put(WordDbHelper.WORD, textWord.getText()
								.toString());
						values.put(WordDbHelper.TRANSLATE, textTranslate
								.getText().toString());

						getContentResolver().insert(WordProvider.CONTENT_URI,
								values);
						mCursor.requery();
					}
				});
		b.setNegativeButton(R.string.btn_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		b.show();
	}

	private void CallEditWordDialog(final long rowId, int position) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View root = inflater.inflate(R.layout.dialog, null);

		final EditText textWord = (EditText) root.findViewById(R.id.word);
		final EditText textTranslate = (EditText) root
				.findViewById(R.id.translate);

		mCursor.moveToPosition(position);

		textWord.setText(mCursor.getString(1));
		textTranslate.setText(mCursor.getString(2));

		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setView(root);
		b.setIcon(R.drawable.d_edit);
		b.setTitle(R.string.title_edit);

		b.setPositiveButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ContentValues values = new ContentValues(2);
						values.put(WordDbHelper.WORD, textWord.getText()
								.toString());
						values.put(WordDbHelper.TRANSLATE, textTranslate
								.getText().toString());
						getContentResolver().update(WordProvider.CONTENT_URI,
								values, "_ID=" + rowId, null);
						mCursor.requery();
					}
				});
		b.setNegativeButton(R.string.btn_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		b.show();
	}

	private void CallDeleteWordDialog(final long rowId) {
		AlertDialog.Builder b = new AlertDialog.Builder(this);

		b.setIcon(R.drawable.d_del);
		b.setTitle(R.string.title_delete);
		b.setPositiveButton(R.string.btn_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						getContentResolver().delete(WordProvider.CONTENT_URI,
								"_ID=" + rowId, null);
						mCursor.requery();
					}
				});

		b.setNegativeButton(R.string.btn_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		b.show();
	}
}