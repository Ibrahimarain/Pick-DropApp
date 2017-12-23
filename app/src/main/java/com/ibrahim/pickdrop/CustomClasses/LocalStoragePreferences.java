package com.ibrahim.pickdrop.CustomClasses;


import android.content.Context;
import android.content.SharedPreferences;


public class LocalStoragePreferences {
    public final static String STORAGE_NAME = "userInfo";
    private static final String USERID = "UserId";

    private static final String FIRSTNAME = "FirstName";
    private static final String LASTNAME = "LastName";
    private static final String COMPANY_CODE = "CompanyCode";
    private static final String IMAGE_PATH = "ImagePath";
    private static final String USER_IMAGE_PATH = "UserImagePath";
    private static final String ASSIGNED_STORES_COUNT = "assignedStoreCount";
    private static final String LAST_TOTAL_STORES_COUNT = "newTaggedStoreCount";
    private static final String TAGGED_STORES_COUNT = "taggedStoreCount";
    private static LocalStoragePreferences instance = null;
    //Constants
    private final String GPS_SERVICE_STATUS = "service";
    private final String USER_NAME = "userInfo";
    private final String FULL_NAME = "fullName";
    private final String TOKEN = "token";
    private final String CONTACT_NUMBER = "contactNumber";
    private final String CNIC = "CNIC";
    private final String IS_LOGGED_IN = "isLoggedIn";
    private final String SYNC_DOWN = "sycn_down";
    private final String SYNCH_UP_DATE = "synch_down_date";
    private final String SYNCH_UP_TIME = "synch_up_time";
    private Context context;


    private static final String USER_TYPE = "UserType";
    private static final String USER_DATA = "UserData";



    private LocalStoragePreferences(Context context) {
        this.context = context;
    }

    public static LocalStoragePreferences getInstance(Context context) {
        if (instance == null) {
            instance = new LocalStoragePreferences(context);

        }
        return instance;
    }

    public void saveCompanyCode(String companyCode) {
        save(COMPANY_CODE, companyCode);
    }

    public String getCompanyCode() {
        return get(COMPANY_CODE, "");
    }

    public void saveImagePath(String imagePath) {
        save(IMAGE_PATH, imagePath);
    }

    public String getImagePath() {
        return get(IMAGE_PATH, "");
    }

    public void saveUserImagePath(String imagePath) {
        save(USER_IMAGE_PATH, imagePath);
    }

    public String getUserImagePath() {
        return get(USER_IMAGE_PATH, "");
    }

    private void save(String key, String value) {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putString(key, value.trim()).commit();
    }

    private void save(String key, int value) {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    private void save(String key, boolean value) {
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    private String get(String key, String def) {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(key, def);
    }

    private int get(String key, int def) {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt(key, def);
    }

    private boolean get(String key, boolean def) {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean(key, def);
    }

    //User Name
    public void saveUserName(String userName) {
        save(USER_NAME, userName);
    }


    public String getUserName()

    {
        return get(USER_NAME, "");
    }

    public void saveFirstName(String firstName) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(FIRSTNAME, firstName);
        sp.apply();
    }

    public boolean isFirstRun() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean("isFirstRun", true);
    }

