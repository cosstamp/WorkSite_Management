package ro.lemacons.worksite_management;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSantierUtilaje extends Fragment {
    View view;

    public FragmentSantierUtilaje() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.santier_utilaje_fragment, container, false);
        return view;

    }
}
