package com.example.maguilar.shptes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public Toolbar toolbar;
    ImageView imageButtonMen;
    ImageSwitcher imageSwitcher;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private int[] galeria = {R.drawable.camisa_azul,R.drawable.camisa_rosa};
    private int position;
    private static final int duracion = 9000;
    private Timer timer = null;
    final Handler handler = new Handler();
    public Timer swipeTimer ;

    Realm realm;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        bindElements(view);

        realm = Realm.getDefaultInstance();

        ViewPagerHome viewPagerHome = new ViewPagerHome(getContext());

        viewPager.setAdapter(viewPagerHome);
        viewPager.setCurrentItem(0);
       // setTimer(viewPager,9,viewPagerHome.getCount(),0);

        dotscount = viewPagerHome.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.not_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.not_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });*/

        //start();

        /*Animation in = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);*/

        imageButtonMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ManActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void bindElements(View view){
        viewPager = view.findViewById(R.id.viewPager);
        sliderDotspanel = view.findViewById(R.id.SliderDots);
        toolbar = view.findViewById(R.id.toolbar);
        imageButtonMen = view.findViewById(R.id.imageButtonMan);
    }

    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(galeria[position]);
                        position++;
                        if (position == galeria.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, duracion);
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        position = 0;
        startSlider();
    }

    public void stop(View button) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*private void updateUser(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        //realm.beginTransaction();
        List<Users> list = realm.where(Users.class).findAll();
        Toast.makeText(getContext(), "toast "+list, Toast.LENGTH_SHORT).show();
        //users.setImage(0);
        //realm.copyToRealmOrUpdate(users);
        //realm.commitTransaction();
    }*/

    /*public void setTimer(final ViewPager myPager, int time, final int numPages, final int curPage){

        final Runnable Update = new Runnable() {
            int NUM_PAGES =numPages;
            int currentPage = curPage ;
            public void run() {
                if (currentPage == NUM_PAGES ) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, time*1000);

    }*/

}
