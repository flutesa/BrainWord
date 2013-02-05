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
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListToast extends Activity {
	
	public static final String KEY_DIRECTION = "com.flutesa.brainword.direction";
	public static final int DIRECTION_WT = 0;
	public static final int DIRECTION_TW = 1;
	
    private Cursor mCursor;
    private ListAdapter mAdapter;
    
    private static final String[] mContent = new String[] { WordDbHelper._ID, WordDbHelper.WORD, WordDbHelper.TRANSLATE };  
       
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	int direct = getIntent().getIntExtra(KEY_DIRECTION, DIRECTION_WT);
	
	setContentView(R.layout.toastmain);
	
    ListView lvTMainw = (ListView) findViewById(R.id.toastmainw);
    ListView lvTMaint = (ListView) findViewById(R.id.toastmaint);
    
    switch (direct) {
    case DIRECTION_WT:
    default:
        mCursor = managedQuery(WordProvider.CONTENT_URI, mContent, null, null, null);
        mAdapter = new SimpleCursorAdapter(this, R.layout.toast, mCursor, 
                new String[] {WordDbHelper.WORD}, 
                new int[] {R.id.textinlist1});
    	lvTMainw.setAdapter(mAdapter);
       break;
    case DIRECTION_TW:
        mCursor = managedQuery(WordProvider.CONTENT_URI, mContent, null, null, "0");
        mAdapter = new SimpleCursorAdapter(this, R.layout.toast, mCursor, 
                new String[] {WordDbHelper.TRANSLATE}, 
                new int[] {R.id.textinlist1});
    	lvTMaint.setAdapter(mAdapter);
       break;
    }
    
   
	lvTMainw.setOnItemLongClickListener(new OnItemLongClickListener() {   	
    	@Override
    	public boolean onItemLongClick(AdapterView<?> parent, View v, int position, final long id) {          
    		CallDeleteWordDialog(id);
        	return true;
    	}
    });

	
	lvTMainw.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> mAdapterWord, View itemClicked, int position, final long id) {
			ShowTranslate(id, position);   			
		}
	});
	
	lvTMaint.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> mAdapterTranslate, View itemClicked, int position, final long id) {
			ShowWord(id, position);   			
		}
	});	
}
	   
	   	   
	private void ShowWord(final long rowId, int position) {	
        final TextView textWord = (TextView)findViewById(R.id.toasttext);                 
        mCursor.moveToPosition(position); 
        textWord.setText(mCursor.getString(1));  
	}
	
	private void ShowTranslate(final long rowId, int position) {	                   
        final TextView textTranslate = (TextView)findViewById(R.id.toasttext);  
        mCursor.moveToPosition(position); 
        textTranslate.setText(mCursor.getString(2));   
	}
	
    private void CallDeleteWordDialog(final long rowId) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        
        b.setIcon(R.drawable.d_del);
        b.setTitle(R.string.title_delete);
        
        b.setPositiveButton(
                R.string.btn_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                getContentResolver().delete(WordProvider.CONTENT_URI, "_ID=" + rowId, null);
                mCursor.requery();
            }
        });
        
        b.setNegativeButton(
                R.string.btn_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        
        b.show();
   }
}