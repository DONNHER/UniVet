package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uni.R;
import com.example.uni.fragments.*;
import com.google.firebase.auth.FirebaseAuth;

public  class groomServiceAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groom_service);
        recyclerView =  findViewById(R.id.service_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        if (temp.getIsLoggedIn().getClass().getSimpleName().equals("owner") || myAuth.getCurrentUser().getClass().getSimpleName().equals("owner")){
//            add.setVisibility(View.INVISIBLE);
//        }
        if (myAuth.getCurrentUser() != null) {
            TextView name = findViewById(R.id.name2);//
            name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
            return;
        }
        TextView name = findViewById(R.id.name2);//
        name.setText( "Hi, ");
    }
    public void back(View view) {
        finish();
    }
    public void onResClick(View view) {
        ownerRegisterAct dialogFragment = new ownerRegisterAct();
        dialogFragment.show(dialogFragment.getParentFragmentManager(), "RegisterDialog");
    }

    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }

    public void onAddClick(View view) {
        addService dialog = new addService();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }
    public FragmentManager getFragment(){
        return getSupportFragmentManager();
    }
}
