package com.ashutosh.iiitd.mymedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
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
        //Toast.makeText(getApplicationContext(),width_text_box+""+"   "+width+"",Toast.LENGTH_SHORT).show();
        start_from = 50;
        rel_height = start_from;

        //RelativeLayout container = (RelativeLayout)findViewById(R.id.inner_linear_layout);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.inner_linear_layout);
        RelativeLayout.LayoutParams relativeLayoutParams;
        TextView[] textView = new TextView[2];

// 1st TextView
        textView[0] = new TextView(this);

        relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        textView[0].setId(R.id.input_number); // changed id from 0 to 1
        textView[0].setText("1");

        relativeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        relativeLayout.addView(textView[0], relativeLayoutParams);

// 2nd TextView
        textView[1] = new TextView(this);

        relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        textView[1].setText("2");

        relativeLayoutParams.addRule(RelativeLayout.RIGHT_OF, textView[0].getId());
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_TOP, textView[0].getId()); // added top alignment rule

        relativeLayout.addView(textView[1], relativeLayoutParams);

        int i;
        //for(i = 0 ; i < num ; i ++){

          //  create_container_in_layout(i, container);
        //}

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
}
