package labex.feevale.br.looky.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import labex.feevale.br.looky.MainActivity;

/**
 * Created by grimmjowjack on 8/24/15.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setFragment(this);
    }
}
