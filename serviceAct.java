@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service);

    // Example: Retrieve a service using temporary storage
    ServiceService serviceService = SessionManager.getInstance().getServiceService();
    Service myService = serviceService.getServiceById(1);
}
@Override
protected void onDestroy() {
    super.onDestroy();
    SessionManager.getInstance().saveSessionData();
}
