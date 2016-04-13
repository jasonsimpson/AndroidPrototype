package com.example.jsimpson.helloworld1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Set button listeners
        //

        //
        // Modal popup
        //
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(MainActivity.this);

                        alert.setTitle("Hello World");

                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // no op
                            }
                        });

                        alert.show();
                    }
                }
        );


        //
        // Launch app
        //
        // Find package name in adb shell "pm list pachakes -f" e.g.
        //
        // root@generic_x86:/ # pm list packages -f | grep -i Browser
        // package:/system/app/Browser/Browser.apk=com.android.browser
        // root@generic_x86:/ #
        //
        //
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PackageManager manager = MainActivity.this.getPackageManager();
                        Intent i = manager.getLaunchIntentForPackage(
                                            "com.android.browser");
                        i.addCategory(Intent.CATEGORY_LAUNCHER);
                        MainActivity.this.startActivity(i);
                    }
                }
        );


        //
        // Phone button (could be skype or whatever)
        // Enable/disable based on current time. Or display popup?
        //
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //
                        // Check some time of day stuff before launching...
                        // We'll check for even vs odd minutes just as an example.
                        //
                        Date date = new Date();
                        Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setTime(date);
                        int minutes = calendar.get(Calendar.MINUTE);

                        if (minutes % 2 == 0) {

                            alert("Sorry", "It is too late to call at this time.");

                            return;
                        }

                        try {

                            //
                            // This doesn't work....
                            //
                            // PackageManager manager = MainActivity.this.getPackageManager();
                            // Intent i = manager.getLaunchIntentForPackage(
                            //         "com.android.phone");
                            // i.addCategory(Intent.CATEGORY_LAUNCHER);
                            // MainActivity.this.startActivity(i);

                            //
                            // Apparently the "phone app" cannot be retrieved by
                            // package name "com.android.phone", need to use this
                            // constant ACTION_DIAL instead...
                            //
                            // But for an app like skpye I imagine that would be
                            // retrieved by package name.
                            //
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            MainActivity.this.startActivity(intent);

                        } catch (Exception e) {
                            alert("Error", e.getMessage());
                        }

                    }
                }
        );

    }

    private void alert(String title, String message) {

        AlertDialog.Builder alert =
                new AlertDialog.Builder(this);

        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // no op
            }
        });

        alert.show();

    }

}
