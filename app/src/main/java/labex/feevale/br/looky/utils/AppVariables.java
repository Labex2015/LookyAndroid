package labex.feevale.br.looky.utils;

/**
 * Created by Jeferson on 15/12/2014.
 */
public class AppVariables {

    public static final String TAG_ROM = "#ROOM";
    public static final String TAG_IDUSER = "#ID_USER";
    public static final String TAG_USERNAME = "#USERNAME";
    public static final String TAG_ID_KNOWLEDGE = "#TAG_ID_KNOWLEDGE";

    public static final String TAG_ID_REQUEST = "#ID_REQUEST";
    public static final String TAG_ID_HELPER = "#ID_HELPER";

    public static final String URL = "http://192.168.1.5:8793/";
    //public static final String URL_SPRING = "http://54.94.220.165:8793/";


    public static final String USER_VERB = "user/";
    public static final String POSITION_VERB = "user/position";
    public static final String REQUEST_HELP_VERB = "user/help/search";
    public static final String CANCEL_HELP = "user/help/cancel";

    public static final String KNOWLEDGE = URL +"knowledge";
    public static final String KNOWLEDGE_REMOVE = URL+USER_VERB+TAG_IDUSER+"/knowledge/"+TAG_ID_KNOWLEDGE;
    public static final String KNOWLEDGE_USER = URL+USER_VERB+TAG_IDUSER+"/knowledge";
    public static final String AREAS = URL+"areas";

    public static final String CHAT = "chat";
    public static final String REQUEST_USER_HELP = "user/#ID_USER/request/#ID_HELPER";
    //public static final String RESPONSE_USER_REQUEST = "response/#ID_USER/request/";

    public static final String RESPONSE_USER_REQUEST = "response/user/#ID_USER/request/";
    public static final String REGISTER_USER = "user";
    public static final String LOGIN_USER = "user/login";

    public static final String URL_INTERACTION = URL+"users/interaction/#ID_REQUEST/#ID_HELPER";
    public static final String URL_LIST_INTERACTIONS = URL+"user/#ID_USER/interactions";
    public static final String URL_LIST_PENDING_INTERACTIONS = URL+"user/#ID_USER/interactions/pending";
    public static final String URL_USER_EVALUATION = URL+"user/evaluations/#ID";
    public static final String URL_CLOSE_INTERACTION = URL+"user/#ID_USER/interaction/#ID";
    public static final String URL_SEND_GLOBAL_REQUEST_HELP = URL+"user/request_help";


    //Params

    public static final int HTTP_TIME_OUT = 5000;
    public static final int SOCKET_TIME_OUT = 5000;
    public static final String WEB_SOCKET = "ws://54.94.220.165:8383/looky-chat-1.0.0/chat/#ROOM/#IDUSER/#USERNAME";

    public static final String SEARCH_HELP = URL + "user/help/search";
    public static final String URL_USER_PROFILE = URL + USER_VERB + TAG_IDUSER + "/profile";
    public static final String SUBJECTS =  URL+"subjects";
}
