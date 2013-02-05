/***
 * Created by Burkova Alexandra
 * m-tel.: +7 (916) 448 99 65
 * e-mail: flutesa@ya.ru
 * 2012, Moscow
 ***/

package com.flutesa.brainword;

import com.flutesa.brainword.WordProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class WordDbHelper extends SQLiteOpenHelper implements BaseColumns {

	public static final String TABLE_NAME = "words";
	public static final String WORD = "word";
	public static final String TRANSLATE = "translate";

	public WordDbHelper(Context context) {
		super(context, WordProvider.DB_WORDS, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD
				+ " TEXT, " + TRANSLATE + " TEXT);");

		ContentValues values = new ContentValues();

		values.put(WORD, "chalk");
		values.put(TRANSLATE, "мел");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "ruler");
		values.put(TRANSLATE, "линейка");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "scissors");
		values.put(TRANSLATE, "ножницы");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "equation");
		values.put(TRANSLATE, "уравнение");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "angle");
		values.put(TRANSLATE, "угол");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "infinity");
		values.put(TRANSLATE, "бесконечность");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "inequality");
		values.put(TRANSLATE, "неравенство");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "attendance");
		values.put(TRANSLATE, "посещаемость");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "sophomore");
		values.put(TRANSLATE, "второкурсник");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "faculty");
		values.put(TRANSLATE, "факультет");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "dean");
		values.put(TRANSLATE, "декан");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "term");
		values.put(TRANSLATE, "семестр");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "translation");
		values.put(TRANSLATE, "перевод");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "handbook");
		values.put(TRANSLATE, "руководство");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "cover");
		values.put(TRANSLATE, "обложка");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "snowflake");
		values.put(TRANSLATE, "снежинка");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "wrist");
		values.put(TRANSLATE, "запястье");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "braces");
		values.put(TRANSLATE, "фигурные скобки");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "binding");
		values.put(TRANSLATE, "переплёт");
		db.insert(TABLE_NAME, WORD, values);

		values.put(WORD, "pamphlet");
		values.put(TRANSLATE, "брошюра");
		db.insert(TABLE_NAME, WORD, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}