    public void setFirstRun(boolean isFirtRun) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean("isFirstRun", isFirtRun);
        sp.commit();
    }

    public String getFirstName() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(FIRSTNAME, null);
    }

    public void saveLastName(String lastName) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(LASTNAME, lastName);
        sp.apply();

    }

    public int getUserId() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt(USERID, 0);
    }

    public String getLastName() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(LASTNAME, null);

    }

    //Full Name
    public void saveFullName(String fullName) {
        save(FULL_NAME, fullName);
    }

    public String getFullName() {
        return get(FULL_NAME, "");
    }

    //Contact Number
    public void saveContactNumber(String contactNumber) {
        save(CONTACT_NUMBER, contactNumber);
    }

    public String getContactNumber() {
        return get(CONTACT_NUMBER, "");
    }

    //CNIC
    public void saveCNIC(String cnic) {
        save(CNIC, cnic);
    }

    public String getCNIC() {
        return get(CNIC, "");
    }

    //Token
    public void saveToken(String token) {
        save(TOKEN, token);
    }

    public String getToken() {
        return get(TOKEN, "");
    }

    //Logged in
    public void saveIsLoggedIn(boolean isLoggedIn) {
        save(IS_LOGGED_IN, isLoggedIn);
    }


    public boolean isLoggedIn() {
        return get(IS_LOGGED_IN, false);
    }


    public void saveUserId(int UserId) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(USERID, UserId);
        sp.apply();
    }
    //SycnDown
    public void saveIsSyncDown(boolean sycnDown) {
        save(SYNC_DOWN, sycnDown);
    }

    public boolean isSyncDown() {
        return get(SYNC_DOWN, false);
    }


    public void saveLastSynchUpDate(String date) {
        save(SYNCH_UP_DATE, date);
    }

    public String getLastSynchUpDate() {
        return get(SYNCH_UP_DATE, "");
    }

    public void saveLastSynchUpTime(String time) {
        save(SYNCH_UP_TIME, time);
    }

    public String getLastSynchUpTime() {
        return get(SYNCH_UP_TIME, "");
    }


    public void clear() {

        saveIsLoggedIn(false);
        saveIsSyncDown(false);

    }

    public void clearPrefrences() {
        SharedPreferences.Editor pref = context.getSharedPreferences(STORAGE_NAME, 0).edit();
        pref.clear().commit();
    }


    public boolean getServiceStatus() {
        return get(GPS_SERVICE_STATUS, false);
    }

    public void setServiceStatus(boolean status) {
        save(GPS_SERVICE_STATUS, status);
    }

    public void setNetworkFailuar(boolean wasNetworkFailed) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean("wasNetworkFailed", wasNetworkFailed);
        sp.commit();
    }

    public boolean wasNetworkFailed() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean("wasNetworkFailed", false);
    }


    public void setShouldStopService(boolean isActivityOpen) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();

        sp.putBoolean("shouldStopService", isActivityOpen);

        sp.commit();
    }

    public boolean shouldStopService() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean("shouldStopService", false);
    }


    public void savePictureQuality(int quality) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt("SelectedQuality", quality);
        sp.commit();
    }


    public int getPictureQuality() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt("SelectedQuality", 0);
    }


    public void saveVisitIdForTimer(String visitId) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString("visitIdForTimer", visitId);
        sp.commit();
    }


    public String getVisitIdForTimer() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("visitIdForTimer", "0");
    }


    public void setAssignedStoresCount(int count) {
        save(ASSIGNED_STORES_COUNT, count);
    }

    public int getAssignedStoreCount() {
        return get(ASSIGNED_STORES_COUNT, 0);
    }



    public int getLastTotalStoresCount() {
        return get(LAST_TOTAL_STORES_COUNT, 0);
    }

    public void setLastTotalStoresCount(int count) {
        save(LAST_TOTAL_STORES_COUNT, count);
    }

    public int getTaggedStoreCount() {
        return get(TAGGED_STORES_COUNT, 0);
    }

    public void setTaggedStoresCount(int count) {
        save(TAGGED_STORES_COUNT, count);
    }


    public String getAuditorTracking(){
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("auditorTracking", null);
    }



    public String getServerTime() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("serverTime", "");

    }

    public void setServerTime(String time) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString("serverTime", time);
        sp.commit();
    }

    public String getServerDate() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("serverDate", "");

    }

    public void setServerDate(String date) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString("serverDate", date);
        sp.commit();
    }



    public String getFCMId(){
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("fcmId", "");

    }

    public void setFCMId(String stdDate) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString("fcmId", stdDate);
        sp.commit();
    }

    public String getLanguageLocal(){
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString("local", "");
    }

    public void setLanguageLocal(String str_local) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString("local", str_local);
        sp.commit();
    }

    public void saveUserType(int userType) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(USER_TYPE, userType);
        sp.apply();
    }

    public int getUserType() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt(USER_TYPE, 0);
    }


    public void saveUserData(String userData) {
        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(USER_DATA, userData);
        sp.apply();
    }

    public String  getUserData() {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(USER_DATA, "");
    }




}
