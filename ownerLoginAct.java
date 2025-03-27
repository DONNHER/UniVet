private void loginUser(String username, String password) {
    if (authenticateUser(username, password)) {
        saveSession(username, getUserRole(username));

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

// Save session with role-based access
private void saveSession(String username, String role) {
    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("isLoggedIn", true);
    editor.putString("username", username);
    editor.putString("role", role); // Store user role
    editor.apply();
}
