package labex.feevale.br.looky.model;


import java.io.Serializable;
import java.util.Date;

public class Interaction implements Serializable{

    private static final long serialVersionUID = -1765802614315416402L;

    private Long id;
    private Long idRequestHelp;
    private RequestHelp requestHelp;
    private String request;
    private String chatRoom;
    private Boolean open;
    private Date started;
    private Date closed;

    private byte[] pictureAnotherUser;

    public Interaction() { }

    public Interaction(Long id, Long idRequestHelp,RequestHelp requestHelp,
                       String chatRoom, Boolean open, Date started, Date closed) {
        this.id = id;
        this.idRequestHelp = idRequestHelp;
        this.chatRoom = chatRoom;
        this.open = open;
        this.started = started;
        this.closed = closed;
        this.requestHelp = requestHelp;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequestHelp() {
        return idRequestHelp;
    }

    public void setIdRequestHelp(Long idRequestHelp) {
        this.idRequestHelp = idRequestHelp;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getClosed() {
        return closed;
    }

    public void setClosed(Date closed) {
        this.closed = closed;
    }

    public byte[] getPictureAnotherUser() {
        return pictureAnotherUser;
    }

    public void setPictureAnotherUser(byte[] pictureAnotherUser) {
        this.pictureAnotherUser = pictureAnotherUser;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public RequestHelp getRequestHelp() {
        return requestHelp;
    }

    public void setRequestHelp(RequestHelp requestHelp) {
        this.requestHelp = requestHelp;
    }
}
