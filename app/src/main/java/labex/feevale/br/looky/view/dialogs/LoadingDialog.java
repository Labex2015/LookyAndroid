package labex.feevale.br.looky.view.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by 0126128 on 07/01/2015.
 */
public class LoadingDialog extends ProgressDialog{


    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.progress_dialog_default);
        //final ImageView animImageView = (ImageView) findViewById(R.id.loading);

        /*animImageView.setBackgroundResource(R.drawable.animation_loading);
        animImageView.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation =  (AnimationDrawable) animImageView.getBackground();
                frameAnimation.start();
            }
        });*/

        setCancelable(false);
        setIndeterminate(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


}
