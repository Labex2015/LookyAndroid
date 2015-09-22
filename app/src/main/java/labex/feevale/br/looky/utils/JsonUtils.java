package labex.feevale.br.looky.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import labex.feevale.br.looky.gcm.model.RegisterLogin;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.RequestHelp;
import labex.feevale.br.looky.model.SearchItem;
import labex.feevale.br.looky.model.ServiceError;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.service.mod.GlobalMod;

/**
 * Created by 0118230 on 12/12/2014.
 */
public class JsonUtils<T>{
    private Gson mGson;
    private T entity;


    public JsonUtils() {
    }

    public JsonUtils(T entity) {
        this.entity = entity;
    }

    private void postExecute(){
        mGson = new GsonBuilder().registerTypeAdapter(Boolean.class, new GsonBoolean())
                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    private void preExecute(){
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").serializeNulls().create();
    }

    public Object process(String response){
        postExecute();
        try{
            return mGson.fromJson(response, entity.getClass());
        }catch (Exception e){
            e.printStackTrace();
            return entity;
        }
    }

    public String process(T toProcess){
        preExecute();
        try{
            return mGson.toJson(toProcess);
        }catch (Exception e){
            return "";
        }
    }

    public User JsonToUser(String response){
        postExecute();
        return mGson.fromJson(response, User.class);
    }

    public String UserToJson(User user){
        preExecute();
        return mGson.toJson(user);
    }

    public MessageResponse JsonToMessageResponse(String response){
        postExecute();
        MessageResponse messageResponse = mGson.fromJson(response, MessageResponse.class);
        if(messageResponse != null)
            return messageResponse;

        return new MessageResponse("Problemas ao consultar servidor!", false);
    }

    public List<Knowledge> JsonToListKnowledges(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<Knowledge>>(){}.getType());
    }

    public List<User> JsonToListUsers(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<User>>(){}.getType());
    }

    public List<GlobalMod> JsonToListGlobalMod(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<GlobalMod>>(){}.getType());
    }

    public List<Subject> JsonToListSubjects(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<Subject>>(){}.getType());
    }

    public List<Interaction> JsonToListInteractions(String response){
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<Interaction>>(){}.getType());
    }

    public String RequestToJson(List<User> users){
        preExecute();
        return mGson.toJson(users);
    }

    public String KnowledgeJson(Knowledge knowledge){
        preExecute();
        return mGson.toJson(knowledge);
    }

    public List<String> jsonStringList(String response){
        return (List<String>)process(response);
    }

    public String MessageToJson(MessageResponse response){
        preExecute();
        return mGson.toJson(response);
    }

    public ServiceError JsonToError(String response){
        postExecute();
        return mGson.fromJson(response, ServiceError.class);
    }

    public String RegisterLoginToJson(RegisterLogin response) {
        preExecute();
        return mGson.toJson(response);
    }

    public RequestHelp JsonToRequestHelp(String response) {
        postExecute();
        return mGson.fromJson(response, RequestHelp.class);
    }

    public String searchItemToJson(SearchItem searchItem) {
        preExecute();
        return mGson.toJson(searchItem);
    }

    public List<UserProfile> jsonToUserProfileList(String response) {
        postExecute();
        return mGson.fromJson(response, new TypeToken<List<UserProfile>>() {
        }.getType());
    }

    public String userToJson(User user) {
        preExecute();
        return mGson.toJson(user);
    }
}
