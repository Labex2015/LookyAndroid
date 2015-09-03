package labex.feevale.br.looky.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class DateUtils {

    public static String dateToStringLong(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMM, yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date){//TODO: Refatorar esse metodo
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
