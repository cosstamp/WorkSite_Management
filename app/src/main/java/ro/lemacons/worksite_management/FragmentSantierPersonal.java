package ro.lemacons.worksite_management;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSantierPersonal extends Fragment {
    View view;

    public FragmentSantierPersonal() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.santier_personal_fragment, container, false);

        String txtJson = getArguments().getString("personal");
        Log.d("txtJsonPersonal", txtJson);

        return view;
    }
}
