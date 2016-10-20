package com.ashutosh.iiitd.mymedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.Display;
import android.graphics.Point;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.Button;

public class Add_medicines extends AppCompatActivity {

    int width_text_box = 0, start_from = 0, changing_factor = 0, rel_height = 0,width = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        Bundle from_details = getIntent().getExtras();
        int num = from_details.getInt("number_of_medicine");

        //getting the size of the display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        int height = size.y;
        changing_factor = height / 10;
        width_text_box = (2 * width) / 3  - 10;
        start_from = 50;
        rel_height = start_from;

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.inner_linear_layout);
        RelativeLayout.LayoutParams relativeLayoutParams, relativeLayout_button_Params;
        EditText[] editText = new EditText[num];
        Button [] button_alarm = new Button[num];
        LinearLayout []linearLayouts = new LinearLayout[num];

        /*for(int i=0;i<num;i++){

            //Initialising and adding edit text view
            editText[i] = new EditText(this);
            relativeLayoutParams = new RelativeLayout.LayoutParams(width_text_box, RelativeLayout.LayoutParams.WRAP_CONTENT);
            editText[i].setId(i+1);
            editText[i].setHint("Medicine Number" + (i+1));
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_LEFT);
            relativeLayout.addView(editText[i],relativeLayoutParams);

            //Initialising and adding button
            button_alarm[i] = new Button(this);
            int width_for_button = width-width_text_box;
            relativeLayout_button_Params = new RelativeLayout.LayoutParams(width_for_button,RelativeLayout.LayoutParams.WRAP_CONTENT);
            //button_alarm[i].setId(i);
            button_alarm[i].setText("Alarm "+(i+1));
            //relativeLayout_button_Params.addRule(RelativeLayout.ALIGN_TOP,editText[i].getId());
            //relativeLayoutParams.addRule(RelativeLayout.RIGHT_OF, editText[i].getId());
            relativeLayout.addView(button_alarm[i],relativeLayout_button_Params);
        }*/

        for(int i=0;i<num;i++){

            linearLayouts[i] = new LinearLayout(getApplicationContext());
            editText[i] = new EditText(this);
            editText[i].setId(i+1);
            editText[i].setHint("Medicine Number" + (i+1));

        }

    }


}




/*void create_container_in_layout(int i, RelativeLayout container){

        //Initialisation
        EditText et=new EditText(getApplicationContext());
        Button button = new Button(getApplicationContext());
        et.setHint("Medicine Number"+(i+1));
        button.setText("Set Alarm"+(i+1));

        //Setting params for Edit Text
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_text_box, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //et.setLayoutParams(params);
        et.setId(i+1);
        //Adding text view
        container.addView(et,params);

        //
        int width_for_button = width-width_text_box;
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width_for_button, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.RIGHT_OF, et.getId());
        //button.setLayoutParams(params1);
        button.setId(i+1);
        container.addView(button,params1);
    }*/
