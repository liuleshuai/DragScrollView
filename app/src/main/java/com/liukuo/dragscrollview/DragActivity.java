package com.liukuo.dragscrollview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.draglibrary.DragListener;
import com.example.draglibrary.InnerScrollView;
import com.example.draglibrary.OuterLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragActivity extends AppCompatActivity {

    @BindView(R.id.layout)
    OuterLayout outerLayout;
    @BindView(R.id.mask)
    InnerScrollView mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        ButterKnife.bind(this);
        outerLayout.setDragListener(new DragListener() {
            @Override
            public void close() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }

            @Override
            public void changeSize(int top) {
                float radio = 1 - ((float) top) / outerLayout.getRootView().getHeight();
                float scale = Math.max(radio, 0.85f);
                mask.setScaleX(scale);
                mask.setScaleY(scale);
            }
        });
    }
}
