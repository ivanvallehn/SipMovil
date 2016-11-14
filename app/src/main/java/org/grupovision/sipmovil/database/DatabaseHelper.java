package org.grupovision.sipmovil.database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="SIPmovil.db";
    private static final Integer VER_1=1;
    private static final Integer DATABASE_VERSION= VER_1;

    private Context context;

    public DatabaseHelper(Context context) {
        // El factory se asigna null por que no se tiene un cursor definido
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE Cases (" +
                "   caseId INTEGER NOT NULL CONSTRAINT Cases_pk PRIMARY KEY AUTOINCREMENT," +
                "   identificationNumber TEXT(15) NOT NULL," +
                "   firstName TEXT(250)," +
                "   lastName TEXT(250)," +
                "   date text," +
                "   pathPhoto TEXT," +
                "   pathLeftFinger TEXT," +
                "   pathRightFinger TEXT" +
                "   isProcessed INTEGER," +
                "   isHit INTEGER," +
                "   candidateList TEXT" +
                ");");

    }

    //i = oldVersion, i1=newVersion
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i!=i1){
        }
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);
            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {
                alc.set(0,c);
                c.moveToFirst();
                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
