package labex.feevale.br.looky.view.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.view.BaseFragment;

/**
 * Created by 0126128 on 25/06/2015.
 */
public class MainFragment extends BaseFragment{


    private FloatingActionButton menuButton, historyButton, interactionButton, reqHelpButton,
            reqHelpGlobButton;

    private int width;
    private int height;
    private AnimatorSet set;

    static float position = 0;
    private static boolean open = false;
    public static int positionItem = 0;

    public float initialPosition = 0;

    Animation fadeIn, fadeOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        height = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        menuButton =  (FloatingActionButton) view.findViewById(R.id.menuButton);
        historyButton =  (FloatingActionButton) view.findViewById(R.id.historyButton);
        interactionButton = (FloatingActionButton) view.findViewById(R.id.interactionsButton);
        reqHelpButton =  (FloatingActionButton) view.findViewById(R.id.reqHelpButton);
        reqHelpGlobButton = (FloatingActionButton) view.findViewById(R.id.reqHelpGlobalButton);

        position = historyButton.getY();
        initialPosition = historyButton.getY();

        menuButton.bringToFront();
        menuButton.setOnClickListener(buttonAnimationEventListener());
        loadVisibility(false);
        return view;
    }

    private void loadVisibility(Boolean status) {
        historyButton.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        interactionButton.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        reqHelpGlobButton.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        reqHelpButton.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
    }

    private View.OnClickListener buttonAnimationEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open = !open;
                set = setAnimation(historyButton, interactionButton, reqHelpGlobButton, reqHelpButton);
                menuButton.animate().rotation(open ? -45f : 0f)
                        .setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
                if(open)
                    loadVisibility(open);
                set.start();
            }
        };
    }

    private AnimatorSet setAnimation(FloatingActionButton... buttons) {
        int counter = 0;

        position = menuButton.getY();
        ObjectAnimator[] animators  = new ObjectAnimator[buttons.length*2];
        for(FloatingActionButton b : buttons){
            ObjectAnimator objectAnimator = null;
            ObjectAnimator objectAnimatorFade = null;
            if(open) {
                position -= (b.getHeight() + 30);
                objectAnimator = ObjectAnimator.ofFloat(b, "y", position).setDuration(100);
                objectAnimatorFade = ObjectAnimator.ofFloat(b, "alpha", 0, 1).setDuration(100);
            }else{
                position = menuButton.getY() - 100;
                objectAnimator = ObjectAnimator.ofFloat(b, "y", position).setDuration(100);
                objectAnimatorFade = ObjectAnimator.ofFloat(b, "alpha", 1, 0).setDuration(100);
            }
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.addListener(getFadeInFadeOut(b));
            objectAnimatorFade.setInterpolator(new DecelerateInterpolator());
            animators[counter++] = objectAnimator;
            animators[counter++] = objectAnimatorFade;
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        return set;
    }

    public Animator.AnimatorListener getFadeInFadeOut(final View view){
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                positionItem++;
                if(open){
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(positionItem == 4) {
                    positionItem = 0;
                }
                if(!open){
                    view.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                loadVisibility(false);
                open = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }
}
