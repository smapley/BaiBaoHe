package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.extra.fresco.DynamicHeightSimpleDraweeView;
import com.smapley.baibaohe.utls.MyData;


/**
 * Created by smapley on 2015/6/5.
 */
public class TouchImageViewActivity extends Activity {

    public DynamicHeightSimpleDraweeView contentSdv;
    public final int GETPIC=1;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  TouchImageView img = new TouchImageView(this, MyData.bitmap);
        setContentView(R.layout.touchimageview);
        contentSdv = (DynamicHeightSimpleDraweeView) findViewById(R.id.aaa_wf_item_content_DraweeView);
        contentSdv.setImageURI(Uri.parse(MyData.URL_FILE_YUAN + getIntent().getStringExtra("pic")));

        float picRatio = (float) Integer.parseInt(getIntent().getStringExtra("height")) / Integer.parseInt(getIntent().getStringExtra("weight"));
        contentSdv.setHeightRatio(picRatio);
        contentSdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
