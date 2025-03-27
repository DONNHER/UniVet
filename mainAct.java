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
