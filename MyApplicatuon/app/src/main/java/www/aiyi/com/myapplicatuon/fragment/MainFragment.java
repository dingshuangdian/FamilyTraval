package www.aiyi.com.myapplicatuon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.activity.MainActivity;
import www.aiyi.com.myapplicatuon.ui.UIHelper;

/**
 * Created by dings on 2016-11-25.
 */

public class MainFragment extends Fragment {
    private LinearLayout myPoint;
    private LinearLayout pointP;
    private LinearLayout pointD;
    private LinearLayout pointC;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        init();
        myPoint.setOnClickListener(clickListener);
        return view;
    }

    private void init() {
        myPoint = (LinearLayout) view.findViewById(R.id.myPoint);
        pointP = (LinearLayout) view.findViewById(R.id.pointP);
        pointD = (LinearLayout) view.findViewById(R.id.pointD);
        pointC = (LinearLayout) view.findViewById(R.id.pointC);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myPoint:
                    UIHelper.showMyPoint(getActivity());
                    break;
            }
        }
    };
}
