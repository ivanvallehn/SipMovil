package org.grupovision.sipmovil.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.grupovision.sipmovil.database.DatabaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ivalle on 07/11/2016.
 */

public class Case implements Serializable {

    public static final String TABLE_NAME="Cases";

    public static final String CASE_ID="caseId";
    public static final String IDENTIFICATION_NUMBER="identificationNumber";
    public static final String FIRST_NAME="firstName";
    public static final String LAST_NAME="lastName";
    public static final String DATE="date";
    public static final String PATH_PHOTO="pathPhoto";
    public static final String PATH_LEFT_FINGER="pathLeftFinger";
    public static final String PATH_RIGHT_FINGER="pathRightFinger";
    public static final String IS_PROCESSED="isProcessed";
    public static final String IS_HIT="isHit";
    public static final String CANDIDATE_LIST="candidateList";

    private Integer caseId;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String date;
    private String pathPhoto;
    private String pathLeftFinger;
    private String pathRightFinger;
    private Integer isProcessed;
    private Integer isHit;
    private String candidateList;

    public Case(Integer caseId, String identificationNumber, String firstName, String lastName, String date, String pathPhoto, String pathLeftFinger, String pathRightFinger, Integer isProcessed, Integer isHit, String candidateList) {
        this.caseId = caseId;
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.pathPhoto = pathPhoto;
        this.pathLeftFinger = pathLeftFinger;
        this.pathRightFinger = pathRightFinger;
        this.isProcessed = isProcessed;
        this.isHit = isHit;
        this.candidateList = candidateList;
    }

    public Case(String identificationNumber, String firstName, String lastName, String date, String pathPhoto, String pathLeftFinger, String pathRightFinger, Integer isProcessed, Integer isHit, String candidateList) {
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.pathPhoto = pathPhoto;
        this.pathLeftFinger = pathLeftFinger;
        this.pathRightFinger = pathRightFinger;
        this.isProcessed = isProcessed;
        this.isHit = isHit;
        this.candidateList = candidateList;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public String getPathLeftFinger() {
        return pathLeftFinger;
    }

    public void setPathLeftFinger(String pathLeftFinger) {
        this.pathLeftFinger = pathLeftFinger;
    }

    public String getPathRightFinger() {
        return pathRightFinger;
    }

    public void setPathRightFinger(String pathRightFinger) {
        this.pathRightFinger = pathRightFinger;
    }

    public Integer getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Integer isProcessed) {
        this.isProcessed = isProcessed;
    }

    public Integer getIsHit() {
        return isHit;
    }

    public void setIsHit(Integer isHit) {
        this.isHit = isHit;
    }

    public String getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(String candidateList) {
        this.candidateList = candidateList;
    }



    public static long insert(Context context, Case _case){
        ContentValues cv = new ContentValues();
        cv.put(IDENTIFICATION_NUMBER,_case.getIdentificationNumber());
        cv.put(FIRST_NAME,_case.getFirstName());
        cv.put(LAST_NAME,_case.getLastName());
        cv.put(DATE,_case.getDate());
        cv.put(PATH_PHOTO,_case.getPathPhoto());
        cv.put(PATH_LEFT_FINGER,_case.getPathLeftFinger());
        cv.put(PATH_RIGHT_FINGER,_case.getPathRightFinger());
        cv.put(IS_PROCESSED,_case.getIsProcessed());
        cv.put(IS_HIT,_case.getIsHit());
        cv.put(CANDIDATE_LIST,_case.getCandidateList());

        return DatabaseAdapter.getDB(context).insert(TABLE_NAME, null, cv );
    }

    public static ArrayList<Case> getCases(Context context){
        ArrayList<Case> cases = new ArrayList<>();
        Cursor cursor = DatabaseAdapter.getDB(context).query(TABLE_NAME,null,null,null,null,null,null);
        if (cursor!=null){
            for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToNext()){

                Integer caseId = cursor.getInt(cursor.getColumnIndexOrThrow(CASE_ID));
                String identificationNumber= cursor.getString(cursor.getColumnIndexOrThrow(IDENTIFICATION_NUMBER));
                String firstName= cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME));
                String lastName= cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME));
                String date= cursor.getString(cursor.getColumnIndexOrThrow(DATE));
                String pathPhoto= cursor.getString(cursor.getColumnIndexOrThrow(PATH_PHOTO));
                String pathLeftFinger= cursor.getString(cursor.getColumnIndexOrThrow(PATH_LEFT_FINGER));
                String pathRightFinger= cursor.getString(cursor.getColumnIndexOrThrow(PATH_RIGHT_FINGER));
                Integer isProcessed= cursor.getInt(cursor.getColumnIndexOrThrow(IS_PROCESSED));
                Integer isHit= cursor.getInt(cursor.getColumnIndexOrThrow(IS_HIT));
                String candidateList= cursor.getString(cursor.getColumnIndexOrThrow(CANDIDATE_LIST));

                cases.add(new Case(caseId,identificationNumber,firstName,lastName,date,pathPhoto,pathLeftFinger,pathRightFinger,isProcessed,isHit,candidateList));
            }
            cursor.close();
        }
        return  cases;
    }

    public static int deleteCaseById(Context context, int caseId){
        int i = DatabaseAdapter.getDB(context).delete(TABLE_NAME,CASE_ID+"="+caseId,null);
        return  i;
    }

    public static void updateContact(Context context, Case _case){
        ContentValues cv = new ContentValues();
        cv.put(IDENTIFICATION_NUMBER,_case.getIdentificationNumber());
        cv.put(FIRST_NAME,_case.getFirstName());
        cv.put(LAST_NAME,_case.getLastName());
        cv.put(DATE,_case.getDate());
        cv.put(PATH_PHOTO,_case.getPathPhoto());
        cv.put(PATH_LEFT_FINGER,_case.getPathLeftFinger());
        cv.put(PATH_RIGHT_FINGER,_case.getPathRightFinger());
        cv.put(IS_PROCESSED,_case.getIsProcessed());
        cv.put(IS_HIT,_case.getIsHit());
        cv.put(CANDIDATE_LIST,_case.getCandidateList());

        DatabaseAdapter.getDB(context).update(TABLE_NAME,cv,CASE_ID+"="+_case.getCaseId(),null);
    }




}

