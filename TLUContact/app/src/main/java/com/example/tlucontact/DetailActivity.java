package com.example.tlucontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlucontact.models.Contact;
import com.example.tlucontact.models.Employee;
import com.example.tlucontact.models.Unit;

public class DetailActivity extends AppCompatActivity {
    private ImageView imvAvatar;
    private TextView Name, Phone, Email, UnitAddress, Position;
    private Button btnBack;
    private ImageButton btnPhone, btnSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chi tiết");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        imvAvatar = (ImageView) findViewById(R.id.imv_avatar);
        Name = (TextView) findViewById(R.id.txt_name);
        Phone = (TextView) findViewById(R.id.txt_phone);
        Email = (TextView) findViewById(R.id.txt_email);
        UnitAddress = (TextView) findViewById(R.id.txt_unit_address);
        Position = (TextView) findViewById(R.id.txt_position);
        btnPhone = (ImageButton) findViewById(R.id.btn_phone);
        btnSms = (ImageButton) findViewById(R.id.btn_sms);

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("contact");
        if (contact != null) {
            imvAvatar.setImageResource(contact.getAvatar());
            Name.setText(contact.getName());
            Phone.setText("Số điện thoại: " + contact.getPhone());


            if (contact instanceof Employee) {
                Employee employee = (Employee) contact;
                Email.setText("Email: " + employee.getEmail());
                UnitAddress.setText("Khoa: " + employee.getUnit());
                Position.setText("Chức vụ: " + employee.getPosition());

                Email.setVisibility(View.VISIBLE);
                Position.setVisibility(View.VISIBLE);
            } else if (contact instanceof Unit) {
                Unit unit = (Unit) contact;
                UnitAddress.setText("Địa chỉ: " + unit.getAddress());
            }
        }
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:" + contact.getPhone()));
                startActivity(intent);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse("sms:" + contact.getPhone()));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        String type = "";
        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact instanceof Employee) {
            type = "employee";
        } else {
            type = "unit";
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("type", type);
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }


}