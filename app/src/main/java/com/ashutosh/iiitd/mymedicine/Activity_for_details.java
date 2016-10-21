package com.ashutosh.iiitd.mymedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Activity_for_details extends AppCompatActivity {

    protected Button mButton_for_redirect;
    private final static String KEY_NUMBERS = "number_of_medicine";
    TextView numbers;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_details);
        mButton_for_redirect = (Button)findViewById(R.id.button_for_redirect);
        mButton_for_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers = (TextView)findViewById(R.id.input_number);
                try
                {
                    num = Integer.parseInt(numbers.getText().toString());
                }
                catch(Exception ex)
                {

                }
                Intent intent_for_adding = new Intent(Activity_for_details.this, Add_medicines_2.class);
                intent_for_adding.putExtra(KEY_NUMBERS,num);
                startActivity(intent_for_adding);
                overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){

        finish();
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }
}
