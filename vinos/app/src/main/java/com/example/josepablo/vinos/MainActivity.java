package com.example.josepablo.vinos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {

    private static final String MODEL_FILE = "file:///android_asset/frozen_model_vino.pb";
    private static final String INPUT_NODE = "Input";
    private static final String OUTPUT_NODE = "output";
    private static final int[] INPUT_SIZE = {1,13};
    private TensorFlowInferenceInterface inferenceInterface;

    static {
        System.loadLibrary("tensorflow_inference");
    }
    public static int argmax (float [] elems)
    {
        int bestIdx = -1;
        float max = -1000;
        for (int i = 0; i < elems.length; i++) {
            float elem = elems[i];
            if (elem > max) {
                max = elem;
                bestIdx = i;
            }
        }
        return bestIdx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inferenceInterface = new TensorFlowInferenceInterface();
        inferenceInterface.initializeTensorFlow(getAssets(), MODEL_FILE);
        System.out.println("model loaded successfully");
        ImageView image= (ImageView) findViewById(R.id.img);
        image.setImageResource(R.drawable.collage);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                final EditText Num1 = (EditText) findViewById(R.id.f1);
                final EditText Num2 = (EditText) findViewById(R.id.f2);
                final EditText Num3 = (EditText) findViewById(R.id.f3);
                final EditText Num4 = (EditText) findViewById(R.id.f4);
                final EditText Num5 = (EditText) findViewById(R.id.f5);
                final EditText Num6 = (EditText) findViewById(R.id.f6);
                final EditText Num7 = (EditText) findViewById(R.id.f7);
                final EditText Num8 = (EditText) findViewById(R.id.f8);
                final EditText Num9 = (EditText) findViewById(R.id.f9);
                final EditText Num10 = (EditText) findViewById(R.id.f10);
                final EditText Num11 = (EditText) findViewById(R.id.f11);
                final EditText Num12 = (EditText) findViewById(R.id.f12);
                final EditText Num13 = (EditText) findViewById(R.id.f13);

                float num1 = Float.parseFloat(Num1.getText().toString());
                float num2 = Float.parseFloat(Num2.getText().toString());
                float num3 = Float.parseFloat(Num3.getText().toString());
                float num4 = Float.parseFloat(Num4.getText().toString());
                float num5 = Float.parseFloat(Num5.getText().toString());
                float num6 = Float.parseFloat(Num6.getText().toString());
                float num7 = Float.parseFloat(Num7.getText().toString());
                float num8 = Float.parseFloat(Num8.getText().toString());
                float num9 = Float.parseFloat(Num9.getText().toString());
                float num10 = Float.parseFloat(Num10.getText().toString());
                float num11 = Float.parseFloat(Num11.getText().toString());
                float num12 = Float.parseFloat(Num12.getText().toString());
                float num13 = Float.parseFloat(Num13.getText().toString());

                float[] inputFloats = {num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11, num12, num13};

                System.out.println(inputFloats.length);


                inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SIZE, inputFloats);


                inferenceInterface.runInference(new String[] {OUTPUT_NODE});

                float[] result={0,0,0} ;
                //int[] result={0};
                inferenceInterface.readNodeFloat(OUTPUT_NODE, result);

                int class_id=argmax(result);

                ImageView image= (ImageView) findViewById(R.id.img);

                String s="class";
                if (class_id==0)
                {
                    s="El vino mas probable es: Vino 1";
                    image.setImageResource(R.drawable.vino1);
                }
                else if (class_id==1)
                {
                    s="El vino mas probable es: Vino 2";
                    image.setImageResource(R.drawable.vino2);
                }
                else if (class_id==2){
                    s="El vino mas probable es: Vino 3";
                    image.setImageResource(R.drawable.vino3);
                }

                //System.out.println(Float.toString(result[0]));

                final TextView textViewR = (TextView) findViewById(R.id.result);
                //textViewR.setText(Integer.toString(class_id));
                textViewR.setText(s);
            }
        });
    }
}
