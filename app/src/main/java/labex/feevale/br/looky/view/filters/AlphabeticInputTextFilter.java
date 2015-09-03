package labex.feevale.br.looky.view.filters;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by 0126128 on 10/07/2015.
 */
public class AlphabeticInputTextFilter implements InputFilter{
    @Override
    public CharSequence filter(CharSequence src, int i, int i1, Spanned spanned, int i2, int i3) {
        if(src.equals(" ")){
            return src;
        }if(src.toString().matches("[\\p{L}\\s,0-9]+")){
            return src;
        }
        return "";
    }
}
