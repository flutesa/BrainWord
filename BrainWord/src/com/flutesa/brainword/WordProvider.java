/***
 * Created by Burkova Alexandra
 * m-tel.: +7 (916) 448 99 65
 * e-mail: flutesa@ya.ru
 * 2012, Moscow
 ***/

package com.flutesa.brainword;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class WordProvider extends ContentProvider {

	public static final String DB_WORDS = "words.db";

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.flutesa.brainword.wordprovider/word");
	public static final int URI_CODE = 1;
	public static final int URI_CODE_ID = 2;

	private static final UriMatcher mUriMatcher;
	private static HashMap<String, String> mContactMap;

	private SQLiteDatabase db;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI("com.flutesa.brainword.wordprovider",
				WordDbHelper.TABLE_NAME, URI_CODE);
		mUriMatcher.addURI("com.flutesa.brainword.wordrovider",
				WordDbHelper.TABLE_NAME + "/#", URI_CODE_ID);

		mContactMap = new HashMap<String, String>();
		mContactMap.put(WordDbHelper._ID, WordDbHelper._ID);
		mContactMap.put(WordDbHelper.WORD, WordDbHelper.WORD);
		mContactMap.put(WordDbHelper.TRANSLATE, WordDbHelper.TRANSLATE);
	}

	public String getDbName() {
		return (DB_WORDS);
	}

	@Override
	public boolean onCreate() {
		db = (new WordDbHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = WordDbHelper.WORD;
		} else {
			orderBy = WordDbHelper.TRANSLATE;
		}

		Cursor c = db.query(WordDbHelper.TABLE_NAME, projection, selection,
				selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	@Override
	public Uri insert(Uri url, ContentValues inValues) {
		ContentValues values = new ContentValues(inValues);

		long rowId = db.insert(WordDbHelper.TABLE_NAME, WordDbHelper.WORD, values);
		if (rowId > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		} else {
			throw new SQLException("Failed to insert row into " + url);
		}
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int retVal = db.delete(WordDbHelper.TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int retVal = db.update(WordDbHelper.TABLE_NAME, values, where,
				whereArgs);

		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
}