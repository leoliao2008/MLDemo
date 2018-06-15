package com.tgi.mldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.tgi.mldemo.R;
import com.tgi.mldemo.presenters.ModulesCmpActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModulesComparisonActivity extends AppCompatActivity {

    @BindView(R.id.radio_group)
    RadioGroup grpModules;
    @BindView(R.id.image_viw)
    ImageView ivTestImage;
    @BindView(R.id.recycler_view)
    RecyclerView rcyResults;
    @BindView(R.id.list_view)
    ListView lstvConsole;
    @BindView(R.id.button)
    Button btnIdentify;
    private ModulesCmpActivityPresenter mPresenter;
    private BitmapDrawable mBitmapDrawable;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModulesComparisonActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_comparison);
        ButterKnife.bind(this);
        mPresenter=new ModulesCmpActivityPresenter(this);
        mBitmapDrawable = (BitmapDrawable) ivTestImage.getDrawable();
        mPresenter.init();
    }

    public RadioGroup getGrpModules() {
        return grpModules;
    }

    public ImageView getIvTestImage() {
        return ivTestImage;
    }

    public RecyclerView getRcyResults() {
        return rcyResults;
    }

    public ListView getLstvConsole() {
        return lstvConsole;
    }

    public Button getBtnIdentify() {
        return btnIdentify;
    }

    public void beginAnnotationTest(View view) {
        mPresenter.identifyImage(mBitmapDrawable.getBitmap());
    }
}
