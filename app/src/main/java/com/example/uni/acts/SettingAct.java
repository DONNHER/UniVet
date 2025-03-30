//
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_settings);
//
//    Button logoutButton = findViewById(R.id.btnLogout);
//    logoutButton.setOnClickListener(v -> handleLogout());
//}
//
//private void handleLogout() {
//    SessionManager.getInstance().saveSessionData(); // Save unsaved changes
//    clearUserSession();
//
//    // Redirect to Login & prevent going back
//    Intent intent = new Intent(this, LoginActivity.class);
//    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//    startActivity(intent);
//    finish();
//}
//
//// Clear stored session
//private void clearUserSession() {
//    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//    editor.clear();
//    editor.apply();
//}
//
