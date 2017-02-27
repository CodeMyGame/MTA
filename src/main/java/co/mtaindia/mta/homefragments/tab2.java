package co.mtaindia.mta.homefragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import co.mtaindia.mta.R;
import co.mtaindia.mta.fragments.tab3;
import co.mtaindia.mta.fragments.tab4;

public class tab2 extends Fragment {
    Vibrator vibe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        BottomBar bottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment;
                if (tabId == R.id.tab_favorites) {
                    fragment = new co.mtaindia.mta.fragments.tab1();
                    vibe.vibrate(12);
                } else if (tabId == R.id.tab_nearby) {
                    fragment = new co.mtaindia.mta.fragments.tab2();
                    vibe.vibrate(12);
                } else if (tabId == R.id.tab_about) {
                    fragment = new tab4();
                    vibe.vibrate(12);
                } else {
                    fragment = new tab3();
                    vibe.vibrate(12);
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.scrollingContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
