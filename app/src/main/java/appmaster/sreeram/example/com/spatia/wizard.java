package appmaster.sreeram.example.com.spatia;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class wizard extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotLayout;
    TextView[] dots;

    private SliderAdapter adapter;

    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wizard);

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        finishButton = (Button) findViewById(R.id.finishButton);

        adapter = new SliderAdapter(this);

        slideViewPager.setAdapter(adapter);

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(pageChangeListener);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wizard.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void addDotsIndicator(int position){
        dots = new TextView[3];
        dotLayout.removeAllViews();

        for(int i =0;i<3;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.TransparentWhite));
            dotLayout.addView(dots[i]);
        }

        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.White));

        }
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            if(position == 2)
                finishButton.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
