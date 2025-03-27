@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    PetService petService = new PetService(dbHelper);
    OwnerService ownerService = new OwnerService(dbHelper);
    ServiceService serviceService = new ServiceService(dbHelper);
    AppointmentService appointmentService = new AppointmentService(dbHelper);

    // Initialize SessionManager
    SessionManager.initialize(petService, ownerService, serviceService, appointmentService);
}
@Override
protected void onDestroy() {
    super.onDestroy();
    SessionManager.getInstance().saveSessionData();
}
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!isSessionActive()) { 
        // Redirect to Login if session is not found
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    } else {
        // Load Main UI for the logged-in user
        setContentView(R.layout.activity_main);
        loadUserRoleUI();
    }
}

// Check if a session exists
private boolean isSessionActive() {
    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
    return sharedPreferences.getBoolean("isLoggedIn", false);
}
