package appmaster.sreeram.example.com.spatia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    int[] sliderImages = {
            R.drawable.slider_1,
            R.drawable.slider_2,
            R.drawable.slider_3
    };

    String[] sliderHeadings = {
            "DISCOVER",
            "LEARN",
            "LAUNCH!"
    };

    String[] sliderDescriptions = {
            "Everything you ever wanted to learn about rocket launch, all at one place!",
            "Study the different steps involved in the launch of a rocket or shuttle",
            "Get updates of all the launch information from all the world, all at one place"
    };

    @Override
    public int getCount() {
        return sliderHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView sliderImage = (ImageView) view.findViewById(R.id.sliderImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.sliderHeading);
        TextView slideDescription = (TextView) view.findViewById(R.id.sliderDescription);

        sliderImage.setImageResource(sliderImages[position]);
        slideHeading.setText(sliderHeadings[position]);
        slideDescription.setText(sliderDescriptions[position]);

        container.addView(view);

        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}