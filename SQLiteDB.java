public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "univet.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL CHECK(role IN ('Admin', 'Technician', 'User'))," +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP" +
                ");";

        String createAppointmentTable = "CREATE TABLE Appointment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "pet_id INTEGER NOT NULL, " +
                "service_type TEXT NOT NULL, " +
                "appointment_date TEXT NOT NULL, " +
                "status TEXT DEFAULT 'Pending', " +
                "technician_id INTEGER, " +
                "payment_status TEXT DEFAULT 'Unpaid', " +
                "total_cost REAL NOT NULL, " +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES User(id), " +
                "FOREIGN KEY (pet_id) REFERENCES Pet(id), " +
                "FOREIGN KEY (technician_id) REFERENCES User(id)" +
                ");";

        db.execSQL(createUserTable);
        db.execSQL(createAppointmentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Appointment");
        onCreate(db);
    }
}
