package labex.feevale.br.looky.utils;

import java.util.List;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.model.UserProfile;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class UserMock {

    private static User user;
    private static List<UserProfile> profiles;


    public static User getUser() {
        if(user == null) {
            user = new User();
            user.setId(1L);
            user.setDegree("Ciência da Computação");
            user.setDescription("Programo a mais de 4 anos, gosto de data science, Java e NodeJS");
            user.setLatitude(-29.664761F);
            user.setLongitude(-51.119113F);
            user.setName("Tales Hartz");
            user.setSemester(7);
            user.setUsername("stifler");
            user.setPicture(null);

        }
        return user;
    }

}
