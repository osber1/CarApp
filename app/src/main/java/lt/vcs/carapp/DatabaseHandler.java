package lt.vcs.carapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import lt.vcs.carapp.model.Car;
import lt.vcs.carapp.model.Image;
import lt.vcs.carapp.model.Maintenance;
import lt.vcs.carapp.model.Service;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "autoApp";
    private static final String ID = "1";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_AUTO_INFO = "CREATE TABLE autoInfo (id INTEGER PRIMARY KEY, mileage INTEGER, techFrom TEXT, techTo TEXT, techPeriod INTEGER, " +
                "insuranceCompany TEXT, insurancePrice INTEGER, insurancePeriod INTEGER, insuranceFrom TEXT, insuranceTo TEXT, autoName TEXT, autoAbout TEXT)";
        String CREATE_SERVICE = "CREATE TABLE service (id INTEGER PRIMARY KEY, autoId INTEGER, date TEXT, title TEXT, comment TEXT)";
        String CREATE_MAINTENANCE = "CREATE TABLE maintenance (id INTEGER PRIMARY KEY, autoId INTEGER, date TEXT, jobId TEXT, mileage INT, comment TEXT)";
        String CREATE_PHOTOS = "CREATE TABLE photos (id INTEGER PRIMARY KEY, jobId INTEGER, autoId INTEGER, photo BLOG)";

        sqLiteDatabase.execSQL(CREATE_AUTO_INFO);
        sqLiteDatabase.execSQL(CREATE_SERVICE);
        sqLiteDatabase.execSQL(CREATE_MAINTENANCE);
        sqLiteDatabase.execSQL(CREATE_PHOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }


    public Car getCarById(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Car selectedCar = new Car();
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM autoInfo WHERE id = " + id, null)) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                selectedCar.id = cursor.getInt(cursor.getColumnIndex("id"));
                selectedCar.mileage = cursor.getInt(cursor.getColumnIndex("mileage"));
                selectedCar.techFrom = cursor.getString(cursor.getColumnIndex("techFrom"));
                selectedCar.techTo = cursor.getString(cursor.getColumnIndex("techTo"));
                selectedCar.techPeriod = cursor.getInt(cursor.getColumnIndex("techPeriod"));
                selectedCar.insuranceCompany = cursor.getString(cursor.getColumnIndex("insuranceCompany"));
                selectedCar.insurancePrice = cursor.getInt(cursor.getColumnIndex("insurancePrice"));
                selectedCar.insurancePeriod = cursor.getInt(cursor.getColumnIndex("insurancePeriod"));
                selectedCar.insuranceFrom = cursor.getString(cursor.getColumnIndex("insuranceFrom"));
                selectedCar.insuranceTo = cursor.getString(cursor.getColumnIndex("insuranceTo"));
                selectedCar.autoName = cursor.getString(cursor.getColumnIndex("autoName"));
                selectedCar.autoAbout = cursor.getString(cursor.getColumnIndex("autoAbout"));
            }
            return selectedCar;
        }
    }

    public Image getImage(int jobId, int autoId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Image image = new Image();
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT photo FROM photos WHERE jobId = " + jobId + " AND autoId = " + autoId, null)) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                image.image = cursor.getBlob(cursor.getColumnIndex("photo"));
            }
            return image;
        }
    }

    public ArrayList getCarList() {
        ArrayList<Car> carList = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM autoInfo", null);
            if (!cursor.moveToFirst())
                return carList;
            do {
                Car car = new Car();
                car.id = cursor.getInt(cursor.getColumnIndex("id"));
                car.mileage = cursor.getInt(cursor.getColumnIndex("mileage"));
                car.techFrom = cursor.getString(cursor.getColumnIndex("techFrom"));
                car.techTo = cursor.getString(cursor.getColumnIndex("techTo"));
                car.techPeriod = cursor.getInt(cursor.getColumnIndex("techPeriod"));
                car.insuranceCompany = cursor.getString(cursor.getColumnIndex("insuranceCompany"));
                car.insurancePrice = cursor.getInt(cursor.getColumnIndex("insurancePrice"));
                car.insurancePeriod = cursor.getInt(cursor.getColumnIndex("insurancePeriod"));
                car.insuranceFrom = cursor.getString(cursor.getColumnIndex("insuranceFrom"));
                car.insuranceTo = cursor.getString(cursor.getColumnIndex("insuranceTo"));
                car.autoName = cursor.getString(cursor.getColumnIndex("autoName"));
                car.autoAbout = cursor.getString(cursor.getColumnIndex("autoAbout"));
                carList.add(car);
            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            return carList;
        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }
    }

    public ArrayList<Maintenance> getMaintenanceListById(int id) {
        ArrayList<Maintenance> maintenanceList = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM maintenance WHERE autoId = " + id, null);
            if (!cursor.moveToFirst())
                return maintenanceList;
            do {
                Maintenance maintenance = new Maintenance();
                maintenance.id = cursor.getInt(cursor.getColumnIndex("id"));
                maintenance.autoId = cursor.getInt(cursor.getColumnIndex("autoId"));
                maintenance.date = cursor.getString(cursor.getColumnIndex("date"));
                maintenance.jobId = cursor.getString(cursor.getColumnIndex("jobId"));
                maintenance.mileage = cursor.getInt(cursor.getColumnIndex("mileage"));
                maintenance.comment = cursor.getString(cursor.getColumnIndex("comment"));
                maintenanceList.add(maintenance);
            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            return maintenanceList;
        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }
    }

    public ArrayList<Service> getServiceListById(int id) {
        ArrayList<Service> serviceList = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM service WHERE autoId = " + id, null);
            if (!cursor.moveToFirst())
                return serviceList;
            do {
                Service service = new Service();
                service.id = cursor.getInt(cursor.getColumnIndex("id"));
                service.autoId = cursor.getInt(cursor.getColumnIndex("autoId"));
                service.date = cursor.getString(cursor.getColumnIndex("date"));
                service.title = cursor.getString(cursor.getColumnIndex("title"));
                service.comment = cursor.getString(cursor.getColumnIndex("comment"));
                serviceList.add(service);
            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
            return serviceList;
        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }
    }

    public void createNewCar(String autoName, String autoAbout) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("autoName", autoName);
        contentValues.put("autoAbout", autoAbout);

        sqLiteDatabase.insert("autoInfo", null, contentValues);
        sqLiteDatabase.close();
    }

    public void updateCarInfo(String title, String about, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("autoName", title);
        contentValues.put("autoAbout", about);

        sqLiteDatabase.update("autoInfo", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void updateMileage(int mileage, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mileage", mileage);
        sqLiteDatabase.update("autoInfo", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void updateTech(String techFrom, String techTo, int techPeriod, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("techFrom", techFrom);
        contentValues.put("techTo", techTo);
        contentValues.put("techPeriod", techPeriod);
        sqLiteDatabase.update("autoInfo", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void updateInsurance(String insuranceCompany, int insurancePrice, int insurancePeriod, String insuranceFrom, String insuranceTo, int id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("insuranceCompany", insuranceCompany);
        contentValues.put("insurancePrice", insurancePrice);
        contentValues.put("insurancePeriod", insurancePeriod);
        contentValues.put("insuranceFrom", insuranceFrom);
        contentValues.put("insuranceTo", insuranceTo);

        sqLiteDatabase.update("autoInfo", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void setMaintenance(String comment, String date, int mileage, int autoId, String jobs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("autoId", autoId);
        contentValues.put("date", date);
        contentValues.put("comment", comment);
        contentValues.put("mileage", mileage);
        contentValues.put("jobId", jobs);

        sqLiteDatabase.insert("maintenance", null, contentValues);
        sqLiteDatabase.close();
    }

    public void updateMaintenance(String comment, String date, int mileage, int id, String jobs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("comment", comment);
        contentValues.put("mileage", mileage);
        contentValues.put("jobId", jobs);

        sqLiteDatabase.update("maintenance", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void setService(String title, String comment, String date, int autoId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("autoId", autoId);
        contentValues.put("date", date);
        contentValues.put("title", title);
        contentValues.put("comment", comment);

        sqLiteDatabase.insert("service", null, contentValues);
        sqLiteDatabase.close();
    }

    public void updateService(String title, String comment, String serviceDate, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", serviceDate);
        contentValues.put("title", title);
        contentValues.put("comment", comment);

        sqLiteDatabase.update("service", contentValues, "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void deleteAuto(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("autoInfo", "id = " + id, null);
        sqLiteDatabase.delete("service", "autoId = " + id, null);
        sqLiteDatabase.delete("maintenance", "autoId = " + id, null);
        sqLiteDatabase.close();
    }

    public void deleteService(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("service", "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void deleteMaintenance(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("maintenance", "id = " + id, null);
        sqLiteDatabase.close();
    }

    public void setImage(int jobId, int idAuto, byte[] photo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        sqLiteDatabase.delete("photos", "jobId = " + jobId + " AND autoId = " + idAuto, null);

        contentValues.put("jobId", jobId);
        contentValues.put("autoId", idAuto);
        contentValues.put("photo", photo);

        sqLiteDatabase.insert("photos", null, contentValues);
        sqLiteDatabase.close();
    }
}
