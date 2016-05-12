package com.example.dgivelet.essai_request_response;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

/**
 * Created by dgivelet on 06/05/2016.
 */
public class GeneratecodeAvtivity extends AppCompatActivity{


    private Button buttongeneratecode;
    private EditText edittextinputStringcode;
    private ImageView imageviewcode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatecode);
        buttongeneratecode= (Button) findViewById(R.id.buttongeneratecode);
        edittextinputStringcode= (EditText) findViewById(R.id.edittextinputStringcode);
        imageviewcode= (ImageView) findViewById(R.id.imageviewcode);



    }




    public void onClickGenerateCode(View view) {
        String inputstringcode=edittextinputStringcode.getText().toString();

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(inputstringcode,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Log.e("onClickGenerateode: ","dans le try" );
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageviewcode);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            Log.e("onClickGenerateode: ","dans le catch" );
        }


    }
}


