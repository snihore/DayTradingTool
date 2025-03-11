package com.sourabh.daytradingtool;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrivacyActivity extends AppCompatActivity {
    private ImageView privacyBackBtn;
    private TextView textTv;
    private final String privacyText = "Privacy Policy for TradeSizer\n" +
            "\n" +
            "Effective Date: September 2, 2024\n" +
            "\n" +
            "1. Introduction\n" +
            "\n" +
            "Welcome to TradeSizer! We value your privacy and are committed to protecting your personal information. This Privacy Policy explains how we collect, use, and safeguard your data when you use our app.\n" +
            "\n" +
            "2. Information We Collect\n" +
            "\n" +
            "We may collect and process the following types of information:\n" +
            "\n" +
            "Personal Information: When you contact us, we may collect information such as your name, email address, and phone number.\n" +
            "Usage Data: We may collect data on how you use our app, such as the features you use and the frequency of usage.\n" +
            "Device Information: We may collect information about your device, such as IP address, device type, operating system, and browser type.\n" +
            "3. How We Use Your Information\n" +
            "\n" +
            "The information we collect may be used for the following purposes:\n" +
            "\n" +
            "To provide, operate, and maintain the app.\n" +
            "To improve, personalize, and expand the app.\n" +
            "To understand and analyze how you use the app.\n" +
            "To develop new products, services, features, and functionality.\n" +
            "To communicate with you, including for customer service, updates, and promotional purposes.\n" +
            "4. Sharing Your Information\n" +
            "\n" +
            "We do not sell, trade, or otherwise transfer your personal information to outside parties except as described below:\n" +
            "\n" +
            "Service Providers: We may share your information with third-party service providers who assist us in operating our app and providing services to you.\n" +
            "Legal Compliance: We may disclose your information if required by law or in response to valid requests by public authorities.\n" +
            "5. Security of Your Information\n" +
            "\n" +
            "We implement a variety of security measures to maintain the safety of your personal information. However, no method of transmission over the Internet or electronic storage is 100% secure, so we cannot guarantee absolute security.\n" +
            "\n" +
            "6. Your Rights\n" +
            "\n" +
            "You have the right to access, correct, or delete your personal information. You may also have the right to object to or restrict certain types of processing. To exercise these rights, please contact us at souravnihore1998@gmail.com.\n" +
            "\n" +
            "7. Third-Party Links\n" +
            "\n" +
            "Our app may contain links to third-party websites or services. We are not responsible for the privacy practices or the content of these third-party sites.\n" +
            "\n" +
            "8. Changes to This Privacy Policy\n" +
            "\n" +
            "We may update this Privacy Policy from time to time. Any changes will be posted on this page, and the effective date will be updated accordingly.\n" +
            "\n" +
            "9. Contact Us\n" +
            "\n" +
            "If you have any questions about this Privacy Policy, please contact us:\n" +
            "\n" +
            "Email: souravnihore1998@gmail.com\n" +
            "Address: Vijay Nagar, Indore, India - 452011\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        initViews();
    }

    private void initViews() {
        privacyBackBtn = (ImageView) findViewById(R.id.privacy_back_btn);
        textTv = (TextView) findViewById(R.id.privacy_text);

        textTv.setText(privacyText);

        privacyBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}